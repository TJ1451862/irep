package cn.edu.whu.irlab.irep.base.mapper;

import cn.edu.whu.irlab.irep.base.entity.UserRetrieverScore;
import org.springframework.stereotype.Component;

@Component
public interface UserRetrieverScoreMapper {

    int insert(UserRetrieverScore record);

    int insertSelective(UserRetrieverScore record);

    UserRetrieverScore select(UserRetrieverScore userRetrieverScore);

}