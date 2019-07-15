package cn.edu.whu.irlab.irep.controller;


import cn.edu.whu.irlab.irep.entity.Result;
import cn.edu.whu.irlab.irep.entity.Retriever;
import cn.edu.whu.irlab.irep.entity.User;
import cn.edu.whu.irlab.irep.entity.UserRetriever;
import cn.edu.whu.irlab.irep.service.impl.ResultServiceImpl;
import cn.edu.whu.irlab.irep.service.impl.RetrieverServiceImpl;
import cn.edu.whu.irlab.irep.service.impl.UserRetrieverServiceImpl;
import cn.edu.whu.irlab.irep.service.retrievalModel.vsmModel.DocForVSM;
import cn.edu.whu.irlab.irep.service.retrievalModel.vsmModel.ResultI;
import cn.edu.whu.irlab.irep.service.retrievalModel.vsmModel.VSMRetriever;
import cn.edu.whu.irlab.irep.service.retrievalModel.vsmModel.VectorI;
import cn.edu.whu.irlab.irep.service.util.Find;
import cn.edu.whu.irlab.irep.service.util.Constructor;
import cn.edu.whu.irlab.irep.service.util.ReadDoc;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author gcr
 * @version 1.0
 * @date 2018-10-30 9:58
 * @desc 向量空间模型交互层
 **/
@Controller
@RequestMapping(value = "IRforCN/Retrieval/vectorSpaceModel")
public class VSMController {

    @Autowired
    public ResultServiceImpl resultService;

    @Autowired
    public VSMRetriever vsmRetriever;

    @Autowired
    public RetrieverServiceImpl retrieverService;

    @Autowired
    public UserRetrieverServiceImpl userRetrieverService;

    HttpServletRequest request;

    /**
     * 返回检索结果
     *
     * @param queryContent 查询语句
     * @param formulaId    TF计算公式ID
     * @param smoothParam  平滑系数
     * @return 检索结果
     */
    @ResponseBody
    @RequestMapping("/vsmSearch")
    public JSONArray vsmSearchController(@RequestParam(name = "query") String queryContent,
                                         @RequestParam(name = "formulaId") int formulaId,
                                         @RequestParam(name = "smoothParam") double smoothParam,
                                         HttpServletRequest request) {
        isNeedSearch(queryContent, formulaId, smoothParam, request);
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
                                                @RequestParam(name = "isRemoveStopWord") boolean isRemoveStopWord,
                                                HttpServletRequest request) {
        isNeedSearch(queryContent, formulaId, smoothParam, request);
        return vsmRetriever.getTerm_idf();
    }

    @ResponseBody
    @RequestMapping("/ppq")
    public JSONObject ppqController(@RequestParam(name = "query") String queryContent,
                                    @RequestParam(name = "formulaId") int formulaId,
                                    @RequestParam(name = "smoothParam") double smoothParam,
                                    HttpServletRequest request) {
        JSONObject ppq = new JSONObject();
        isNeedSearch(queryContent, formulaId, smoothParam, request);
        ppq.put("query", queryContent);
        ppq.put("result", vsmRetriever.getQuery().getPreProcessResult());
        return ppq;
    }

    @ResponseBody
    @RequestMapping("/tfOfQuery")
    public Map<String, Double> getQueryTfController(@RequestParam(name = "query") String queryContent,
                                                    @RequestParam(name = "formulaId") int formulaId,
                                                    @RequestParam(name = "smoothParam") double smoothParam,
                                                    HttpServletRequest request) {
        isNeedSearch(queryContent, formulaId, smoothParam, request);
        return vsmRetriever.getQuery().getTfMap();
    }

