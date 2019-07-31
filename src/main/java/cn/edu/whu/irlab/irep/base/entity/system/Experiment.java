package cn.edu.whu.irlab.irep.base.entity.system;

public class Experiment {
    private Integer id;

    private String experimentName;

    private Integer choice1Id;

    private Integer choice2Id;

    private Integer choice3Id;

    private Integer choice4Id;

    private Integer completion1Id;

    private Integer completion2Id;

    private Integer completion3Id;

    private Integer completion4Id;

    private Integer rankingScore;

    private Integer analyticalScore;

    private Integer operationNum;

    private Integer operationScore;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getExperimentName() {
        return experimentName;
    }

    public void setExperimentName(String experimentName) {
        this.experimentName = experimentName == null ? null : experimentName.trim();
    }

    public Integer getChoice1Id() {
        return choice1Id;
    }

    public void setChoice1Id(Integer choice1Id) {
        this.choice1Id = choice1Id;
    }

    public Integer getChoice2Id() {
        return choice2Id;
    }

    public void setChoice2Id(Integer choice2Id) {
        this.choice2Id = choice2Id;
    }

    public Integer getChoice3Id() {
        return choice3Id;
    }

    public void setChoice3Id(Integer choice3Id) {
        this.choice3Id = choice3Id;
    }

    public Integer getChoice4Id() {
        return choice4Id;
    }

    public void setChoice4Id(Integer choice4Id) {
        this.choice4Id = choice4Id;
    }

    public Integer getCompletion1Id() {
        return completion1Id;
    }

    public void setCompletion1Id(Integer completion1Id) {
        this.completion1Id = completion1Id;
    }

    public Integer getCompletion2Id() {
        return completion2Id;
    }

    public void setCompletion2Id(Integer completion2Id) {
        this.completion2Id = completion2Id;
    }

    public Integer getCompletion3Id() {
        return completion3Id;
    }

    public void setCompletion3Id(Integer completion3Id) {
        this.completion3Id = completion3Id;
    }

    public Integer getCompletion4Id() {
        return completion4Id;
    }

    public void setCompletion4Id(Integer completion4Id) {
        this.completion4Id = completion4Id;
    }

    public Integer getRankingScore() {
        return rankingScore;
    }

    public void setRankingScore(Integer rankingScore) {
        this.rankingScore = rankingScore;
    }

    public Integer getAnalyticalScore() {
        return analyticalScore;
    }

    public void setAnalyticalScore(Integer analyticalScore) {
        this.analyticalScore = analyticalScore;
    }

    public Integer getOperationNum() {
        return operationNum;
    }

    public void setOperationNum(Integer operationNum) {
        this.operationNum = operationNum;
    }

    public Integer getOperationScore() {
        return operationScore;
    }

    public void setOperationScore(Integer operationScore) {
        this.operationScore = operationScore;
    }
}