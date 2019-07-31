package cn.edu.whu.irlab.irep.base.dao.impl;

import cn.edu.whu.irlab.irep.base.dao.CompletionQuestionService;
import cn.edu.whu.irlab.irep.base.entity.CompletionQuestion;
import cn.edu.whu.irlab.irep.base.mapper.CompletionQuestionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author gcr19
 * @date 2019-07-25 07:36
 * @desc
 **/
@Service
public class CompletionQuestionServiceImpl implements CompletionQuestionService {

    @Autowired
    private CompletionQuestionMapper completionQuestionMapper;

    @Override
    public List<CompletionQuestion> selectAllExperimentIdAndScore() {
        return completionQuestionMapper.selectAllExperimentIdAndScore();
    }

    @Override
    public Integer selectScoreById(int id) {
        return completionQuestionMapper.selectScoreById(id);
    }

    @Override
    public CompletionQuestion selectAnswerAndScoreById(int id) {
        return completionQuestionMapper.selectAnswerAndScoreById(id);
    }
}
