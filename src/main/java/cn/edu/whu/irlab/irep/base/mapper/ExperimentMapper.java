package cn.edu.whu.irlab.irep.base.mapper;

import cn.edu.whu.irlab.irep.base.entity.Experiment;

public interface ExperimentMapper {
    int insert(Experiment record);

    int insertSelective(Experiment record);

    Experiment selectById(int id);

    int updateById(Experiment experiment);
}