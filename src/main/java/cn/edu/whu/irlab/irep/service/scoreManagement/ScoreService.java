package cn.edu.whu.irlab.irep.service.scoreManagement;

import cn.edu.whu.irlab.irep.base.entity.system.UserAnalyticalScoreWithBLOBs;
import cn.edu.whu.irlab.irep.base.entity.system.UserOperationRecord;
import cn.edu.whu.irlab.irep.service.vo.AnswerVo;
import cn.edu.whu.irlab.irep.service.vo.RankingResultVo;

import javax.servlet.http.HttpServletRequest;

/**
 * @author gcr19
 * @date 2019-07-25 19:26
 * @desc
 **/
public interface ScoreService {

    //计算选择填空题成绩并更新用户成绩
    int updateChoiceAndCompletionScore(AnswerVo choiceAndCompletion, HttpServletRequest request);

    //计算拖拽排序题成绩并更新用户成绩
    int updateRankingScore(RankingResultVo rankingResult, HttpServletRequest request);

    //更新分析题答案
    int updateAnalyticalContent(UserAnalyticalScoreWithBLOBs userAnalyticalScore, HttpServletRequest request);

    //为分析题写评语和打分
    int updateAnalyticalComment(UserAnalyticalScoreWithBLOBs userAnalyticalScore, HttpServletRequest request);

    //记录一条操作
    int createOperationRecord(UserOperationRecord userOperationRecord,HttpServletRequest request);
}
