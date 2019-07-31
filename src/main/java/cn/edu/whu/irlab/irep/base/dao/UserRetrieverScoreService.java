package cn.edu.whu.irlab.irep.base.dao;

import cn.edu.whu.irlab.irep.base.entity.UserRetrieverScore;

/**
 * @author gcr19
 * @date 2019-07-25 06:36
 * @desc
 **/
public interface UserRetrieverScoreService {


    int insertSelective(UserRetrieverScore record);

    UserRetrieverScore select(UserRetrieverScore userRetrieverScore);

}
