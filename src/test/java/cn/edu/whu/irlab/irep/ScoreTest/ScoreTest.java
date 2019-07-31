package cn.edu.whu.irlab.irep.ScoreTest;

import cn.edu.whu.irlab.irep.base.dao.system.ChoiceQuestionService;
import cn.edu.whu.irlab.irep.base.dao.system.CompletionQuestionService;
import cn.edu.whu.irlab.irep.base.dao.system.ExperimentService;
import cn.edu.whu.irlab.irep.base.entity.system.ChoiceQuestion;
import cn.edu.whu.irlab.irep.base.entity.system.CompletionQuestion;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * @author gcr19
 * @date 2019-07-25 06:41
 * @desc 向实验表中插入对应数据
 **/
public class ScoreTest {
    @Autowired
    private ChoiceQuestionService choiceQuestionService;
    @Autowired
    private CompletionQuestionService completionQuestionService;
    @Autowired
    private ExperimentService experimentService;


    @Test
    public void scoreTest() {
        List<ChoiceQuestion> choiceQuestions=choiceQuestionService.selectAllExperimentIdAndScore();
        List<CompletionQuestion> completionQuestions=completionQuestionService.selectAllExperimentIdAndScore();
        Map<Integer,int[]> cids;

        for (ChoiceQuestion c:
             choiceQuestions) {

        }

    }
}
