package cn.edu.whu.irlab.irep.service.experiment.retrieval.probabilityModel;

import cn.edu.whu.irlab.irep.base.dao.DocumentService;
import cn.edu.whu.irlab.irep.base.dao.FullIndexService;
import cn.edu.whu.irlab.irep.base.dao.RecordService;
import cn.edu.whu.irlab.irep.base.entity.Document;
import cn.edu.whu.irlab.irep.base.entity.Record;
import cn.edu.whu.irlab.irep.service.experiment.IndexService;
import cn.edu.whu.irlab.irep.service.experiment.retrieval.ProbabilityRetrievalService;
import cn.edu.whu.irlab.irep.service.util.BubbleSort;
import cn.edu.whu.irlab.irep.service.util.Find;
import cn.edu.whu.irlab.irep.service.vo.BijVo;
import cn.edu.whu.irlab.irep.service.vo.IndexVo;
import cn.edu.whu.irlab.irep.service.vo.ResultVo;
import cn.edu.whu.irlab.irep.base.dao.InvertedIndexService;
import cn.edu.whu.irlab.irep.service.entity.Doc;
import cn.edu.whu.irlab.irep.service.entity.Query;
import cn.edu.whu.irlab.irep.service.util.Constructor;
import cn.edu.whu.irlab.irep.service.vo.SearchResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProbabilityRetrieverServiceImpl implements ProbabilityRetrievalService {
    //查询
    private Query query;
    //文档数
    private final static int N = 166;
    //参数K取正值大于0
    private double k;
    //参数b取0-1
    private double b;
    //平均文档长度
    private double avg_length;
    //分词器名称
    private String analyzerName;
    //是否去停用词
    private boolean removeStopWord;

    @Autowired
    private DocumentService documentService;

    @Autowired
    private IndexService indexService;

    public void initRetriever(String queryContent, double k, double b, HttpServletRequest request) {
        this.analyzerName = (String) request.getSession().getAttribute("analyzer");
        this.removeStopWord = (boolean) request.getSession().getAttribute("removeStopWord");
        this.k = k;
        this.b = b;
//        this.indexType = Constructor.indexTypeConstructor(request);
        this.query = new Query(queryContent, analyzerName, removeStopWord);
        setAvg_length();
    }

    @Override
    public List<SearchResultVo> search() {

        List<ResultVo> resultVos = descendOrderSimilarities();
        List<SearchResultVo> searchResultVos = new ArrayList<>();
        for (int i = 0; i < resultVos.size(); i++) {
            int docId = resultVos.get(i).getDocID();
            Document document = documentService.selectByDocId(docId);
            String content = Find.findDoc(docId, true);
            SearchResultVo searchResultVo = new SearchResultVo(docId, document.getTitle(), document.getUrl(), content);
            searchResultVos.add(searchResultVo);
        }
        return searchResultVos;
    }

    public List<ResultVo> descendOrderSimilarities() {
        return BubbleSort.bubbleSort(calculateSimilarities());
    }

    public List<ResultVo> calculateSimilarities() {
        List<ResultVo> resultVos = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            ResultVo resultVo = calculateSimilarity(i);
            if (resultVo.getSimilarity() > 0) {
                resultVos.add(resultVo);
            }
        }
        return resultVos;
    }

    public List<BijVo> calculateBijs() {
        List<BijVo> bijVos = new ArrayList<>();
        List<String> termList = query.getPreProcessResult();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < termList.size(); j++) {
                BijVo bijVo = calculateBij(termList.get(j), i);
                if (bijVo.getBij() != 0) {
                    bijVos.add(bijVo);
                }
            }
        }
        return bijVos;
    }

    private ResultVo calculateSimilarity(int docId) {
        List<String> termList = query.getPreProcessResult();
        double similarity = 0;
        for (int i = 0; i < termList.size(); i++) {
            String term = termList.get(i);
            int df = indexService.selectDf(term);
            BijVo bijVo = calculateBij(term, docId);
            double bij = bijVo.getBij();
//            double nj=Math.log((N - df + 0.5) / (df + 0.5));
            similarity += bij * Math.log((N - df + 0.5) / (df + 0.5));
        }
        String title = Find.findTitle(docId, true);
        ResultVo resultVo = new ResultVo(docId, title, similarity);
        return resultVo;
    }

    private BijVo calculateBij(String term, int docId) {
        int docLength = indexService.selectDocLength(docId);
        Integer tf= indexService.selectTf(term, docId);
        double bij=0;
        if (tf!=null){
            bij = ((k + 1) * tf) / (k * ((1 - b) + b * (docLength / avg_length)) + tf);
        }
        BijVo bijVo = new BijVo(term, docId, bij);
        return bijVo;
    }

    private void setAvg_length() {
        List<Record> records = IndexVo.getRecordList();
        this.avg_length = (double) records.size() / N;
    }

    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }

    public double getK() {
        return k;
    }

    public void setK(double k) {
        this.k = k;
    }

    public double getB() {
        return b;
    }

    public void setB(double b) {
        this.b = b;
    }
}
