package cn.edu.whu.irlab.irep.controller.experiment;


import cn.edu.whu.irlab.irep.service.enums.ResponseEnum;
import cn.edu.whu.irlab.irep.service.experiment.retrieval.VsmRetrievalService;
import cn.edu.whu.irlab.irep.service.util.ResponseVoUtil;
import cn.edu.whu.irlab.irep.service.vo.*;
import cn.edu.whu.irlab.irep.base.entity.experiment.Result;
import cn.edu.whu.irlab.irep.base.dao.experiment.ResultService;
import cn.edu.whu.irlab.irep.base.dao.experiment.RetrieverService;
import cn.edu.whu.irlab.irep.base.dao.system.UserRetrieverService;
import cn.edu.whu.irlab.irep.service.util.Find;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    public ResultService resultService;

    @Autowired
    public VsmRetrievalService vsmRetriever;

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
                                                    @RequestParam(name = "smoothParam") Double smoothParam,
                                                    HttpServletRequest request) {
        vsmRetriever.initVSMRetriever(queryContent, formulaId, smoothParam, request);
        return vsmRetriever.search();
    }

    @PostMapping("/idf")
    public List<IdfVo> getIdfController(@RequestParam(name = "query") String queryContent,
                                        @RequestParam(name = "formulaId") int formulaId,
                                        @RequestParam(name = "smoothParam") Double smoothParam,
                                        HttpServletRequest request) {
        vsmRetriever.initVSMRetriever(queryContent, formulaId, smoothParam, request);
        return vsmRetriever.calculateIdf();
    }

    @PostMapping("/ppq")
    public JSONObject ppqController(@RequestParam(name = "query") String queryContent,
                                    @RequestParam(name = "formulaId") int formulaId,
                                    @RequestParam(name = "smoothParam") Double smoothParam,
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
                                                    @RequestParam(name = "smoothParam") Double smoothParam,
                                                    HttpServletRequest request) {
        vsmRetriever.initVSMRetriever(queryContent, formulaId, smoothParam, request);
        return vsmRetriever.calculateVectorOfQuery();

    }

    @PostMapping("/tfOfQuery")
    public List<TfVo> getQueryTfController(@RequestParam(name = "query") String queryContent,
                                           @RequestParam(name = "formulaId") int formulaId,
                                           @RequestParam(name = "smoothParam") Double smoothParam,
                                           HttpServletRequest request) {
        vsmRetriever.initVSMRetriever(queryContent, formulaId, smoothParam, request);
        return vsmRetriever.calculateTfOfQuery();
    }

    @PostMapping("/tfsOfDoc")
    public JSONObject getTfsOfDocsController(@RequestParam(name = "query") String queryContent,
                                             @RequestParam(name = "formulaId") int formulaId,
                                             @RequestParam(name = "smoothParam") Double smoothParam,
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
                                                 @RequestParam(name = "smoothParam") Double smoothParam,
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
                                                  @RequestParam(name = "smoothParam") Double smoothParam,
                                                  HttpServletRequest request) {
        vsmRetriever.initVSMRetriever(queryContent, formulaId, smoothParam, request);
        return vsmRetriever.calculateSimilarity();

    }

    @PostMapping("/descendOrderSimilarity")
    public List<ResultVo> getSimilarityAfterSortController(@RequestParam(name = "query") String queryContent,
                                                           @RequestParam(name = "formulaId") int formulaId,
                                                           @RequestParam(name = "smoothParam") Double smoothParam,
                                                           HttpServletRequest request) {
        vsmRetriever.initVSMRetriever(queryContent, formulaId, smoothParam, request);
        return vsmRetriever.descendOrderSimilarity();
    }

    @PostMapping("/testRetriever")
    public Map<String, List<Result>> testRetrieverController(@RequestParam(name = "query") String queryContent,
                                                             @RequestParam(name = "formulaId") int formulaId,
                                                             @RequestParam(name = "smoothParam") Double smoothParam,
                                                             HttpServletRequest request){
        vsmRetriever.initVSMRetriever(queryContent, formulaId, smoothParam, request);
        return vsmRetriever.testRetriever();
    }

    @PostMapping("/quit")
    public ResponseVo quitController(@RequestParam(name = "query") String queryContent,
                                     @RequestParam(name = "formulaId") int formulaId,
                                     @RequestParam(name = "smoothParam") Double smoothParam,
                                     HttpServletRequest request){
        vsmRetriever.initVSMRetriever(queryContent, formulaId, smoothParam, request);
        int state=vsmRetriever.quit();
        if (state == 1) {
            return ResponseVoUtil.success();
        } else {
            return ResponseVoUtil.error(ResponseEnum.UNKNOW_ERROR);
        }
    }
}
