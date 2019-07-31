package cn.edu.whu.irlab.irep.base.mapper.system;

import cn.edu.whu.irlab.irep.base.entity.system.RankingQuestion;

public interface RankingQuestionMapper {
    int insert(RankingQuestion record);

    int insertSelective(RankingQuestion record);
}