package cn.edu.whu.irlab.irep.controller.experiment;

import cn.edu.whu.irlab.irep.base.entity.experiment.Result;
import cn.edu.whu.irlab.irep.service.enums.ResponseEnum;
import cn.edu.whu.irlab.irep.service.experiment.retrieval.ProbabilityRetrievalService;
import cn.edu.whu.irlab.irep.service.util.ResponseVoUtil;
import cn.edu.whu.irlab.irep.service.vo.BijVo;
import cn.edu.whu.irlab.irep.service.vo.ResponseVo;
import cn.edu.whu.irlab.irep.service.vo.ResultVo;
import cn.edu.whu.irlab.irep.service.vo.SearchResultVo;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author gcr19
 * @date 2019-07-19 23:47
 * @desc 概率检索模型 交互层
 **/
@RestController
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
    @PostMapping("/search")
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
    @PostMapping("/bij")
    public List<BijVo> calculateBijController(@RequestParam("query") String query,
                                              @RequestParam("k") double k,
                                              @RequestParam("b") double b,
                                              HttpServletRequest request) {
        probabilityRetrievalService.initRetriever(query, k, b, request);
        return probabilityRetrievalService.calculateBijs();
    }

    @PostMapping("/ppq")
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
    @PostMapping("/similarity")
    public List<ResultVo> calculateSimilaritiesController(@RequestParam("query") String query,
                                                          @RequestParam("k") double k,
                                                          @RequestParam("b") double b,
                                                          HttpServletRequest request) {
        probabilityRetrievalService.initRetriever(query, k, b, request);
        return probabilityRetrievalService.calculateSimilarity();
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
    @PostMapping("/descendOrderSimilarity")
    public List<ResultVo> descendOrderSimilaritiesController(@RequestParam("query") String query,
                                                             @RequestParam("k") double k,
                                                             @RequestParam("b") double b,
                                                             HttpServletRequest request) {
        probabilityRetrievalService.initRetriever(query, k, b, request);
        return probabilityRetrievalService.descendOrderSimilarity();
    }

    /**
     * 调试检索模型
     * @param query
     * @param k
     * @param b
     * @param request
     * @return
     */
    @PostMapping("/testRetriever")
    public Map<String, List<Result>> testRetrieverController(@RequestParam("query") String query,
                                                             @RequestParam("k") double k,
                                                             @RequestParam("b") double b,
                                                             HttpServletRequest request){
        probabilityRetrievalService.initRetriever(query, k, b, request);
        return probabilityRetrievalService.testRetriever();
    }


    @PostMapping("/quit")
    public ResponseVo quitController(@RequestParam("query") String query,
                                     @RequestParam("k") double k,
                                     @RequestParam("b") double b,
                                     HttpServletRequest request){
        probabilityRetrievalService.initRetriever(query, k, b, request);
        int state= probabilityRetrievalService.quit();
        if (state == 1) {
            return ResponseVoUtil.success();
        } else {
            return ResponseVoUtil.error(ResponseEnum.UNKNOW_ERROR);
        }
    }
}
