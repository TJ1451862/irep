package cn.edu.whu.irlab.irep.service.experiment.retrieval;


import cn.edu.whu.irlab.irep.base.entity.experiment.Result;
import cn.edu.whu.irlab.irep.service.vo.Query;
import cn.edu.whu.irlab.irep.service.vo.ResultVo;
import cn.edu.whu.irlab.irep.service.vo.SearchResultVo;

import java.util.List;
import java.util.Map;

public interface RetrieverService {

    List<SearchResultVo> search();

    /**
     * 计算相似度
     * @return 相似度
     */
    List<ResultVo> calculateSimilarity();

    int quit();

    /**
     * 按相似度降序排序
     * @return 降序排序后的相似度
     */
    List<ResultVo> descendOrderSimilarity();

    /**
     * 测试检索器
     * @return
     */
    Map<String, List<Result>> testRetriever();

    /**
     * 返回查询对象
     * @return 查询对象
     */
    Query getQuery();


}
