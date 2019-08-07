package cn.edu.whu.irlab.irep.service.experiment.retrieval;

import cn.edu.whu.irlab.irep.service.vo.BoolStepVo;
import cn.edu.whu.irlab.irep.service.vo.BoolVectorVo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author gcr19
 * @date 2019-07-26 07:08
 * @desc
 **/
public interface BoolRetrieverService extends RetrieverService {

    void initBoolRetriever(String query, HttpServletRequest request);

    void initBoolRetriever(String query, String indexType);

    List<String> preProcess();

    List<BoolVectorVo> outputBoolVector();

    List<BoolStepVo> booleanOperation();
}
