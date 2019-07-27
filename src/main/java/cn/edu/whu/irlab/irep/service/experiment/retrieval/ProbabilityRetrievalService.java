package cn.edu.whu.irlab.irep.service.experiment.retrieval;

import cn.edu.whu.irlab.irep.service.vo.Query;
import cn.edu.whu.irlab.irep.service.vo.BijVo;
import cn.edu.whu.irlab.irep.service.vo.ResultVo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ProbabilityRetrievalService extends RetrieverService {

    //初始化检索器
    void initRetriever(String queryContent, double k, double b, HttpServletRequest request);

    //计算Bij
    List<BijVo> calculateBijs();

    //计算相似度
    List<ResultVo> calculateSimilarities();

    //按照相似度降序排序
    List<ResultVo> descendOrderSimilarities();

    //获取query
    Query getQuery();


}
