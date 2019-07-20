package cn.edu.whu.irlab.irep.controller.experiment;

import cn.edu.whu.irlab.irep.service.experiment.retrieval.ProbabilityRetrievalService;
import cn.edu.whu.irlab.irep.service.vo.BijVo;
import cn.edu.whu.irlab.irep.service.vo.ResultVo;
import cn.edu.whu.irlab.irep.service.vo.SearchResultVo;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author gcr19
 * @date 2019-07-19 23:47
 * @desc 概率检索模型 交互层
 **/
@Controller
@RequestMapping("IRforCN/Retrieval/probabilityModel")
public class ProbabilityRetrieverController {

    @Autowired
    private ProbabilityRetrievalService probabilityRetrievalService;

    /**
     * 检索
     *
     * @param query   查询
     * @param k       参数K
     * @param b       参数b
     * @param request HttpServletRequest
     * @return 检索结果
     */
    @RequestMapping("/search")
    @ResponseBody
    public List<SearchResultVo> searchController(@RequestParam("query") String query,
                                                 @RequestParam("k") double k,
                                                 @RequestParam("b") double b,
                                                 HttpServletRequest request) {
        probabilityRetrievalService.initRetriever(query, k, b, request);
        return probabilityRetrievalService.search();
    }

    /**
     * 求bij
     *
     * @param query   查询
     * @param k       参数K
     * @param b       参数b
     * @param request HttpServletRequest
     * @return bij计算结果
     */
    @RequestMapping("/bij")
    @ResponseBody
    public List<BijVo> calculateBijController(@RequestParam("query") String query,
                                              @RequestParam("k") double k,
                                              @RequestParam("b") double b,
                                              HttpServletRequest request) {
        probabilityRetrievalService.initRetriever(query, k, b, request);
        return probabilityRetrievalService.calculateBijs();
    }

    @ResponseBody
    @RequestMapping("/ppq")
    public JSONObject ppqController(@RequestParam("query") String query,
                                    @RequestParam("k") double k,
                                    @RequestParam("b") double b,
                                    HttpServletRequest request) {
        JSONObject ppq = new JSONObject();
        probabilityRetrievalService.initRetriever(query, k, b, request);
        ppq.put("query", query);
        ppq.put("result", probabilityRetrievalService.getQuery().getPreProcessResult());
        return ppq;
    }

    /**
     * 计算相似度
     *
     * @param query   查询
     * @param k       参数K
     * @param b       参数b
     * @param request HttpServletRequest
     * @return 相似度计算结果
     */
    @RequestMapping("/similarity")
    @ResponseBody
    public List<ResultVo> calculateSimilaritiesController(@RequestParam("query") String query,
                                                          @RequestParam("k") double k,
                                                          @RequestParam("b") double b,
                                                          HttpServletRequest request) {
        probabilityRetrievalService.initRetriever(query, k, b, request);
        return probabilityRetrievalService.calculateSimilarities();
    }

    /**
     * 按相似度降序排列
     *
     * @param query   查询
     * @param k       参数K
     * @param b       参数b
     * @param request HttpServletRequest
     * @return 排序后的相似度结果
     */
    @RequestMapping("/descendOrderSimilarity")
    @ResponseBody
    public List<ResultVo> descendOrderSimilaritiesController(@RequestParam("query") String query,
                                                             @RequestParam("k") double k,
                                                             @RequestParam("b") double b,
                                                             HttpServletRequest request) {
        probabilityRetrievalService.initRetriever(query, k, b, request);
        return probabilityRetrievalService.descendOrderSimilarities();
    }


}
