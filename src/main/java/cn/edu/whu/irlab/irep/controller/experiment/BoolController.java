package cn.edu.whu.irlab.irep.controller.experiment;


import cn.edu.whu.irlab.irep.base.dao.experiment.impl.ResultServiceImpl;
import cn.edu.whu.irlab.irep.base.dao.experiment.impl.RetrieverServiceImpl;
import cn.edu.whu.irlab.irep.base.dao.system.impl.UserRetrieverServiceImpl;
import cn.edu.whu.irlab.irep.base.entity.experiment.Result;
import cn.edu.whu.irlab.irep.service.enums.ResponseEnum;
import cn.edu.whu.irlab.irep.service.experiment.retrieval.BoolRetrieverService;


import cn.edu.whu.irlab.irep.service.util.ResponseVoUtil;
import cn.edu.whu.irlab.irep.service.vo.*;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author chr
 * @version 1.0
 * @date 2019-07-26 17:08
 * @desc 布尔模型交互层
 **/

@RestController
@RequestMapping(value = "IRforCN/Retrieval/boolModel")
public class BoolController {


    @Autowired
    public RetrieverServiceImpl retrieverService;

    @Autowired
    public UserRetrieverServiceImpl userRetrieverService;

    @Autowired
    public ResultServiceImpl resultService;

    @Autowired
    private BoolRetrieverService boolRetrieverService;


    /**
     * 返回检索结果
     *
     * @return 检索结果
     */
    @PostMapping("/search")
    public List<SearchResultVo> boolSearchController(@RequestBody JSONObject object,
                                                     HttpServletRequest request) {
        String query = object.getString("query");
        boolRetrieverService.initBoolRetriever(query, request);
        return boolRetrieverService.search();
    }

    /**
     * 返回检索式的预处理结果
     *
     * @return 检索式的预处理结果
     */
    @PostMapping("/ppq")
    public JSONObject boolQueryProcessController(@RequestBody JSONObject object,
                                                 HttpServletRequest request) {
        String query = object.getString("query");
        JSONObject output = new JSONObject();
        boolRetrieverService.initBoolRetriever(query, request);
        List<String> result = boolRetrieverService.preProcess();
        String ppq = "";
        for (String s :
                result) {
            ppq += s;
        }
        output.put("query", query);
        output.put("result", ppq);
        return output;
    }

    /**
     * 返回各词对应的布尔向量
     *
     * @return 检索式的预处理结果
     */
    @PostMapping("/boolVector")
    public List<BoolVectorVo> boolVectorController(@RequestBody JSONObject object,
                                                   HttpServletRequest request) {
        String query = object.getString("query");
        boolRetrieverService.initBoolRetriever(query, request);
        return boolRetrieverService.outputBoolVector();
    }

    /**
     * 返回计算完毕的布尔向量
     *
     * @return 检索式的预处理结果
     */
    @PostMapping("/booleanOperation")
    public List<BoolStepVo> booleanOperationController(@RequestBody JSONObject object,
                                                       HttpServletRequest request) {
        String query = object.getString("query");
        boolRetrieverService.initBoolRetriever(query, request);
        return boolRetrieverService.booleanOperation();
    }

    @PostMapping("/callbackResult")
    public List<ResultVo> callbackResultController(@RequestBody JSONObject object,
                                                   HttpServletRequest request) {
        String query = object.getString("query");
        boolRetrieverService.initBoolRetriever(query, request);
        return boolRetrieverService.descendOrderSimilarity();
    }

    @PostMapping("/testRetriever")
    public Map<String, List<Result>> testRetrieverController(@RequestBody JSONObject object,
                                                             HttpServletRequest request) {
        String query = object.getString("query");
        boolRetrieverService.initBoolRetriever(query, request);
        return boolRetrieverService.testRetriever();
    }

    @PostMapping("/quit")
    public ResponseVo quitController(@RequestBody JSONObject object,
                                     HttpServletRequest request){
        String query = object.getString("query");
        boolRetrieverService.initBoolRetriever(query, request);
        int state=boolRetrieverService.quit();
        if (state == 1) {
            return ResponseVoUtil.success();
        } else {
            return ResponseVoUtil.error(ResponseEnum.UNKNOW_ERROR);
        }
    }
}


