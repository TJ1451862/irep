package cn.edu.whu.irlab.irep.base.mapper;

import cn.edu.whu.irlab.irep.base.entity.RankingQuestion;

public interface RankingQuestionMapper {
    int insert(RankingQuestion record);

    int insertSelective(RankingQuestion record);
}