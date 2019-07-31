package cn.edu.whu.irlab.irep.base.dao.system.impl;

import cn.edu.whu.irlab.irep.base.dao.system.UserRetrieverScoreService;
import cn.edu.whu.irlab.irep.base.entity.system.UserRetrieverScore;
import cn.edu.whu.irlab.irep.base.mapper.system.UserRetrieverScoreMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author gcr19
 * @date 2019-07-31 15:32
 * @desc
 **/
@Service
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

    @Override
    public int updateByUserId(UserRetrieverScore userRetrieverScore){
        int state;
        UserRetrieverScore temp=userRetrieverScoreMapper.selectByUserId(userRetrieverScore.getUserId());
        if (temp==null){
            state=userRetrieverScoreMapper.insertSelective(userRetrieverScore);
        }else {
            state=userRetrieverScoreMapper.updateByUserId(userRetrieverScore);
        }
        return state;
    }
}
