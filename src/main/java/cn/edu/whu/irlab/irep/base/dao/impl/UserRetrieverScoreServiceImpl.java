package cn.edu.whu.irlab.irep.base.dao.impl;

import cn.edu.whu.irlab.irep.base.dao.UserRetrieverScoreService;
import cn.edu.whu.irlab.irep.base.entity.UserRetrieverScore;
import cn.edu.whu.irlab.irep.base.mapper.UserRetrieverScoreMapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author gcr19
 * @date 2019-07-31 15:32
 * @desc
 **/
public class UserRetrieverScoreServiceImpl implements UserRetrieverScoreService {


    @Autowired
    private UserRetrieverScoreMapper userRetrieverScoreMapper;

    @Override
    public int insertSelective(UserRetrieverScore record) {
        return userRetrieverScoreMapper.insertSelective(record);
    }

    @Override
    public UserRetrieverScore select(UserRetrieverScore userRetrieverScore) {
        return userRetrieverScoreMapper.select(userRetrieverScore);
    }
}
