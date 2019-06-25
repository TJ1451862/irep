package cn.edu.whu.irlab.irep.controller;


import cn.edu.whu.irlab.irep.entity.Result;
import cn.edu.whu.irlab.irep.service.impl.ResultServiceImpl;
import cn.edu.whu.irlab.irep.service.retrievalModel.vsmmodel.DocForVSM;
import cn.edu.whu.irlab.irep.service.retrievalModel.vsmmodel.ResultI;
import cn.edu.whu.irlab.irep.service.retrievalModel.vsmmodel.VSMRetriever;
import cn.edu.whu.irlab.irep.service.retrievalModel.vsmmodel.VectorI;
import cn.edu.whu.irlab.irep.service.util.Find;
import cn.edu.whu.irlab.irep.service.util.IndexTypeConstructor;
import cn.edu.whu.irlab.irep.service.util.ReadDoc;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author gcr
 * @version 1.0
 * @date 2018-10-30 9:58
 * @desc 向量空间模型交互层
 **/
@RestController
@RequestMapping(value = "IRforCN/Retrieval/vectorSpaceModel")
public class VSMController {

    @Autowired
    public ResultServiceImpl resultService;

    @Autowired
    public VSMRetriever vsmRetriever;

    /**
     * 返回检索结果
     *
     * @param queryContent     查询语句
     * @param formulaId        TF计算公式ID
     * @param smoothParam      平滑系数
     * @param analyzerName     分词器名称
     * @param isRemoveStopWord 是否去停用词
     * @return 检索结果
     */
    @ResponseBody
    @RequestMapping("/vsmSearch")
    public JSONArray vsmSearchController(@RequestParam(name = "query") String queryContent,
                                         @RequestParam(name = "formulaId") int formulaId,
                                         @RequestParam(name = "smoothParam") double smoothParam,
                                         @RequestParam(name = "analyzerName") String analyzerName,
                                         @RequestParam(name = "isRemoveStopWord") boolean isRemoveStopWord) {
        isNeedSearch(queryContent, formulaId, smoothParam, analyzerName, isRemoveStopWord);
        JSONArray searchResult = new JSONArray();
        List<ResultI> resultIList = vsmRetriever.getResultAfterSort();
        for (int i = 0; i < resultIList.size(); i++) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("title", resultIList.get(i).getTitle());
            jsonObject.put("content", Find.findDoc(resultIList.get(i).getDocID(), true));
            searchResult.add(jsonObject);
        }
        return searchResult;
    }

    @ResponseBody
    @RequestMapping("/idf")
    public Map<String, Double> getIdfController(@RequestParam(name = "query") String queryContent,
                                                @RequestParam(name = "formulaId") int formulaId,
                                                @RequestParam(name = "smoothParam") double smoothParam,
                                                @RequestParam(name = "analyzerName") String analyzerName,
                                                @RequestParam(name = "isRemoveStopWord") boolean isRemoveStopWord) {
        isNeedSearch(queryContent, formulaId, smoothParam, analyzerName, isRemoveStopWord);
        return vsmRetriever.getTerm_idf();
    }

    @ResponseBody
    @RequestMapping("/ppq")
    public JSONObject ppqController(@RequestParam(name = "query") String queryContent,
                                    @RequestParam(name = "formulaId") int formulaId,
                                    @RequestParam(name = "smoothParam") double smoothParam,
                                    @RequestParam(name = "analyzerName") String analyzerName,
                                    @RequestParam(name = "isRemoveStopWord") boolean isRemoveStopWord) {
        JSONObject ppq = new JSONObject();
        isNeedSearch(queryContent, formulaId, smoothParam, analyzerName, isRemoveStopWord);
        ppq.put("query", queryContent);
        ppq.put("result", vsmRetriever.getQuery().getPreProcessResult());
        return ppq;
    }

    @ResponseBody
    @RequestMapping("/tfOfQuery")
    public Map<String, Double> getQueryTfController(@RequestParam(name = "query") String queryContent,
                                                    @RequestParam(name = "formulaId") int formulaId,
                                                    @RequestParam(name = "smoothParam") double smoothParam,
                                                    @RequestParam(name = "analyzerName") String analyzerName,
                                                    @RequestParam(name = "isRemoveStopWord") boolean isRemoveStopWord) {
        isNeedSearch(queryContent, formulaId, smoothParam, analyzerName, isRemoveStopWord);
        return vsmRetriever.getQuery().getTfMap();
    }

    @ResponseBody
    @RequestMapping("/vectorOfQuery")
    public List<JSONObject> getQueryVectorController(@RequestParam(name = "query") String queryContent,
                                                     @RequestParam(name = "formulaId") int formulaId,
                                                     @RequestParam(name = "smoothParam") double smoothParam,
                                                     @RequestParam(name = "analyzerName") String analyzerName,
                                                     @RequestParam(name = "isRemoveStopWord") boolean isRemoveStopWord) {
        isNeedSearch(queryContent, formulaId, smoothParam, analyzerName, isRemoveStopWord);
        List<VectorI> vector = vsmRetriever.getQuery().getVector();
        List<JSONObject> vectorOfQuery = new ArrayList<>();
        for (int i = 0; i < vector.size(); i++) {
            JSONObject jsonObject = new JSONObject(JSON.parseObject(JSON.toJSONString(vector.get(i))));
            vectorOfQuery.add(jsonObject);
        }
        return vectorOfQuery;
    }

    @ResponseBody
    @RequestMapping("/tfsOfDocs")
    public List<JSONObject> getTfsOfDocsController(@RequestParam(name = "query") String queryContent,
                                                   @RequestParam(name = "formulaId") int formulaId,
                                                   @RequestParam(name = "smoothParam") double smoothParam,
                                                   @RequestParam(name = "analyzerName") String analyzerName,
                                                   @RequestParam(name = "isRemoveStopWord") boolean isRemoveStopWord) {
        isNeedSearch(queryContent, formulaId, smoothParam, analyzerName, isRemoveStopWord);
        List<DocForVSM> docForVSMList = vsmRetriever.getDocForVSMList();
        List<JSONObject> tfsOfDocs = new ArrayList<>();
        for (int i = 0; i < docForVSMList.size(); i++) {
            JSONObject jsonObject = new JSONObject();
            JSONObject tfsJson;
            jsonObject.put("title", Find.findTitle(docForVSMList.get(i).getId(), true));
            jsonObject.put("docId", docForVSMList.get(i).getId());
            tfsJson = JSON.parseObject(JSON.toJSONString(docForVSMList.get(i).getTfMap()));
            jsonObject.put("tfs", tfsJson);
            tfsOfDocs.add(jsonObject);
        }
        return tfsOfDocs;
    }

    @ResponseBody
    @RequestMapping("vectorsOfDocs")
    public List<JSONObject> getVectorsOfDocsController(@RequestParam(name = "query") String queryContent,
                                                       @RequestParam(name = "formulaId") int formulaId,
                                                       @RequestParam(name = "smoothParam") double smoothParam,
                                                       @RequestParam(name = "analyzerName") String analyzerName,
                                                       @RequestParam(name = "isRemoveStopWord") boolean isRemoveStopWord) {
        isNeedSearch(queryContent, formulaId, smoothParam, analyzerName, isRemoveStopWord);
        List<JSONObject> vectorsForDocs = new ArrayList<>();
        List<DocForVSM> docForVSMList = vsmRetriever.getDocForVSMList();
        for (int i = 0; i < docForVSMList.size(); i++) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("title", Find.findTitle(docForVSMList.get(i).getId(), true));
            jsonObject.put("docId", docForVSMList.get(i).getId());
            JSONArray vectorJson = new JSONArray();
            List<VectorI> docVector = docForVSMList.get(i).getVector();
            for (int j = 0; j < docVector.size(); j++) {
                VectorI vectorI=docVector.get(j);
                JSONObject object =JSONObject.parseObject(JSONObject.toJSONString(vectorI));
                vectorJson.add(object);
            }
            jsonObject.put("vector", vectorJson);
            vectorsForDocs.add(jsonObject);
        }
        return vectorsForDocs;
    }

    @ResponseBody
    @RequestMapping("/similarity")
    public List<ResultI> getSimilarityController(@RequestParam(name = "query") String queryContent,
                                                 @RequestParam(name = "formulaId") int formulaId,
                                                 @RequestParam(name = "smoothParam") double smoothParam,
                                                 @RequestParam(name = "analyzerName") String analyzerName,
                                                 @RequestParam(name = "isRemoveStopWord") boolean isRemoveStopWord) {
        isNeedSearch(queryContent, formulaId, smoothParam, analyzerName, isRemoveStopWord);
        return vsmRetriever.getResult();

    }

    @ResponseBody
    @RequestMapping("/similarityAfterSort")
    public List<ResultI> getSimilarityAfterSortController(@RequestParam(name = "query") String queryContent,
                                                          @RequestParam(name = "formulaId") int formulaId,
                                                          @RequestParam(name = "smoothParam") double smoothParam,
                                                          @RequestParam(name = "analyzerName") String analyzerName,
                                                          @RequestParam(name = "isRemoveStopWord") boolean isRemoveStopWord) {
        isNeedSearch(queryContent, formulaId, smoothParam, analyzerName, isRemoveStopWord);
        return vsmRetriever.getResultAfterSort();
    }

    //    public void insertResult(@RequestParam(name = "formulaId") int formulaId,
