package cn.edu.whu.irlab.irep.service.scoreManagement;

import cn.edu.whu.irlab.irep.service.vo.AnswerVo;

import javax.servlet.http.HttpServletRequest;

/**
 * @author gcr19
 * @date 2019-07-25 19:26
 * @desc
 **/
public interface ScoreService {

    //计算选择填空题成绩并更新用户成绩
    int updateChoiceAndCompletionScore(AnswerVo choiceAndCompletion, HttpServletRequest request);
}
