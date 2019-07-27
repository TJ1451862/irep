package cn.edu.whu.irlab.irep.base.dao.impl;

import cn.edu.whu.irlab.irep.base.dao.UserExperimentScoreService;
import cn.edu.whu.irlab.irep.base.entity.UserExperimentScore;
import cn.edu.whu.irlab.irep.base.mapper.UserExperimentScoreMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author gcr19
 * @date 2019-07-25 20:45
 * @desc
 **/
@Service
public class UserExperimentScoreServiceImpl implements UserExperimentScoreService {

    @Autowired
    private UserExperimentScoreMapper userExperimentScoreMapper;

    @Override
    public int insert(UserExperimentScore userExperimentScore) {
        return userExperimentScoreMapper.insert(userExperimentScore);
    }

    @Override
    public int insertSelective(UserExperimentScore userExperimentScore) {
        return userExperimentScoreMapper.insertSelective(userExperimentScore);
    }

    @Override
    public int updateByUserIdAndExperimentId(UserExperimentScore userExperimentScore) {
        int state;
        int experimentId = userExperimentScore.getExperimentId();
        int userId = userExperimentScore.getUserId();
        UserExperimentScore temp=selectByUserIdAndExperimentId(userId, experimentId) ;
        if (temp!=null) {
            state=userExperimentScoreMapper.updateByUserIdAndExperimentId(userExperimentScore);
        } else {
            state = userExperimentScoreMapper.insertSelective(userExperimentScore);
        }
        return state;
    }

    public UserExperimentScore select(UserExperimentScore userExperimentScore) {
        return userExperimentScoreMapper.select(userExperimentScore);
    }

    private UserExperimentScore selectByUserIdAndExperimentId(int userId, int experimentId) {
        UserExperimentScore userExperimentScore = new UserExperimentScore();
        userExperimentScore.setUserId(userId);
        userExperimentScore.setExperimentId(experimentId);
        return select(userExperimentScore);
    }

}
