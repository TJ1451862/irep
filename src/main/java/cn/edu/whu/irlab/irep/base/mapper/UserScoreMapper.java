package cn.edu.whu.irlab.irep.base.mapper;

import cn.edu.whu.irlab.irep.base.entity.UserScore;

public interface UserScoreMapper {
    int insert(UserScore record);

    int insertSelective(UserScore record);
}