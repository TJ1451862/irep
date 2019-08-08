package cn.edu.whu.irlab.irep.base.mapper.system;

import cn.edu.whu.irlab.irep.base.entity.system.UserRetrieverScore;
import org.springframework.stereotype.Component;

@Component
public interface UserRetrieverScoreMapper {

    int insert(UserRetrieverScore record);

    int insertSelective(UserRetrieverScore record);

    UserRetrieverScore select(UserRetrieverScore userRetrieverScore);

    UserRetrieverScore selectByUserId(int user_id);

    int updateByUserId(UserRetrieverScore userRetrieverScore);



}