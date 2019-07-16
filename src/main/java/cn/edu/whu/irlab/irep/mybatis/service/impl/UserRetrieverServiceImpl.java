package cn.edu.whu.irlab.irep.mybatis.service.impl;

import cn.edu.whu.irlab.irep.mybatis.entity.UserRetriever;
import cn.edu.whu.irlab.irep.mybatis.mapper.UserRetrieverMapper;
import cn.edu.whu.irlab.irep.mybatis.service.UserRetrieverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRetrieverServiceImpl implements UserRetrieverService {

    @Autowired
    public UserRetrieverMapper userRetrieverMapper;

    @Override
    public int deleteByPrimaryKey(Integer userId) {
        return userRetrieverMapper.deleteByPrimaryKey(userId);
    }

    @Override
    public int insert(UserRetriever record) {
        return userRetrieverMapper.insert(record);
    }

    @Override
    public int insertSelective(UserRetriever record) {
        return userRetrieverMapper.insertSelective(record);
    }

    @Override
    public UserRetriever selectByPrimaryKey(Integer userId) {
        return userRetrieverMapper.selectByPrimaryKey(userId);
    }

    @Override
    public int updateByPrimaryKeySelective(UserRetriever record) {
        return userRetrieverMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(UserRetriever record) {
        return userRetrieverMapper.updateByPrimaryKey(record);
    }


}
