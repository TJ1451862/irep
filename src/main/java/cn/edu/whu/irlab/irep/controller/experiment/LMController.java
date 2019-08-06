package cn.edu.whu.irlab.irep.controller.experiment;


import cn.edu.whu.irlab.irep.base.dao.experiment.impl.ResultServiceImpl;
import cn.edu.whu.irlab.irep.service.enums.ResponseEnum;
import cn.edu.whu.irlab.irep.service.experiment.retrieval.languagemodel.LMRetriever;
import cn.edu.whu.irlab.irep.service.util.Find;
import cn.edu.whu.irlab.irep.base.dao.experiment.impl.RetrieverServiceImpl;
import cn.edu.whu.irlab.irep.base.dao.system.impl.UserRetrieverServiceImpl;

import cn.edu.whu.irlab.irep.service.util.ResponseVoUtil;
import cn.edu.whu.irlab.irep.service.vo.ResponseVo;
import cn.edu.whu.irlab.irep.service.vo.ResultVo;
import cn.edu.whu.irlab.irep.service.vo.SearchResultVo;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * @author Jane
 * @version 1.0
 * @date 2010-07-13 15:32
 * @desc 语言模型交互层
 **/
@RestController
@RequestMapping(value = "IRforCN/Retrieval/languageModel")
public class LMController {

    @Autowired
    public LMRetriever languageRetriever;

    @Autowired
    public RetrieverServiceImpl retrieverService;

    @Autowired
    public UserRetrieverServiceImpl userRetrieverService;

    @Autowired
    public ResultServiceImpl resultService;

    /**
     * 返回检索结果
     *
     * @param query            检索式
     * @param smoothParam      平滑系数
     * @return 检索结果
     */
    @PostMapping("/search")
    public List<SearchResultVo> vsmSearchController(@RequestParam(name = "query") String query,
                                                    @RequestParam(name = "smoothParam") Double smoothParam,
                                                    HttpServletRequest request) {
        languageRetriever.initLMRetriever(query, smoothParam, request);
        return languageRetriever.search();
    }


    @PostMapping("/ppq")
    public JSONObject ppqController(@RequestParam(name = "query") String query,
                                    @RequestParam(name = "smoothParam") double smoothParam,
                                    HttpServletRequest request) {
        JSONObject ppq = new JSONObject();
        languageRetriever.initLMRetriever(query, smoothParam, request);
        ppq.put("query", query);
        ppq.put("result", languageRetriever.getQuery().getPreProcessResult());
        return ppq;
    }

    @PostMapping("/tfsOfDoc")
    public JSONObject getTfsOfDocsController(@RequestParam(name = "query") String query,
                                             @RequestParam(name = "smoothParam") double smoothParam,
                                             @RequestParam(name = "docId") int docId,
                                             HttpServletRequest request) {
        languageRetriever.initLMRetriever(query, smoothParam, request);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("title", Find.findTitle(docId, true));
        jsonObject.put("docId", docId);
        jsonObject.put("tfs", languageRetriever.calculateLM(docId));
        return jsonObject;
    }

    @PostMapping("/similarity")
    public List<ResultVo> getSimilarityController(@RequestParam(name = "query") String query,
                                                  @RequestParam(name = "smoothParam") double smoothParam,
                                                  HttpServletRequest request) {
        languageRetriever.initLMRetriever(query, smoothParam, request);
        return languageRetriever.calculateSimilarity();

    }

    @PostMapping("/descendOrderSimilarity")
    public List<ResultVo> descendOrderSimilarityController(@RequestParam(name = "query") String query,
                                                           @RequestParam(name = "smoothParam") double smoothParam,HttpServletRequest request) {
        languageRetriever.initLMRetriever(query, smoothParam, request);
        return languageRetriever.descendOrderSimilarity();
    }

    @PostMapping("/quit")
    public ResponseVo quitController(@RequestParam(name = "query") String query,
                                     @RequestParam(name = "smoothParam") Double smoothParam,
                                     HttpServletRequest request){
        languageRetriever.initLMRetriever(query, smoothParam, request);
        int state=languageRetriever.quit();
        if (state == 1) {
            return ResponseVoUtil.success();
        } else {
            return ResponseVoUtil.error(ResponseEnum.UNKNOW_ERROR);
        }
    }
}
