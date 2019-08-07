package cn.edu.whu.irlab.irep.controller.experiment;

import cn.edu.whu.irlab.irep.base.dao.experiment.DocumentService;
import cn.edu.whu.irlab.irep.base.dao.experiment.ResultService;
import cn.edu.whu.irlab.irep.base.dao.experiment.RetrieverService;
import cn.edu.whu.irlab.irep.base.dao.system.UserRetrieverService;
import cn.edu.whu.irlab.irep.base.entity.experiment.Document;
import cn.edu.whu.irlab.irep.base.entity.experiment.Performance;
import cn.edu.whu.irlab.irep.base.entity.experiment.Result;
import cn.edu.whu.irlab.irep.base.entity.experiment.Retriever;
import cn.edu.whu.irlab.irep.base.entity.system.User;
import cn.edu.whu.irlab.irep.base.entity.system.UserRetriever;
import cn.edu.whu.irlab.irep.service.experiment.perfomance.EvaluateService;
import cn.edu.whu.irlab.irep.service.experiment.perfomance.Impl.EvaluateServiceImpl;
import cn.edu.whu.irlab.irep.service.experiment.perfomance.Impl.Evaluator;
import cn.edu.whu.irlab.irep.service.experiment.retrieval.ProbabilityRetrievalService;
import cn.edu.whu.irlab.irep.service.experiment.retrieval.VsmRetrievalService;
import cn.edu.whu.irlab.irep.service.util.ReadDoc;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 检索模型性能 控制层
 */
@RestController
@RequestMapping("IRforCN/performance")
public class PerformanceController {

    @Autowired
    public VsmRetrievalService vsmRetriever;

    @Autowired
    public ResultService resultService;

    @Autowired
    public UserRetrieverService userRetrieverService;

    @Autowired
    public RetrieverService retrieverService;

    @Autowired
    public DocumentService documentService;
    @Autowired
    private EvaluateService evaluateService;


    @PostMapping("/testRetriever")
    public Map<String, List<Result>> testRetriever(@RequestParam("query") String query,
                                                   @RequestParam("modelName") String modelName,
                                                   HttpServletRequest request) {
        return evaluateService.testRetriever(query, modelName, request);
    }

    @PostMapping("/individualPerformance")
    public Performance individualPerformanceController(@RequestParam("query") String query,
                                                       @RequestParam("modelName") String modelName,
                                                       HttpServletRequest request) {
        return evaluateService.individualPerformance(query, modelName, request);
    }


    @PostMapping("/averagePerformance")
    public JSONObject averageController(HttpServletRequest request) {
        return evaluateService.calculateAvgPerformances(request);
    }

}
