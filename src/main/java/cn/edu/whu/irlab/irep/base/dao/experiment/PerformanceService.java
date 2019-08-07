package cn.edu.whu.irlab.irep.base.dao.experiment;

import cn.edu.whu.irlab.irep.base.entity.experiment.Performance;

/**
 * @author gcr19
 * @date 2019-08-07 21:12
 * @desc
 **/
public interface PerformanceService {
    int insert(Performance record);

    int insertSelective(Performance record);

    Performance selectByQueryAndRetrieverId(String query, String retrieverId);
}
