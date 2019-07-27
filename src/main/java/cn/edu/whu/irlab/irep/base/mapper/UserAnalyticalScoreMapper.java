package cn.edu.whu.irlab.irep.base.mapper;

import cn.edu.whu.irlab.irep.base.entity.UserAnalyticalScoreWithBLOBs;
import org.springframework.stereotype.Component;

@Component
public interface UserAnalyticalScoreMapper {
    int insert(UserAnalyticalScoreWithBLOBs record);

    int insertSelective(UserAnalyticalScoreWithBLOBs record);

    int updateSelective(UserAnalyticalScoreWithBLOBs record);

    UserAnalyticalScoreWithBLOBs select(UserAnalyticalScoreWithBLOBs record);

}