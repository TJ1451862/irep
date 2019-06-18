package cn.edu.whu.irlab.irep.controller;

import cn.edu.whu.irlab.irep.entity.FullIndex;
import cn.edu.whu.irlab.irep.entity.InvertedIndex;
import cn.edu.whu.irlab.irep.service.creatIndex.IndexGenerator;
import cn.edu.whu.irlab.irep.service.impl.FullIndexServiceImpl;
import cn.edu.whu.irlab.irep.service.impl.InvertedIndexServiceImpl;
import cn.edu.whu.irlab.irep.service.preProcess.PreProcessor;
import cn.edu.whu.irlab.irep.service.retrievalModel.vsmmodel.DocForVSM;
import cn.edu.whu.irlab.irep.service.retrievalModel.vsmmodel.ResultI;
import cn.edu.whu.irlab.irep.service.retrievalModel.vsmmodel.VSMRetriever;
import cn.edu.whu.irlab.irep.service.retrievalModel.vsmmodel.VectorI;
import cn.edu.whu.irlab.irep.service.util.Find;
import cn.edu.whu.irlab.irep.service.util.IndexTypeConstructor;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.lucene.analysis.Analyzer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/experiment")
public class ExperimentController {

    @Autowired
    public PreProcessor preProcessor;

    @Autowired
    public FullIndexServiceImpl fullIndexService;

    @Autowired
    public InvertedIndexServiceImpl invertedIndexService;

    @Autowired
    IndexTypeConstructor indexTypeConstructor;

    @Autowired
    public Find find;

    public String folderPath = "resources/doc_ch";

    /**
     * 中文预处理控制层
     *
     * @param token            待处理字符串
     * @param analyzerName     分词器名称
     * @param isRemoveStopWord 是否去停用词
     * @return 预处理结果
     */
    @RequestMapping("/preProcess")
    public String preProcessController(@RequestParam(name = "token") String token,
                                       @RequestParam(name = "analyzerName") String analyzerName,
                                       @RequestParam(name = "isRemoveStopWord") boolean isRemoveStopWord) {
        ArrayList<String> termList = preProcessor.preProcess(token, analyzerName, isRemoveStopWord);
        String string = termList.toString();
        return string;
    }

    public String preProcessEnController(@RequestParam(name = "token") String token,
                                         @RequestParam(name = "isRemoveStopWord") boolean isRemoveStopWord,
                                         @RequestParam(name = "isExtractStems") boolean isExtractStems) {
        String string = "";//待完善
        return string;
    }

    /**
     * @param analyzerName     分词器名称
     * @param isRemoveStopWord 是否去停用词
     * @return 全体倒排索引表
     */
    @RequestMapping("/fullIndex")
    public List<FullIndex> selectFullIndexController(@RequestParam(name = "analyzerName") String analyzerName,
                                                     @RequestParam(name = "isRemoveStopWord") boolean isRemoveStopWord) {
        String indexType = indexTypeConstructor.indexTypeConstructor(analyzerName, isRemoveStopWord);
        List<FullIndex> fullIndexList = fullIndexService.selectFullIndexByIndexType(indexType);
        if (fullIndexList == null) {
            IndexGenerator indexGenerator = new IndexGenerator(folderPath, analyzerName, isRemoveStopWord);
            indexGenerator.generateIndex();
            fullIndexList = fullIndexService.selectFullIndexByIndexType(indexType);
        }
        return fullIndexList;
    }

    //返回invertedIndex

    /**
     * @param analyzerName     分词器名称
     * @param isRemoveStopWord 是否去停用词
     * @param term             词项
     * @return 倒排索引表
     */
    @RequestMapping("/invertedIndex")
    public List<InvertedIndex> selectInvertedIndexController(@RequestParam(name = "analyzerName") String analyzerName,
                                                             @RequestParam(name = "isRemoveStopWord") boolean isRemoveStopWord,
                                                             @RequestParam(name = "term") String term) {
        String indexType = indexTypeConstructor.indexTypeConstructor(analyzerName, isRemoveStopWord);
        List<InvertedIndex> invertedIndexList = invertedIndexService.selectByIndexTypeAndTerm(indexType, term);
        return invertedIndexList;
    }

