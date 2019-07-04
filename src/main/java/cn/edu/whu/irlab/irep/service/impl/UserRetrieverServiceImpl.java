package cn.edu.whu.irlab.irep.service.impl;

import cn.edu.whu.irlab.irep.entity.UserRetriever;
import cn.edu.whu.irlab.irep.mapper.UserRetrieverMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRetrieverServiceImpl {

    @Autowired
    public UserRetrieverMapper userRetrieverMapper;

    public int deleteByPrimaryKey(Integer userId) {
        return userRetrieverMapper.deleteByPrimaryKey(userId);
    }

    public int insert(UserRetriever record) {
        return userRetrieverMapper.insert(record);
    }

    public int insertSelective(UserRetriever record) {
        return userRetrieverMapper.insertSelective(record);
    }

    public UserRetriever selectByPrimaryKey(Integer userId) {
        return userRetrieverMapper.selectByPrimaryKey(userId);
    }

    public int updateByPrimaryKeySelective(UserRetriever record) {
        return userRetrieverMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(UserRetriever record) {
        return userRetrieverMapper.updateByPrimaryKey(record);
    }


}
