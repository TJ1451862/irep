package cn.edu.whu.irlab.irep.controller.system;

import cn.edu.whu.irlab.irep.base.entity.system.UserAnalyticalScoreWithBLOBs;
import cn.edu.whu.irlab.irep.base.entity.system.UserOperationRecord;
import cn.edu.whu.irlab.irep.service.enums.ResponseEnum;
import cn.edu.whu.irlab.irep.service.scoreManagement.ScoreService;
import cn.edu.whu.irlab.irep.service.util.ResponseVoUtil;
import cn.edu.whu.irlab.irep.service.vo.AnswerVo;
import cn.edu.whu.irlab.irep.service.vo.RankingResultVo;
import cn.edu.whu.irlab.irep.service.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author gcr19
 * @date 2019-07-26 07:31
 * @desc 用户成绩 控制层
 **/
@RestController
@RequestMapping("/score")
public class ScoreController {

    @Autowired
    private ScoreService scoreService;

    @PostMapping("/updateChoiceAndCompletionScore")
    public ResponseVo updateChoiceAndCompletionScoreController(@RequestBody AnswerVo answerVo, HttpServletRequest request) {
        int state = scoreService.updateChoiceAndCompletionScore(answerVo, request);
        if (state == 1) {
            return ResponseVoUtil.success();
        } else {
            return ResponseVoUtil.error(ResponseEnum.UNKNOW_ERROR);
        }
    }

    @PostMapping("/updateRankingScore")
    public ResponseVo updateRankingScoreController(@RequestBody RankingResultVo rankingResultVo, HttpServletRequest request) {
        int state = scoreService.updateRankingScore(rankingResultVo, request);
        if (state == 1) {
            return ResponseVoUtil.success();
        } else {
            return ResponseVoUtil.error(ResponseEnum.UNKNOW_ERROR);
        }
    }

    @PostMapping("/updateAnalyticalContent")
    public ResponseVo updateAnalyticalContentController(@RequestBody UserAnalyticalScoreWithBLOBs userAnalyticalScoreWithBLOBs, HttpServletRequest request) {
        int state = scoreService.updateAnalyticalContent(userAnalyticalScoreWithBLOBs, request);
        if (state == 1) {
            return ResponseVoUtil.success();
        } else {
            return ResponseVoUtil.error(ResponseEnum.UNKNOW_ERROR);
        }
    }

    @PostMapping("/updateAnalyticalComment")
    public ResponseVo updateAnalyticalCommentController(@RequestBody UserAnalyticalScoreWithBLOBs userAnalyticalScoreWithBLOBs, HttpServletRequest request) {
        int state = scoreService.updateAnalyticalComment(userAnalyticalScoreWithBLOBs, request);
        if (state == 1) {
            return ResponseVoUtil.success();
        } else {
            return ResponseVoUtil.error(ResponseEnum.UNKNOW_ERROR);
        }
    }

    @PostMapping("/createOperationRecord")
    public ResponseVo createOperationRecordController(@RequestBody UserOperationRecord userOperationRecord, HttpServletRequest request) {
        int state = scoreService.createOperationRecord(userOperationRecord,request);
        if (state == 1) {
            return ResponseVoUtil.success();
        } else {
            return ResponseVoUtil.success();
        }
    }


}
