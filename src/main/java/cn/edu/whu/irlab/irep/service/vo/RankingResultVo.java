package cn.edu.whu.irlab.irep.service.vo;

import java.util.List;

/**
 * @author gcr19
 * @date 2019-07-27 08:50
 * @desc
 **/
public class RankingResultVo {
    private int experimentId;

    private List<Integer> rankingResult;

    public int getExperimentId() {
        return experimentId;
    }

    public void setExperimentId(int experimentId) {
        this.experimentId = experimentId;
    }

    public List<Integer> getRankingResult() {
        return rankingResult;
    }

    public void setRankingResult(List<Integer> rankingResult) {
        this.rankingResult = rankingResult;
    }
}