//                             @RequestParam(name = "smoothParam") double smoothParam,
//                             @RequestParam(name = "analyzerName") String analyzerName,
//                             @RequestParam(name = "isRemoveStopWord") boolean isRemoveStopWord) {
//        String standardQuery = readDoc.readDoc("resources/results/standardQuery");
//        String indexType = indexTypeConstructor.indexTypeConstructor(analyzerName, isRemoveStopWord);
//        String modelType;
//        JSONArray queryList = JSONArray.parseArray(standardQuery);
//        for (int i = 0; i < queryList.size(); i++) {
//            String queryContent=queryList.getJSONObject(i).getString("query");
//            int queryId=queryList.getJSONObject(i).getIntValue("queryId");
//            VSMRetriever retriever = new VSMRetriever(queryContent, formulaId, smoothParam, analyzerName, isRemoveStopWord);
//            modelType=retriever.modelTypeConstuctor().toJSONString();
//            retriever.search();
//            List<ResultI> resultAfterSort = retriever.getResultAfterSort();
//            for (int j = 0; j < resultAfterSort.size(); j++) {
//                Result result = new Result();
//                result.setIndexType(indexType);
//                result.setDocId(resultAfterSort.get(i).getDocID());
//                result.setDocRank(j);
//                result.setIsChinese(1);
//                result.setQuery(queryContent);
//                result.setQueryId(queryId);
//                result.setTitle(resultAfterSort.get(i).getTitle());
//                result.setModelType(modelType);
//                resultService.insertSelective(result);
//            }
//        }
//    }

    /**
     * 判断是否需要检索
     *
     * @param queryContent     查询语句
     * @param formulaId        TF计算公式ID
     * @param smoothParam      平滑系数
     * @param analyzerName     分词器名称
     * @param isRemoveStopWord 是否去停用词
     */
    public void isNeedSearch(@RequestParam(name = "query") String queryContent,
                             @RequestParam(name = "formulaId") int formulaId,
                             @RequestParam(name = "smoothParam") double smoothParam,
                             @RequestParam(name = "analyzerName") String analyzerName,
                             @RequestParam(name = "isRemoveStopWord") boolean isRemoveStopWord) {
        if (vsmRetriever.getResult().size() == 0 || !vsmRetriever.getQuery().getContent().equals(queryContent)) {
            vsmRetriever.initVSMRetriever(queryContent, formulaId, smoothParam, analyzerName, isRemoveStopWord);
            vsmRetriever.search();
        }
    }
}
