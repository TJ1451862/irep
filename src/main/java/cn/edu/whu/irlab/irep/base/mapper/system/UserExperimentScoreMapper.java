package cn.edu.whu.irlab.irep.base.mapper.system;

import cn.edu.whu.irlab.irep.base.entity.system.UserExperimentScore;
import org.springframework.stereotype.Component;

@Component
public interface UserExperimentScoreMapper {
    int insert(UserExperimentScore record);

    int insertSelective(UserExperimentScore record);

    //查询用户的子实验成绩
    UserExperimentScore select(UserExperimentScore record);

    UserExperimentScore selectByUserIdAndExperimentId(UserExperimentScore record);

    //更新用户的子实验成绩
    int updateByUserIdAndExperimentId(UserExperimentScore userExperimentScore);
}