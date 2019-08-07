package cn.edu.whu.irlab.irep.base.dao.experiment.impl;

import cn.edu.whu.irlab.irep.base.dao.experiment.PerformanceService;
import cn.edu.whu.irlab.irep.base.entity.experiment.Performance;
import cn.edu.whu.irlab.irep.base.mapper.experiment.PerformanceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author gcr19
 * @date 2019-08-07 21:13
 * @desc
 **/
@Service
public class PerformanceServiceImpl implements PerformanceService {


    @Autowired
    private PerformanceMapper performanceMapper;

    @Override
    public int insert(Performance record) {
        return performanceMapper.insert(record);
    }

    @Override
    public int insertSelective(Performance record) {
        return performanceMapper.insertSelective(record);
    }

    @Override
    public Performance selectByQueryAndRetrieverId(String query, String retrieverId){
        Performance performance=new Performance();
        performance.setQuery(query);
        performance.setRetrieverId(retrieverId);
        return performanceMapper.selectByQueryAndRetrieverId(performance);
    }
}
