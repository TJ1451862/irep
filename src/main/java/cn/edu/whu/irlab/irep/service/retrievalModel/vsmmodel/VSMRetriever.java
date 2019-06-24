package cn.edu.whu.irlab.irep.service.retrievalModel.vsmmodel;

import cn.edu.whu.irlab.irep.entity.FullIndex;
import cn.edu.whu.irlab.irep.entity.InvertedIndex;
import cn.edu.whu.irlab.irep.entity.Record;
import cn.edu.whu.irlab.irep.service.impl.FullIndexServiceImpl;
import cn.edu.whu.irlab.irep.service.impl.InvertedIndexServiceImpl;
import cn.edu.whu.irlab.irep.service.util.Calculator;
import cn.edu.whu.irlab.irep.service.util.Find;
import cn.edu.whu.irlab.irep.service.util.IndexTypeConstructor;
import cn.edu.whu.irlab.irep.service.util.ReadDoc;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author gcr
 * @version 1.0
 * @date 2019-06-18 23:36
 * @desc 向量空间模型检索器
 */
@Service
public class VSMRetriever {

    @Autowired
    public FullIndexServiceImpl fullIndexService;

    @Autowired
    public InvertedIndexServiceImpl invertedIndexService;

    @Autowired
    public Find find;

    @Autowired
    public ReadDoc readDoc;

    private String indexType;//索引类型

    private int formulaID;//公式ID

    private double smoothParam;//平滑系数

    private String analyzerName;//分词器名称

    private boolean isRemoveStopWord;//是否去停用词

    private Map<String, Double> term_idf = new HashMap<>();//idf

    private List<DocForVSM> docForVSMList = new ArrayList<>();//文档集

    private QueryForVSM query;//查询语句

    private List<ResultI> result = new ArrayList<>();//查询结果（未排序）

    private List<ResultI> resultAfterSort = new ArrayList<>();//查询结果

    public void initVSMRetriever(String queryContent,
                                 int formulaID,
                                 double smoothParam,
                                 String analyzerName,
                                 boolean isRemoveStopWord) {

        this.indexType = IndexTypeConstructor.indexTypeConstructor(analyzerName, isRemoveStopWord);
        this.formulaID = formulaID;
        this.smoothParam = smoothParam;
        this.analyzerName = analyzerName;
        this.isRemoveStopWord = isRemoveStopWord;
        this.query = new QueryForVSM(queryContent, analyzerName, isRemoveStopWord, formulaID, smoothParam);
    }

    public void initVSMRetriever(String analyzerName, boolean isRemoveStopWord) {
        this.analyzerName = analyzerName;
        this.isRemoveStopWord = isRemoveStopWord;
    }

    public void initVSMRetriever(String queryContent, String analyzerName, boolean isRemoveStopWord) {
        this.analyzerName = analyzerName;
        this.isRemoveStopWord = isRemoveStopWord;
        this.query = new QueryForVSM(queryContent, analyzerName, isRemoveStopWord);
    }

    public void initVSMRetriever(int formulaID, double smoothParam, String analyzerName, boolean isRemoveStopWord) {
        this.formulaID = formulaID;
        this.smoothParam = smoothParam;
        this.analyzerName = analyzerName;
        this.isRemoveStopWord = isRemoveStopWord;
    }

    public void search() {
        //初始化
        result.clear();
        resultAfterSort.clear();
        docForVSMList.clear();

        setTerm_idf();
        setDocForVSMList();
        calculateTfidf();
        calculateSimilarity();
        sortSimilarity();
    }

    /**
     * 计算IDF
     * idf=log(N/DF)
     */
    public void setTerm_idf() {
        List<FullIndex> fullIndexList = fullIndexService.selectFullIndexByIndexType(indexType);
        int N = fullIndexList.size();
        double idf;
        for (int i = 0; i < fullIndexList.size(); i++) {
            idf = Math.log(N / fullIndexList.get(i).getDf());
            term_idf.put(fullIndexList.get(i).getTerm(), idf);
        }
    }

