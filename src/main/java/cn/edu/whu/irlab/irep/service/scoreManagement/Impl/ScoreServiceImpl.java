package cn.edu.whu.irlab.irep.service.scoreManagement.Impl;

import cn.edu.whu.irlab.irep.base.dao.*;
import cn.edu.whu.irlab.irep.base.entity.*;
import cn.edu.whu.irlab.irep.service.scoreManagement.ScoreService;
import cn.edu.whu.irlab.irep.service.vo.AnswerVo;
import cn.edu.whu.irlab.irep.service.vo.RankingResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author gcr19
 * @date 2019-07-25 19:27
 * @desc 用户成绩业务类
 **/
@Service
public class ScoreServiceImpl implements ScoreService {

    @Autowired
    private ChoiceQuestionService choiceQuestionService;

    @Autowired
    private CompletionQuestionService completionQuestionService;

    @Autowired
    private ExperimentService experimentService;

    @Autowired
    private UserExperimentScoreService userExperimentScoreService;

    @Autowired
    private UserAnalyticalScoreService userAnalyticalScoreService;

    @Override
    public int updateChoiceAndCompletionScore(AnswerVo answer, HttpServletRequest request) {

        int experimentId = answer.getExperimentId();

        Experiment experiment = experimentService.selectById(experimentId);

        ChoiceQuestion choiceQuestion1 = choiceQuestionService.selectAnswerAndScoreById(experiment.getChoice1Id());
        ChoiceQuestion choiceQuestion2 = choiceQuestionService.selectAnswerAndScoreById(experiment.getChoice2Id());
        ChoiceQuestion choiceQuestion3 = choiceQuestionService.selectAnswerAndScoreById(experiment.getChoice3Id());
        ChoiceQuestion choiceQuestion4 = choiceQuestionService.selectAnswerAndScoreById(experiment.getChoice4Id());

        CompletionQuestion completionQuestion1 = completionQuestionService.selectAnswerAndScoreById(experiment.getCompletion1Id());
        CompletionQuestion completionQuestion2 = completionQuestionService.selectAnswerAndScoreById(experiment.getCompletion2Id());
        CompletionQuestion completionQuestion3 = completionQuestionService.selectAnswerAndScoreById(experiment.getCompletion3Id());
        CompletionQuestion completionQuestion4 = completionQuestionService.selectAnswerAndScoreById(experiment.getCompletion4Id());

        int choice1Score = answer.getChoice1().equals(choiceQuestion1.getAnswer()) ? choiceQuestion1.getScore() : 0;
        int choice2Score = answer.getChoice2().equals(choiceQuestion2.getAnswer()) ? choiceQuestion2.getScore() : 0;
        int choice3Score = answer.getChoice3().equals(choiceQuestion3.getAnswer()) ? choiceQuestion3.getScore() : 0;
        int choice4Score = answer.getChoice4().equals(choiceQuestion4.getAnswer()) ? choiceQuestion4.getScore() : 0;

        int completion1Score = answer.getCompletion1().equals(completionQuestion1.getAnswer()) ? completionQuestion1.getScore() : 0;
        int completion2Score = answer.getCompletion2().equals(completionQuestion2.getAnswer()) ? completionQuestion2.getScore() : 0;
        int completion3Score = answer.getCompletion3().equals(completionQuestion3.getAnswer()) ? completionQuestion3.getScore() : 0;
        int completion4Score = answer.getCompletion4().equals(completionQuestion4.getAnswer()) ? completionQuestion4.getScore() : 0;

        UserExperimentScore userExperimentScore = getUserExperimentScore(experimentId,request);

        userExperimentScore.setChoice1Score(choice1Score);
        userExperimentScore.setChoice2Score(choice2Score);
        userExperimentScore.setChoice3Score(choice3Score);
        userExperimentScore.setChoice4Score(choice4Score);

        userExperimentScore.setCompletion1Score(completion1Score);
        userExperimentScore.setCompletion2Score(completion2Score);
        userExperimentScore.setCompletion3Score(completion3Score);
        userExperimentScore.setCompletion4Score(completion4Score);

        return userExperimentScoreService.updateByUserIdAndExperimentId(userExperimentScore);
    }

    @Override
    public int updateRankingScore(RankingResultVo rankingResult, HttpServletRequest request) {

        int experimentId = rankingResult.getExperimentId();
        Experiment experiment = experimentService.selectById(experimentId);
        int standardScore = experiment.getRankingScore();
        List<Integer> ranking = rankingResult.getRankingResult();
        int num = 0;

        for (int i = 1; i < ranking.size(); i++) {
            if (i == ranking.get(i-1).intValue()) {
                num++;
            }
        }

        int score = standardScore * num / ranking.size();

        UserExperimentScore userExperimentScore=getUserExperimentScore(experimentId,request);
        userExperimentScore.setRankingScore(score);

        return userExperimentScoreService.updateByUserIdAndExperimentId(userExperimentScore);

    }

    @Override
    public int updateAnalyticalContent(UserAnalyticalScoreWithBLOBs userAnalyticalScore, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        userAnalyticalScore.setUserId(user.getId());
        return userAnalyticalScoreService.updateByUserIdAndExperimentId(userAnalyticalScore);
    }

    @Override
    public int updateAnalyticalComment(UserAnalyticalScoreWithBLOBs userAnalyticalScore, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        userAnalyticalScore.setTeacherId(user.getId());

        UserExperimentScore userExperimentScore=new UserExperimentScore();
        userExperimentScore.setUserId(userAnalyticalScore.getUserId());
        userExperimentScore.setExperimentId(userAnalyticalScore.getExperimentId());
        userExperimentScore.setAnalyticalScore(userAnalyticalScore.getScore()*100);

        userExperimentScoreService.updateByUserIdAndExperimentId(userExperimentScore);

        return userAnalyticalScoreService.updateComment(userAnalyticalScore);
    }


    private UserExperimentScore getUserExperimentScore(int experimentId, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        UserExperimentScore userExperimentScore = new UserExperimentScore();
        userExperimentScore.setUserId(user.getId());
        userExperimentScore.setExperimentId(experimentId);
        return userExperimentScore;
    }

}
