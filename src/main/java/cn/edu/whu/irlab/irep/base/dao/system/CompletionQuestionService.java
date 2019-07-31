package cn.edu.whu.irlab.irep.base.dao.system;

import cn.edu.whu.irlab.irep.base.entity.system.CompletionQuestion;

import java.util.List;

/**
 * @author gcr19
 * @date 2019-07-25 06:35
 * @desc 填空题 接口
 **/
public interface CompletionQuestionService {

    //查询所有实验Id及对应分值
    List<CompletionQuestion> selectAllExperimentIdAndScore();

    //查询分值
    Integer selectScoreById(int id);

    //按照Id查询答案和分值
    CompletionQuestion selectAnswerAndScoreById(int id);

}
