package cn.edu.whu.irlab.irep.controller.experiment;


import cn.edu.whu.irlab.irep.base.dao.experiment.impl.ResultServiceImpl;
import cn.edu.whu.irlab.irep.base.dao.experiment.impl.RetrieverServiceImpl;
import cn.edu.whu.irlab.irep.base.dao.system.impl.UserRetrieverServiceImpl;
import cn.edu.whu.irlab.irep.base.entity.experiment.Result;
import cn.edu.whu.irlab.irep.base.entity.experiment.Retriever;
import cn.edu.whu.irlab.irep.base.entity.system.User;
import cn.edu.whu.irlab.irep.base.entity.system.UserRetriever;
import cn.edu.whu.irlab.irep.service.experiment.retrieval.BoolRetrieverService;
import cn.edu.whu.irlab.irep.service.experiment.retrieval.boolmodel.BoolRetrieverServiceImpl;
import cn.edu.whu.irlab.irep.service.experiment.retrieval.boolmodel.ResultForBool;
import cn.edu.whu.irlab.irep.service.experiment.retrieval.boolmodel.TermsForBool;
import cn.edu.whu.irlab.irep.service.util.Constructor;
import cn.edu.whu.irlab.irep.service.util.Find;
import cn.edu.whu.irlab.irep.service.util.ReadDoc;


import cn.edu.whu.irlab.irep.service.vo.BoolStepVo;
import cn.edu.whu.irlab.irep.service.vo.BoolVectorVo;
import cn.edu.whu.irlab.irep.service.vo.ResultVo;
import cn.edu.whu.irlab.irep.service.vo.SearchResultVo;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    public List<SearchResultVo> boolSearchController(@RequestParam(name = "query") String query,
                                                     HttpServletRequest request) {
        List<String> booleanQuery = Arrays.asList(query.split(" "));
        boolRetrieverService.initBoolRetriever(booleanQuery, request);
        return boolRetrieverService.search();
    }

    /**
     * 返回检索式的预处理结果
     * @return 检索式的预处理结果
     */
    @PostMapping("/ppq")
    public JSONObject boolQueryProcessController(@RequestParam(name = "query") String query,
                                                 HttpServletRequest request) {
        JSONObject output = new JSONObject();
        List<String> booleanQuery = Arrays.asList(query.split(" "));
        boolRetrieverService.initBoolRetriever(booleanQuery, request);
        List<String> result = boolRetrieverService.preProcess(booleanQuery);
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
    public List<BoolVectorVo> boolVectorController(@RequestParam(name = "query") String query,
                                                   HttpServletRequest request) {
        List<String> booleanQuery = Arrays.asList(query.split(" "));
        boolRetrieverService.initBoolRetriever(booleanQuery, request);
        return boolRetrieverService.outputBoolVector();
    }

    /**
     * 返回计算完毕的布尔向量
     * @return 检索式的预处理结果
     */
    @PostMapping("/booleanOperation")
    public List<BoolStepVo> booleanOperationController(@RequestParam(name = "query") String query,
                                          HttpServletRequest request) {
        List<String> booleanQuery = Arrays.asList(query.split(" "));
        boolRetrieverService.initBoolRetriever(booleanQuery, request);
        return boolRetrieverService.booleanOperation();
    }
    @PostMapping("/callbackResult")
    public List<ResultVo> callbackResultController(@RequestParam(name = "query") String query,
                                                           HttpServletRequest request) {
        List<String> booleanQuery = Arrays.asList(query.split(" "));
        boolRetrieverService.initBoolRetriever(booleanQuery, request);
        return boolRetrieverService.descendOrderSimilarity();
    }
}


