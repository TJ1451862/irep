package cn.edu.whu.irlab.irep.base.mapper.experiment;

import cn.edu.whu.irlab.irep.base.entity.experiment.StandardQuery;

import java.util.List;

public interface StandardQueryMapper {
    int insert(StandardQuery record);

    int insertSelective(StandardQuery record);

    List<StandardQuery> selectAll();

    StandardQuery select(StandardQuery standardQuery);
}