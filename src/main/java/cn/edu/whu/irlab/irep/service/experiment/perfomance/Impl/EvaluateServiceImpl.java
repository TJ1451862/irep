package cn.edu.whu.irlab.irep.service.experiment.perfomance.Impl;

import cn.edu.whu.irlab.irep.base.dao.RetrieverService;
import cn.edu.whu.irlab.irep.base.entity.Retriever;
import cn.edu.whu.irlab.irep.service.experiment.perfomance.EvaluateService;
import cn.edu.whu.irlab.irep.service.experiment.retrieval.RetrieverModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author gcr19
 * @date 2019-07-28 08:17
 * @desc
 **/
@Service
public class EvaluateServiceImpl implements EvaluateService {

    @Autowired
    private RetrieverService retrieverService;

    @Override
    public int testRetriever(Retriever retriever) {
        int state;
        Retriever tempRetriever = retrieverService.selectByPrimaryKey(retriever.getRetrieverId());
        if (tempRetriever == null) {
            retrieverService.insert(retriever);
            state = 0;
        } else {
            state = 1;
        }
        return state;
    }


}
