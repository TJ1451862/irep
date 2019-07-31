package cn.edu.whu.irlab.irep.base.dao.system;

import cn.edu.whu.irlab.irep.base.entity.system.UserExperimentScore;

/**
 * @author gcr19
 * @date 2019-07-25 06:36
 * @desc
 **/
public interface UserExperimentScoreService {

    int insert(UserExperimentScore userExperimentScore);

    int insertSelective(UserExperimentScore userExperimentScore);

    //根据用户Id和实验Id更新
    int updateByUserIdAndExperimentId(UserExperimentScore userExperimentScore);

}
