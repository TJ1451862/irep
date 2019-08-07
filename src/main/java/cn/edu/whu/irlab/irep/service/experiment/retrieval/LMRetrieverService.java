package cn.edu.whu.irlab.irep.service.experiment.retrieval;

import cn.edu.whu.irlab.irep.service.vo.TfVo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author gcr19
 * @date 2019-08-06 09:24
 * @desc
 **/
public interface LMRetrieverService extends RetrieverService {
    void initLMRetriever(String query, double smoothParam, HttpServletRequest request);

    List<TfVo> calculateLM(int docId);
}
