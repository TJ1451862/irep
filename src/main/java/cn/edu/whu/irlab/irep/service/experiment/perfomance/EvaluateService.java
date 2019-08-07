package cn.edu.whu.irlab.irep.service.experiment.perfomance;

import cn.edu.whu.irlab.irep.base.entity.experiment.Performance;
import cn.edu.whu.irlab.irep.base.entity.experiment.Result;
import cn.edu.whu.irlab.irep.base.entity.experiment.Retriever;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author gcr19
 * @date 2019-07-28 08:14
 * @desc 检索模型新能评价服务
 **/
public interface EvaluateService {

    /**
     *
     * @param retriever
     * @return 0代表不存在 1代表已存在
     */
    int testRetriever(Retriever retriever);

    Map<String, List<Result>> testRetriever(String query, String modelName, HttpServletRequest request);

    Performance individualPerformance(String query, String modelName, HttpServletRequest request);

    JSONObject calculateAvgPerformances(HttpServletRequest request);
}
