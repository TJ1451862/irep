package cn.edu.whu.irlab.irep.base.mapper.system;

import cn.edu.whu.irlab.irep.base.entity.system.CompletionQuestion;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CompletionQuestionMapper {
    int insert(CompletionQuestion record);

    int insertSelective(CompletionQuestion record);

    List<CompletionQuestion> selectAllExperimentIdAndScore();

    Integer selectScoreById(int id);

    CompletionQuestion selectAnswerAndScoreById(int id);
}