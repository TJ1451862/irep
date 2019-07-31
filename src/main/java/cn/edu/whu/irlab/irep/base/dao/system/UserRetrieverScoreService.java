package cn.edu.whu.irlab.irep.base.dao.system;

import cn.edu.whu.irlab.irep.base.entity.system.UserRetrieverScore;

/**
 * @author gcr19
 * @date 2019-07-25 06:36
 * @desc
 **/
public interface UserRetrieverScoreService {


    int insertSelective(UserRetrieverScore record);

    UserRetrieverScore select(UserRetrieverScore userRetrieverScore);

    int updateByUserId(UserRetrieverScore userRetrieverScore);
}
