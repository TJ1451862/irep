package cn.edu.whu.irlab.irep.base.mapper.system;

import cn.edu.whu.irlab.irep.base.entity.system.ChoiceQuestion;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface ChoiceQuestionMapper {
    int insert(ChoiceQuestion record);

    int insertSelective(ChoiceQuestion record);

    List<ChoiceQuestion> selectAllExperimentIdAndScore();

    Integer selectScoreById(int id);

    ChoiceQuestion selectAnswerAndScoreById(int id);
}