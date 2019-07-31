package cn.edu.whu.irlab.irep.base.entity;

public class UserScore {
    private Integer id;

    private Integer userId;

    private Integer exam1Score;

    private Integer exam2Score;

    private Integer exam3Score;

    private Integer exam4Score;

    private Integer exam5Score;

    private Integer sumScore;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getExam1Score() {
        return exam1Score;
    }

    public void setExam1Score(Integer exam1Score) {
        this.exam1Score = exam1Score;
    }

    public Integer getExam2Score() {
        return exam2Score;
    }

    public void setExam2Score(Integer exam2Score) {
        this.exam2Score = exam2Score;
    }

    public Integer getExam3Score() {
        return exam3Score;
    }

    public void setExam3Score(Integer exam3Score) {
        this.exam3Score = exam3Score;
    }

    public Integer getExam4Score() {
        return exam4Score;
    }

    public void setExam4Score(Integer exam4Score) {
        this.exam4Score = exam4Score;
    }

    public Integer getExam5Score() {
        return exam5Score;
    }

    public void setExam5Score(Integer exam5Score) {
        this.exam5Score = exam5Score;
    }

    public Integer getSumScore() {
        return sumScore;
    }

    public void setSumScore(Integer sumScore) {
        this.sumScore = sumScore;
    }
}