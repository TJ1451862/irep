package cn.edu.whu.irlab.irep.base.dao.system;

import cn.edu.whu.irlab.irep.base.entity.system.UserAnalyticalScoreWithBLOBs;

/**
 * @author gcr19
 * @date 2019-07-27 12:17
 * @desc
 **/
public interface UserAnalyticalScoreService {

    int insert(UserAnalyticalScoreWithBLOBs record);

    int insertSelective(UserAnalyticalScoreWithBLOBs record);

    //更新学生答案
    int updateByUserIdAndExperimentId(UserAnalyticalScoreWithBLOBs record);

    //更新老师评语和成绩
    int updateComment(UserAnalyticalScoreWithBLOBs record);
}