    /**
     * 初始化各doc并计算tf
     */
    public void setDocForVSMList() {
        Map<Integer, Map<String, Double>> docID_termTF = selectTFs();
        for (Integer integer :
                docID_termTF.keySet()) {
            DocForVSM docForVSM = new DocForVSM(integer);
            docForVSM.setTfMap(docID_termTF.get(integer), formulaID, smoothParam);
            docForVSMList.add(docForVSM);
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

//        Map<Integer,List<InvertedIndex>> docId_tf=new HashMap<>();
//        for (int i = 0; i <invertedIndexList.size() ; i++) {
//            int docId=invertedIndexList.get(i).getDocId();
//            List<InvertedIndex> temp=new ArrayList<>();
//            if (term_tf.containsKey(docId)){
//                 temp=docId_tf.get(docId);
//                temp.add(invertedIndexList.get(i));
//                docId_tf.put(docId,temp);
//            }else {
//                temp.add(invertedIndexList.get(i));
//                docId_tf.put(docId,temp);
//            }
//        }

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
     * 计算tfidf并初始化vector
     */
    public void calculateTfidf() {

        Map<String, Double> tfMapOfQuery = query.getTfMap();
        List<VectorI> vectorOfQuery = new ArrayList<>();
        double idf;
        double tfidf;
        int num = 0;
        for (String s :
                term_idf.keySet()) {
            idf = term_idf.get(s);
            if (tfMapOfQuery.containsKey(s)) {
                double tfOfQuery = tfMapOfQuery.get(s);
                tfidf = tfOfQuery * idf;
                VectorI vectorI = new VectorI(s, num, tfidf);
                vectorOfQuery.add(vectorI);
            }

            for (int i = 0; i < docForVSMList.size(); i++) {
                Map<String, Double> tfMapOfDoc = docForVSMList.get(i).getTfMap();
                List<VectorI> vectorOfDoc = docForVSMList.get(i).getVector();
                if (tfMapOfDoc.containsKey(s)) {
                    double tfOfDoc = tfMapOfDoc.get(s);
                    tfidf = tfOfDoc * idf;
                    VectorI vectorI = new VectorI(s, num, tfidf);
                    vectorOfDoc.add(vectorI);
                    docForVSMList.get(i).setVector(vectorOfDoc);
                }
            }

            num++;
        }
        query.setVector(vectorOfQuery);
    }

    /**
     * 计算相似度
     */
    public void calculateSimilarity() {

        int docId;
        String title;
        double similarity = 0;

        for (int i = 0; i < docForVSMList.size(); i++) {
            List<VectorI> vector = docForVSMList.get(i).getVector();
            docId = docForVSMList.get(i).getId();
            title = find.findTitle(docId, true);
            for (int j = 0; j < query.getVector().size(); j++) {
                String term = query.getVector().get(j).getTerm();
                double valueOfQuery = query.getVector().get(j).getValue();
                for (int k = 0; k < vector.size(); k++) {
                    if (term.equals(vector.get(k).getTerm())) {
                        double valueOfDoc = vector.get(k).getValue();
                        similarity += valueOfDoc * valueOfQuery;
                    }
                }
            }
            similarity = similarity / (Calculator.calculateModule(query.getVector()) * Calculator.calculateModule(vector));
            //保留5位显示
            if (similarity>0.00001){
                ResultI resultI = new ResultI(docId, title, similarity);
                result.add(resultI);
            }
        }
    }

    /**
     * 按相似度降序排序
     */
    public void sortSimilarity() {
        List <ResultI> resultIList=new ArrayList<>();
        System.out.println("未排序");
        for (int i = 0; i < result.size(); i++) {
            resultIList.add(result.get(i));
        }
        resultAfterSort = bubbleSort(resultIList);
//        Comparator comparator = new ResultComparator();
//        Collections.sort(resultAfterSort);
        System.out.println("已排序");
        for (int i = 0; i < resultAfterSort.size(); i++) {
            System.out.println(resultAfterSort.get(i));
        }
    }

    /**
     * 冒泡排序
     */
    public List<ResultI> bubbleSort(List<ResultI> result) {
        for (int i = 0; i < result.size(); i++) {
            for (int j = 0; j < result.size() - 1 - i; j++) {
                if (result.get(j).getSimilarity() < result.get(j + 1).getSimilarity()) {
                    ResultI temp = result.get(j + 1);
                    result.set(j + 1, result.get(j));
                    result.set(j, temp);
                }
            }
        }
        return result;
    }

    public JSONObject modelTypeConstuctor() {
        JSONObject modelType = new JSONObject();
        modelType.put("modelName", "vsm");
        modelType.put("formulaID", formulaID);
        modelType.put("smoothParam", smoothParam);
        return modelType;
    }

    /**
     * 以下是get和set方法
     */
    public Map<String, Double> getTerm_idf() {
        return term_idf;
    }

    public void setTerm_idf(Map<String, Double> term_idf) {
        this.term_idf = term_idf;
    }

    public List<DocForVSM> getDocForVSMList() {
        return docForVSMList;
    }

    public void setDocForVSMList(List<DocForVSM> docForVSMList) {
        this.docForVSMList = docForVSMList;
    }

    public QueryForVSM getQuery() {
        return query;
    }

    public void setQuery(QueryForVSM query) {
        this.query = query;
    }

    public List<ResultI> getResult() {
        return result;
    }

    public void setResult(List<ResultI> result) {
        this.result = result;
    }

    public List<ResultI> getResultAfterSort() {
        return resultAfterSort;
    }

    public void setResultAfterSort(List<ResultI> resultAfterSort) {
        this.resultAfterSort = resultAfterSort;
    }
}
