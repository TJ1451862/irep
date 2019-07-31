package cn.edu.whu.irlab.irep.service.experiment.retrieval;

import cn.edu.whu.irlab.irep.service.vo.BijVo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ProbabilityRetrievalService extends RetrieverService {

    //初始化检索器
    void initRetriever(String queryContent, double k, double b, HttpServletRequest request);

    //计算Bij
    List<BijVo> calculateBijs();


}
