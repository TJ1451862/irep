package cn.edu.whu.irlab.irep.base.dao.system.impl;

import cn.edu.whu.irlab.irep.base.dao.system.UserAnalyticalScoreService;
import cn.edu.whu.irlab.irep.base.entity.system.UserAnalyticalScoreWithBLOBs;
import cn.edu.whu.irlab.irep.base.mapper.system.UserAnalyticalScoreMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author gcr19
 * @date 2019-07-27 12:32
 * @desc
 **/
@Service
public class UserAnalyticalScoreServiceImpl implements UserAnalyticalScoreService {

    @Autowired
    private UserAnalyticalScoreMapper userAnalyticalScoreMapper;

    @Override
    public int insert(UserAnalyticalScoreWithBLOBs record) {
        return userAnalyticalScoreMapper.insert(record);
    }

    @Override
    public int insertSelective(UserAnalyticalScoreWithBLOBs record) {
        return userAnalyticalScoreMapper.insertSelective(record);
    }

    @Override
    public int updateByUserIdAndExperimentId(UserAnalyticalScoreWithBLOBs record) {
        int state;
        int experimentId=record.getExperimentId();
        int userId=record.getUserId();
        UserAnalyticalScoreWithBLOBs temp=selectByUserIdAndExperimentId(userId,experimentId);
        if(temp!=null){
            state=userAnalyticalScoreMapper.updateSelective(record);
        }else {
            state=userAnalyticalScoreMapper.insertSelective(record);
        }
        return state;
    }

    @Override
    public int updateComment(UserAnalyticalScoreWithBLOBs record) {
        return userAnalyticalScoreMapper.updateSelective(record);
    }

    public UserAnalyticalScoreWithBLOBs selectByUserIdAndExperimentId(int userId, int experimentId){
        UserAnalyticalScoreWithBLOBs userAnalyticalScoreWithBLOBs=new UserAnalyticalScoreWithBLOBs();
        userAnalyticalScoreWithBLOBs.setUserId(userId);
        userAnalyticalScoreWithBLOBs.setExperimentId(experimentId);
        return userAnalyticalScoreMapper.select(userAnalyticalScoreWithBLOBs);
    }
}