    /**
     * 返回文章
     *
     * @param docID 文章id
     * @return
     */
    public String selectDoc(@RequestParam(name = "docID") int docID) {
        return find.findDoc(docID, true);
    }

    /**
     * 向量空间模型 检索功能 控制层
     *
     * @param queryContent     查询语句
     * @param formulaID        TF计算公式ID
     * @param smoothParam      平滑系数
     * @param analyzerName     分词器名称
     * @param isRemoveStopWord 是否去停用词
     * @return 检索结果和各中间结果
     */
    @RequestMapping("/vsmSearch")
    public JSONObject vsmSearch(@RequestParam(name = "query") String queryContent,
                               @RequestParam(name = "formulaID") int formulaID,
                               @RequestParam(name = "smoothParam") double smoothParam,
                               @RequestParam(name = "analyzerName") String analyzerName,
                               @RequestParam(name = "isRemoveStopWord") boolean isRemoveStopWord) {
        JSONObject result=new JSONObject();

        //put 检索结果
        JSONArray searchResult = new JSONArray();
        VSMRetriever retriever = new VSMRetriever(queryContent, formulaID, smoothParam, analyzerName, isRemoveStopWord);
        retriever.search();
        List<ResultI> resultIList = retriever.getResultAfterSort();
        for (int i = 0; i < resultIList.size(); i++) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("title", resultIList.get(i).getTitle());
            jsonObject.put("content", find.findDoc(resultIList.get(i).getDocID(), true));
            searchResult.add(jsonObject);
        }
        result.put("searchResult",searchResult);

        //put IDF
        Map<String, Double> term_idf = retriever.getTerm_idf();
        result.put("idf",JSON.parseObject(JSON.toJSONString(term_idf)));

        //put 查询预处理结果
        JSONObject ppq = new JSONObject();
        ppq.put("查询语句", queryContent);
        ppq.put("预处理结果", retriever.getQuery().getPreProcessResult());
        result.put("PPQ",ppq);

        //put 查询的tf
        Map<String, Double> qtf = retriever.getQuery().getTfMap();
        result.put("qtf",qtf);

        //put 查询的vector
        List<VectorI> vector = retriever.getQuery().getVector();
        List<JSONObject> vectorOfQuery = new ArrayList<>();
        for (int i = 0; i < vector.size(); i++) {
            JSONObject jsonObject = new JSONObject(JSON.parseObject(vector.get(i).toString()));
            vectorOfQuery.add(jsonObject);
        }
        result.put("vectorOfQuery",vectorOfQuery);

        //put 文档的tf
        List<DocForVSM> docForVSMList=retriever.getDocForVSMList();
        List<JSONObject> tfsOfDocs=new ArrayList<>();
        for (int i = 0; i <docForVSMList.size() ; i++) {
            JSONObject jsonObject=new JSONObject();
            JSONObject tfsJson;
            jsonObject.put("title",find.findTitle(docForVSMList.get(i).getId(),true));
            tfsJson=JSON.parseObject(JSON.toJSONString(docForVSMList.get(i).getTfMap()));
            jsonObject.put("tfs",tfsJson);
            tfsOfDocs.add(jsonObject);
        }
        result.put("tfsOfDocs",tfsOfDocs);

        //put 文档的vector
        List<JSONObject> vectorsForDocs=new ArrayList<>();
        for (int i = 0; i <docForVSMList.size() ; i++) {
            JSONObject jsonObject=new JSONObject();
            JSONObject vectorJson;
            jsonObject.put("title",find.findTitle(docForVSMList.get(i).getId(),true));
            vectorJson=JSON.parseObject(JSON.toJSONString(docForVSMList.get(i).getVector()));
            jsonObject.put("tfs",vectorJson);
            vectorsForDocs.add(jsonObject);
        }

        //put 相似度
        List<ResultI> similarity=retriever.getResult();
        result.put("similarity",similarity);

        //put 排序后的相似度
        result.put("sortSimilarity",resultIList);
        return result;
    }




}
