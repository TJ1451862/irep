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
    public Find find;

    @Autowired
    public ReadDoc readDoc;


    @Autowired
    public ResultServiceImpl resultService;

    /**
     * 向量空间模型 检索功能 控制层
     *
     * @param queryContent     查询语句
     * @param formulaId        TF计算公式ID
     * @param smoothParam      平滑系数
     * @param analyzerName     分词器名称
     * @param isRemoveStopWord 是否去停用词
     * @return 检索结果和各中间结果
     */
    @ResponseBody
    @RequestMapping("/vsmSearch")
    public JSONObject vsmSearch(@RequestParam(name = "query") String queryContent,
                                @RequestParam(name = "formulaId") int formulaId,
                                @RequestParam(name = "smoothParam") double smoothParam,
                                @RequestParam(name = "analyzerName") String analyzerName,
                                @RequestParam(name = "isRemoveStopWord") boolean isRemoveStopWord) {
        JSONObject result = new JSONObject();
        //put 检索结果
        JSONArray searchResult = new JSONArray();
        VSMRetriever retriever = new VSMRetriever(queryContent, formulaId, smoothParam, analyzerName, isRemoveStopWord);
        retriever.search();

        List<ResultI> resultIList = retriever.getResultAfterSort();
        for (int i = 0; i < resultIList.size(); i++) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("title", resultIList.get(i).getTitle());
            jsonObject.put("content", find.findDoc(resultIList.get(i).getDocID(), true));
            searchResult.add(jsonObject);
        }
        result.put("searchResult", searchResult);

        //put IDF
        Map<String, Double> term_idf = retriever.getTerm_idf();
        result.put("idf", JSON.parseObject(JSON.toJSONString(term_idf)));

        //put 查询预处理结果
        JSONObject ppq = new JSONObject();
        ppq.put("查询语句", queryContent);
        ppq.put("预处理结果", retriever.getQuery().getPreProcessResult());
        result.put("PPQ", ppq);

        //put 查询的tf
        Map<String, Double> qtf = retriever.getQuery().getTfMap();
        result.put("qtf", qtf);

        //put 查询的vector
        List<VectorI> vector = retriever.getQuery().getVector();
        List<JSONObject> vectorOfQuery = new ArrayList<>();
        for (int i = 0; i < vector.size(); i++) {
            JSONObject jsonObject = new JSONObject(JSON.parseObject(vector.get(i).toString()));
            vectorOfQuery.add(jsonObject);
        }
        result.put("vectorOfQuery", vectorOfQuery);

        //put 文档的tf
        List<DocForVSM> docForVSMList = retriever.getDocForVSMList();
        List<JSONObject> tfsOfDocs = new ArrayList<>();
        for (int i = 0; i < docForVSMList.size(); i++) {
            JSONObject jsonObject = new JSONObject();
            JSONObject tfsJson;
            jsonObject.put("title", find.findTitle(docForVSMList.get(i).getId(), true));
            tfsJson = JSON.parseObject(JSON.toJSONString(docForVSMList.get(i).getTfMap()));
            jsonObject.put("tfs", tfsJson);
            tfsOfDocs.add(jsonObject);
        }
        result.put("tfsOfDocs", tfsOfDocs);

        //put 文档的vector
        List<JSONObject> vectorsForDocs = new ArrayList<>();
        for (int i = 0; i < docForVSMList.size(); i++) {
            JSONObject jsonObject = new JSONObject();
            JSONObject vectorJson;
            jsonObject.put("title", find.findTitle(docForVSMList.get(i).getId(), true));
            vectorJson = JSON.parseObject(JSON.toJSONString(docForVSMList.get(i).getVector()));
            jsonObject.put("tfs", vectorJson);
            vectorsForDocs.add(jsonObject);
        }

        //put 相似度
        List<ResultI> similarity = retriever.getResult();
        result.put("similarity", similarity);

        //put 排序后的相似度
        result.put("sortSimilarity", resultIList);
        return result;
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
}
