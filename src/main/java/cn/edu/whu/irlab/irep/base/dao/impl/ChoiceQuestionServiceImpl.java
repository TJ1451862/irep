package cn.edu.whu.irlab.irep.base.dao.impl;

import cn.edu.whu.irlab.irep.base.dao.ChoiceQuestionService;
import cn.edu.whu.irlab.irep.base.entity.ChoiceQuestion;
import cn.edu.whu.irlab.irep.base.mapper.ChoiceQuestionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author gcr19
 * @date 2019-07-25 06:59
 * @desc 选择题服务 实现类
 **/
@Service
public class ChoiceQuestionServiceImpl implements ChoiceQuestionService {

    @Autowired
    private ChoiceQuestionMapper choiceQuestionMapper;

    @Override
    public List<ChoiceQuestion> selectAllExperimentIdAndScore() {
        return choiceQuestionMapper.selectAllExperimentIdAndScore();
    }

    @Override
    public Integer selectScoreById(int id) {
        return choiceQuestionMapper.selectScoreById(id);
    }

    @Override
    public ChoiceQuestion selectAnswerAndScoreById(int id) {
        return choiceQuestionMapper.selectAnswerAndScoreById(id);
    }
}
