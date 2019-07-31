package cn.edu.whu.irlab.irep.base.dao.system;

import cn.edu.whu.irlab.irep.base.entity.system.ChoiceQuestion;

import java.util.List;

/**
 * @author gcr19
 * @date 2019-07-25 06:33
 * @desc 选择题 接口
 **/
public interface ChoiceQuestionService {

    //查询所有实验Id及对应分值
    List<ChoiceQuestion> selectAllExperimentIdAndScore();

    //查询分值
    Integer selectScoreById(int id);

    //按照Id查询正确答案和分值
    ChoiceQuestion selectAnswerAndScoreById(int id);

}
