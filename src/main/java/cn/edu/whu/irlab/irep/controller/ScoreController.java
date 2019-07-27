package cn.edu.whu.irlab.irep.controller;

import cn.edu.whu.irlab.irep.service.enums.ResponseEnum;
import cn.edu.whu.irlab.irep.service.scoreManagement.ScoreService;
import cn.edu.whu.irlab.irep.service.util.ResponseVoUtil;
import cn.edu.whu.irlab.irep.service.vo.AnswerVo;
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
    public ResponseVo updateChoiceAndCompletionScoreController(@RequestBody AnswerVo answerVo, HttpServletRequest request){
        int state=scoreService.updateChoiceAndCompletionScore(answerVo,request);
        if (state==1){
            return ResponseVoUtil.success();
        }else {
            return ResponseVoUtil.error(ResponseEnum.UNKNOW_ERROR);
        }
    }
}
