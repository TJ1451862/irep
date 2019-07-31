package cn.edu.whu.irlab.irep.service.experiment.perfomance;

import cn.edu.whu.irlab.irep.base.entity.experiment.Retriever;

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
}
