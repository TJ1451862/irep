package cn.edu.whu.irlab.irep.base.mapper;

import cn.edu.whu.irlab.irep.base.entity.UserRetrieverScore;

public interface UserRetrieverScoreMapper {
    int insert(UserRetrieverScore record);

    int insertSelective(UserRetrieverScore record);
}