    @ResponseBody
    @RequestMapping("/vectorOfQuery")
    public List<JSONObject> getQueryVectorController(@RequestParam(name = "query") String queryContent,
                                                     @RequestParam(name = "formulaId") int formulaId,
                                                     @RequestParam(name = "smoothParam") double smoothParam,
                                                     HttpServletRequest request) {
        isNeedSearch(queryContent, formulaId, smoothParam, request);
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
                                                   HttpServletRequest request) {
        isNeedSearch(queryContent, formulaId, smoothParam, request);
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
                                                       HttpServletRequest request) {
        isNeedSearch(queryContent, formulaId, smoothParam, request);
        List<JSONObject> vectorsForDocs = new ArrayList<>();
        List<DocForVSM> docForVSMList = vsmRetriever.getDocForVSMList();
        for (int i = 0; i < docForVSMList.size(); i++) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("title", Find.findTitle(docForVSMList.get(i).getId(), true));
            jsonObject.put("docId", docForVSMList.get(i).getId());
            JSONArray vectorJson = new JSONArray();
            List<VectorI> docVector = docForVSMList.get(i).getVector();
            for (int j = 0; j < docVector.size(); j++) {
                VectorI vectorI = docVector.get(j);
                JSONObject object = JSONObject.parseObject(JSONObject.toJSONString(vectorI));
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
                                                 HttpServletRequest request) {
        isNeedSearch(queryContent, formulaId, smoothParam, request);
        return vsmRetriever.getResult();

    }

    @ResponseBody
    @RequestMapping("/similarityAfterSort")
    public List<ResultI> getSimilarityAfterSortController(@RequestParam(name = "query") String queryContent,
                                                          @RequestParam(name = "formulaId") int formulaId,
                                                          @RequestParam(name = "smoothParam") double smoothParam,
                                                          @RequestParam(name = "analyzerName") String analyzerName,
                                                          @RequestParam(name = "isRemoveStopWord") boolean isRemoveStopWord,
                                                          HttpServletRequest request) {
        isNeedSearch(queryContent, formulaId, smoothParam, request);
        return vsmRetriever.getResultAfterSort();
    }

    /**
     * 向result表中插入结果数据
     * 如果数据不存在则插入，如果数据存在则不插入
     */
    @RequestMapping("/insertResult")
    @ResponseBody
    public ModelMap insertResultController(@RequestParam(name = "formulaId") int formulaId,
                                           @RequestParam(name = "smoothParam") double smoothParam,
                                           @RequestParam(name = "analyzerName") String analyzerName,
                                           @RequestParam(name = "isRemoveStopWord") boolean isRemoveStopWord,
                                           HttpServletRequest request) {

        String standardQuery = ReadDoc.readDoc("resources/results/standardQuery.json");

        //初始化retriever
        Retriever retriever = new Retriever();
        retriever.setIsChinese(true);
        retriever.setAnalyzer(analyzerName);
        retriever.setIsRemoveStopWord(isRemoveStopWord);
        retriever.setModel("vsm");
        retriever.setFormulaId(formulaId);
        retriever.setParamName1("平滑系数");
        retriever.setParam1((int) (smoothParam * 100));
        String retrieverId = Constructor.retrieverIdConstructor(retriever);
        retriever.setRetrieverId(retrieverId);

        //检查数据库中是否已经存在标准查询的检索结果数据，如果没有则生成并插入
        Retriever retriever1 = retrieverService.selectByPrimaryKey(retrieverId);
        if (retriever1 == null) {
            //插入retriever
            retrieverService.insert(retriever);
            JSONArray queryList = JSONArray.parseArray(standardQuery);

            for (int i = 0; i < queryList.size(); i++) {
                String queryContent = queryList.getJSONObject(i).getString("query");
                int queryId = queryList.getJSONObject(i).getIntValue("queryId");
                vsmRetriever.initVSMRetriever(queryContent, formulaId, smoothParam, request);
                vsmRetriever.search();
                List<ResultI> resultAfterSort = vsmRetriever.getResultAfterSort();
                for (int j = 0; j < resultAfterSort.size(); j++) {
                    Result result = new Result();
                    result.setDocId(resultAfterSort.get(j).getDocID());
                    result.setDocRank(j);
                    result.setQuery(queryContent);
                    result.setQueryId(queryId);
                    result.setTitle(resultAfterSort.get(j).getTitle());
                    result.setRetrieverId(retrieverId);
                    resultService.insertSelective(result);
                }
            }
        }

        //保存检索器
        ModelMap modelMap = new ModelMap();
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            int userId = user.getId();
            int retrieverNum = 0;
            int state = 0;
            UserRetriever userRetriever = userRetrieverService.selectByPrimaryKey(userId);
            if (userRetriever == null) {
                userRetriever = new UserRetriever();
                userRetriever.setUserId(userId);
                userRetriever.setRetriever1(retrieverId);
                state = userRetrieverService.insertSelective(userRetriever);
            } else {
                switch (retrieverNum) {
                    case 1:
                        userRetriever.setRetriever1(retrieverId);
                        break;
                    case 2:
                        userRetriever.setRetriever2(retrieverId);
                        break;
                    case 3:
                        userRetriever.setRetriever3(retrieverId);
                        break;
                    default:
                        userRetriever.setRetriever1(retrieverId);
                        break;
                }
                state = userRetrieverService.updateByPrimaryKeySelective(userRetriever);
            }
            if (state == 1) {
                modelMap.put("code", 1);
                modelMap.put("message", "保存成功");
            } else {
                modelMap.put("code", 0);
                modelMap.put("message", "保存失败");
            }
        }

        return modelMap;
    }

    /**
     * 判断是否需要检索
     *
     * @param queryContent     查询语句
     * @param formulaId        TF计算公式ID
     * @param smoothParam      平滑系数
     */
    public void isNeedSearch(@RequestParam(name = "query") String queryContent,
                             @RequestParam(name = "formulaId") int formulaId,
                             @RequestParam(name = "smoothParam") double smoothParam,
                             HttpServletRequest request) {
        if (vsmRetriever.getResult().size() == 0 ||
                !vsmRetriever.getQuery().getContent().equals(queryContent) ||
                vsmRetriever.getFormulaID() != formulaId ||
                vsmRetriever.getSmoothParam() != smoothParam) {
            vsmRetriever.initVSMRetriever(queryContent, formulaId, smoothParam, request);
            vsmRetriever.search();
        }
    }
}
