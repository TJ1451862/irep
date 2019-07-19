package cn.edu.whu.irlab.irep.mapper;

import cn.edu.whu.irlab.irep.entity.Answer;

import java.util.List;

public interface AnswerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Answer record);

    int insertSelective(Answer record);

    Answer selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Answer record);

    int updateByPrimaryKey(Answer record);

    List<Answer> selectByCId(int cId);

    int deleteByCId(int cId);
}