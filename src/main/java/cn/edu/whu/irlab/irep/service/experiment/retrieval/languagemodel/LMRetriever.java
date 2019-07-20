package cn.edu.whu.irlab.irep.service.experiment.retrieval.languagemodel;

import cn.edu.whu.irlab.irep.base.entity.FullIndex;
import cn.edu.whu.irlab.irep.base.entity.InvertedIndex;
import cn.edu.whu.irlab.irep.base.dao.impl.FullIndexServiceImpl;
import cn.edu.whu.irlab.irep.base.dao.impl.InvertedIndexServiceImpl;
import cn.edu.whu.irlab.irep.service.util.Constructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author Jane
 * @version 1.0
 * @date 2019-07-13 15:57
 * @desc 语言模型检索器
 */
@Service
public class LMRetriever {

    @Autowired
    public FullIndexServiceImpl fullIndexService;

    @Autowired
    public InvertedIndexServiceImpl invertedIndexService;

    private String indexType;//索引类型

    private String analyzerName;//分词器名称

    private boolean isRemoveStopWord;//是否去停用词

    private QueryForLM query;//查询语句

    private double smoothParam;//平滑系数

    private List<DocForLM> docForLMList= new ArrayList<>();//文档集

    private List<ResultForLM> result=new ArrayList<>(); //未排序的结果

    private List<ResultForLM> resultAfterSort=new ArrayList<>(); //排序后的结果

    private double totalArticles=166.0;//总共文本数量;

    Map<String,Double> idfMap=new HashMap<>(); //词典中各词项在所有文档中出现的频率

    public void initLMRetriever(String query, double smoothParam,  HttpServletRequest request){
        this.indexType = Constructor.indexTypeConstructor(request);
        this.smoothParam=smoothParam;
        this.analyzerName = (String) request.getSession().getAttribute("analyzer");
        this.isRemoveStopWord = (boolean)request.getSession().getAttribute("removeStopWord");
        this.query=new QueryForLM(query, analyzerName, isRemoveStopWord);
    }


    public void search() {
        //初始化
        result.clear();
        resultAfterSort.clear();
        docForLMList.clear();

        setDocForLMList();
        setIdfs();
        setResult();
        sortResult();
    }

    /**
     * 初始化各doc并计算词频
     */
    public void setDocForLMList() {
        Map<Integer, Map<String, Double>> docID_termTF = selectTFs();
        for (Integer integer :
                docID_termTF.keySet()) {
            DocForLM docForLM = new DocForLM(integer);
            docForLM.setLMap(docID_termTF.get(integer));
            docForLMList.add(docForLM);
        }
    }

    /**
     * 获取数据库中各词项的tf
     * 按文档id进行分类
     */
    public Map<Integer, Map<String, Double>> selectTFs() {
        Map<Integer, Map<String, Double>> docID_termTF = new HashMap<>();
        List<InvertedIndex> invertedIndexList = invertedIndexService.selectByIndexType(indexType);
        double tf;
        String term;

        for (int i = 0; i < invertedIndexList.size(); i++) {
            Map<String, Double> term_tf = new HashMap<>();
            InvertedIndex invertedIndex = invertedIndexList.get(i);
            int docID = invertedIndex.getDocId();
            term = invertedIndex.getTerm();
            tf = (double) invertedIndex.getTf();
            if (docID_termTF.containsKey(invertedIndex.getDocId())) {
                term_tf = docID_termTF.get(docID);
                term_tf.put(term, tf);
                docID_termTF.put(docID, term_tf);
            } else {
                term_tf.put(term, tf);
                docID_termTF.put(docID, term_tf);
            }
        }
        return docID_termTF;
    }


    /**
     * 获取数据库中各词的idf
     * @return
     */
    public void  setIdfs(){
        List<FullIndex> fullIndexList = fullIndexService.selectByIndexType(indexType);
        for(int i=0;i<fullIndexList.size();i++){
            idfMap.put(fullIndexList.get(i).getTerm(),fullIndexList.get(i).getDf()/totalArticles);
        }
    }

    /**
     * 计算各文档的生成概率
     */
    public void setResult(){
        for(int i=0;i<docForLMList.size();i++){
            Integer docId=docForLMList.get(i).getId();
            String title=docForLMList.get(i).getTitle();
            Double GP=1.0;
            for(int j=0;j<query.getPreProcessResult().size();j++){
                Map<String,Double> lmap=docForLMList.get(i).getLMap();
                String term=query.getPreProcessResult().get(j);
                double a;
                double b;
                if(lmap.get(term)==null){
                    a=0.0;
                }else{
                    a=lmap.get(term);
                }
                if(idfMap.get(term)==null){
                    b=0.0;
                }else{
                    b=idfMap.get(term);
                }
                GP *=(1-smoothParam)*a+(smoothParam)*b;
            }
            //保留6位显示
            if (GP > 0.0000001) {
                ResultForLM resultI = new ResultForLM(docId, title, GP);
                result.add(resultI);
            }
        }
    }

    public void sortResult(){
        List<ResultForLM> resultIList = new ArrayList<>();
        for (int i = 0; i < result.size(); i++) {
            resultIList.add(result.get(i));
        }
        resultAfterSort = bubbleSort(resultIList);
    }

    /**
     * 冒泡排序
     */
    public List<ResultForLM> bubbleSort(List<ResultForLM> results) {
        for (int i = 0; i < results.size(); i++) {
            for (int j = 0; j < results.size() - 1 - i; j++) {
                if (results.get(j).getGenerateProbability() < results.get(j + 1).getGenerateProbability()) {
                    ResultForLM temp = results.get(j + 1);
                    results.set(j + 1, results.get(j));
                    results.set(j, temp);
                }
            }
        }
        return results;
    }

    /**
     * get和set方法
     */
    public QueryForLM getQuery() {
        return query;
    }

    public List<DocForLM> getDocForLMList() {
        return docForLMList;
    }

    public Map<String,Double> getIdfMap(){
        return idfMap;
    }

    public List<ResultForLM> getResult(){
        return result;
    }

    public List<ResultForLM> getResultAfterSort(){
        return resultAfterSort;
    }

    public double getSmoothParam() {
        return smoothParam;
    }

    public void setSmoothParam(double smoothParam) {
        this.smoothParam = smoothParam;
    }
}
