package cn.edu.whu.irlab.irep.base.mapper.system;

import cn.edu.whu.irlab.irep.base.entity.system.UserScore;

public interface UserScoreMapper {
    int insert(UserScore record);

    int insertSelective(UserScore record);
}