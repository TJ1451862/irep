package cn.edu.whu.irlab.irep.service.experiment.retrieval.probabilityModel;

import cn.edu.whu.irlab.irep.base.dao.experiment.DocumentService;
import cn.edu.whu.irlab.irep.base.entity.experiment.Record;
import cn.edu.whu.irlab.irep.base.entity.experiment.Retriever;
import cn.edu.whu.irlab.irep.base.entity.system.User;
import cn.edu.whu.irlab.irep.base.entity.system.UserRetrieverScore;
import cn.edu.whu.irlab.irep.service.experiment.IndexService;
import cn.edu.whu.irlab.irep.service.experiment.retrieval.ProbabilityRetrievalService;
import cn.edu.whu.irlab.irep.service.experiment.retrieval.RetrievalService;
import cn.edu.whu.irlab.irep.service.util.Find;
import cn.edu.whu.irlab.irep.service.vo.BijVo;
import cn.edu.whu.irlab.irep.service.vo.IndexVo;
import cn.edu.whu.irlab.irep.service.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProbabilityRetrieverServiceImpl extends RetrievalService implements ProbabilityRetrievalService {

    //文档数
    private final static int N = 166;
    //参数K取正值大于0
    private double k;
    //参数b取0-1
    private double b;
    //平均文档长度
    private double avg_length;

    @Autowired
    private DocumentService documentService;

    @Autowired
    private IndexService indexService;

    public void initRetriever(String queryContent, double k, double b, HttpServletRequest request) {
        super.initRetriever(queryContent,request);
        this.k = k;
        this.b = b;
        super.retriever=new Retriever(true,analyzerName,isRemoveStopWord,"probabilityModel",
                0,"系数K",(int)k*100,"系数B",(int)(b*100));
        setAvg_length();
    }

    @Override
    public List<ResultVo> calculateSimilarity() {
        List<ResultVo> resultVos = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            ResultVo resultVo = calculateSimilarity(i);
            resultVos.add(resultVo);
//            if (resultVo.getSimilarity() > 0) {
//            }
        }
        return resultVos;
    }

    public List<BijVo> calculateBijs() {
        List<BijVo> bijVos = new ArrayList<>();
        List<String> termList = super.query.getPreProcessResult();
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

    @Override
    public int quit() {
        UserRetrieverScore userRetrieverScore=new UserRetrieverScore();
        User user=(User) session.getAttribute("user");
        userRetrieverScore.setUserId(user.getId());
        userRetrieverScore.setProbabilityRetriever(retriever.getRetrieverId());
        return userRetrieverScoreService.updateByUserId(userRetrieverScore);
    }

    private ResultVo calculateSimilarity(int docId) {
        List<String> termList = super.query.getPreProcessResult();
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
        IndexVo indexVo=(IndexVo)session.getAttribute("indexVo");
        List<Record> records = indexVo.getRecordList();
        this.avg_length = (double) records.size() / N;
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
