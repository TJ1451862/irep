package cn.edu.whu.irlab.irep.mybatis.service;

import cn.edu.whu.irlab.irep.mybatis.entity.UserRetriever;

public interface UserRetrieverService {
    int deleteByPrimaryKey(Integer userId);
    int insert(UserRetriever record);
    int insertSelective(UserRetriever record);
    UserRetriever selectByPrimaryKey(Integer userId);
    int updateByPrimaryKeySelective(UserRetriever record);
    int updateByPrimaryKey(UserRetriever record);
}
