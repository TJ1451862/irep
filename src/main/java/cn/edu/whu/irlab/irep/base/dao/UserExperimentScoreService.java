package cn.edu.whu.irlab.irep.base.dao;

import cn.edu.whu.irlab.irep.base.entity.UserExperimentScore;

/**
 * @author gcr19
 * @date 2019-07-25 06:36
 * @desc
 **/
public interface UserExperimentScoreService {

    int insert(UserExperimentScore userExperimentScore);

    int insertSelective(UserExperimentScore userExperimentScore);

    int updateByUserIdAndExperimentId(UserExperimentScore userExperimentScore);

}
