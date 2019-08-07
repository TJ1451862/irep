package cn.edu.whu.irlab.irep.base.mapper.experiment;

import cn.edu.whu.irlab.irep.base.entity.experiment.Performance;
import org.springframework.stereotype.Component;

@Component
public interface PerformanceMapper {
    int insert(Performance record);

    int insertSelective(Performance record);

    Performance selectByQueryAndRetrieverId(Performance performance);
}