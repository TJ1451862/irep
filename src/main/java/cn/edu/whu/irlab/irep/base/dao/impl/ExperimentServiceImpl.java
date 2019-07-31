package cn.edu.whu.irlab.irep.base.dao.impl;

import cn.edu.whu.irlab.irep.base.dao.ExperimentService;
import cn.edu.whu.irlab.irep.base.entity.Experiment;
import cn.edu.whu.irlab.irep.base.mapper.ExperimentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author gcr19
 * @date 2019-07-25 07:29
 * @desc
 **/
@Service
public class ExperimentServiceImpl implements ExperimentService {

    @Autowired
    private ExperimentMapper experimentMapper;

    @Override
    public Experiment selectById(int id) {
        return experimentMapper.selectById(id);
    }

    @Override
    public int updateById(Experiment experiment) {
        return experimentMapper.updateById(experiment);
    }
}
