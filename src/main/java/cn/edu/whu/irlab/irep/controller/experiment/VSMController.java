package cn.edu.whu.irlab.irep.controller.experiment;


import cn.edu.whu.irlab.irep.service.vo.IdfVo;
import cn.edu.whu.irlab.irep.service.vo.ResultVo;
import cn.edu.whu.irlab.irep.base.entity.Result;
import cn.edu.whu.irlab.irep.base.entity.Retriever;
import cn.edu.whu.irlab.irep.base.entity.User;
import cn.edu.whu.irlab.irep.base.entity.UserRetriever;
import cn.edu.whu.irlab.irep.base.dao.ResultService;
import cn.edu.whu.irlab.irep.base.dao.RetrieverService;
import cn.edu.whu.irlab.irep.base.dao.UserRetrieverService;
import cn.edu.whu.irlab.irep.service.experiment.retrieval.vsmModel.VSMRetriever;
import cn.edu.whu.irlab.irep.service.vo.VectorIVo;
import cn.edu.whu.irlab.irep.service.util.Find;
import cn.edu.whu.irlab.irep.service.util.Constructor;
import cn.edu.whu.irlab.irep.service.util.ReadDoc;
import cn.edu.whu.irlab.irep.service.vo.SearchResultVo;
import cn.edu.whu.irlab.irep.service.vo.TfVo;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
    public ResultService resultService;

    @Autowired
    public VSMRetriever vsmRetriever;

    @Autowired
    public RetrieverService retrieverService;

    @Autowired
    public UserRetrieverService userRetrieverService;

    /**
     * 返回检索结果
     *
     * @param queryContent 查询语句
     * @param formulaId    TF计算公式ID
     * @param smoothParam  平滑系数
     * @return 检索结果
     */
    @PostMapping("/search")
    public List<SearchResultVo> vsmSearchController(@RequestParam(name = "query") String queryContent,
                                                    @RequestParam(name = "formulaId") int formulaId,
                                                    @RequestParam(name = "smoothParam") double smoothParam,
                                                    HttpServletRequest request) {
        vsmRetriever.initVSMRetriever(queryContent, formulaId, smoothParam, request);
        return vsmRetriever.search();
    }

    @PostMapping("/idf")
    public List<IdfVo> getIdfController(@RequestParam(name = "query") String queryContent,
                                        @RequestParam(name = "formulaId") int formulaId,
                                        @RequestParam(name = "smoothParam") double smoothParam,
                                        HttpServletRequest request) {
        vsmRetriever.initVSMRetriever(queryContent, formulaId, smoothParam, request);
        return vsmRetriever.getIdfVoList();
    }

    @PostMapping("/ppq")
    public JSONObject ppqController(@RequestParam(name = "query") String queryContent,
                                    @RequestParam(name = "formulaId") int formulaId,
                                    @RequestParam(name = "smoothParam") double smoothParam,
                                    HttpServletRequest request) {
        JSONObject ppq = new JSONObject();
        vsmRetriever.initVSMRetriever(queryContent, formulaId, smoothParam, request);
        ppq.put("query", queryContent);
        ppq.put("result", vsmRetriever.getQuery().getPreProcessResult());
        return ppq;
    }

    @PostMapping("/vectorOfQuery")
    public List<VectorIVo> getQueryVectorController(@RequestParam(name = "query") String queryContent,
                                                    @RequestParam(name = "formulaId") int formulaId,
                                                    @RequestParam(name = "smoothParam") double smoothParam,
                                                    HttpServletRequest request) {
        vsmRetriever.initVSMRetriever(queryContent, formulaId, smoothParam, request);
        return vsmRetriever.calculateVectorOfQuery();

    }

    @PostMapping("/tfOfQuery")
    public List<TfVo> getQueryTfController(@RequestParam(name = "query") String queryContent,
                                           @RequestParam(name = "formulaId") int formulaId,
                                           @RequestParam(name = "smoothParam") double smoothParam,
                                           HttpServletRequest request) {
        vsmRetriever.initVSMRetriever(queryContent, formulaId, smoothParam, request);
        return vsmRetriever.calculateTfOfQuery();
    }

    @PostMapping("/tfsOfDoc")
    public JSONObject getTfsOfDocsController(@RequestParam(name = "query") String queryContent,
                                             @RequestParam(name = "formulaId") int formulaId,
                                             @RequestParam(name = "smoothParam") double smoothParam,
                                             @RequestParam(name = "docId") int docId,
                                             HttpServletRequest request) {
        vsmRetriever.initVSMRetriever(queryContent, formulaId, smoothParam, request);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("title", Find.findTitle(docId, true));
        jsonObject.put("docId", docId);
        jsonObject.put("tfs", vsmRetriever.calculateTfOfDoc(docId));
        return jsonObject;
    }

    @PostMapping("vectorOfDoc")
    public JSONObject getVectorsOfDocsController(@RequestParam(name = "query") String queryContent,
                                                 @RequestParam(name = "formulaId") int formulaId,
                                                 @RequestParam(name = "smoothParam") double smoothParam,
                                                 @RequestParam(name = "docId") int docId,
                                                 HttpServletRequest request) {
        vsmRetriever.initVSMRetriever(queryContent, formulaId, smoothParam, request);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("title", Find.findTitle(docId, true));
        jsonObject.put("docId", docId);
        jsonObject.put("vector", vsmRetriever.calculateVectorOfDoc(docId));
        return jsonObject;
    }

    @PostMapping("/similarity")
    public List<ResultVo> getSimilarityController(@RequestParam(name = "query") String queryContent,
                                                  @RequestParam(name = "formulaId") int formulaId,
                                                  @RequestParam(name = "smoothParam") double smoothParam,
                                                  HttpServletRequest request) {
        vsmRetriever.initVSMRetriever(queryContent, formulaId, smoothParam, request);
        return vsmRetriever.calculateSimilarity();

    }

    @PostMapping("/descendOrderSimilarity")
    public List<ResultVo> getSimilarityAfterSortController(@RequestParam(name = "query") String queryContent,
                                                           @RequestParam(name = "formulaId") int formulaId,
                                                           @RequestParam(name = "smoothParam") double smoothParam,
                                                           HttpServletRequest request) {
        vsmRetriever.initVSMRetriever(queryContent, formulaId, smoothParam, request);
        return vsmRetriever.descendOrderSimilarity();
    }

    /**
     * 向result表中插入结果数据
     * 如果数据不存在则插入，如果数据存在则不插入
     */
    @PostMapping("/insertResult")
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
                List<ResultVo> resultAfterSort = vsmRetriever.descendOrderSimilarity();
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

}
