package cn.edu.whu.irlab.irep.base.entity.system;

public class UserRetrieverScore {
    private Integer userId;

    private String vsmRetriever;

    private String probabilityRetriever;

    private String lanugaeRetriever;

    private Integer vsmScore;

    private Integer probabilityScore;

    private Integer lanugaeScore;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getVsmRetriever() {
        return vsmRetriever;
    }

    public void setVsmRetriever(String vsmRetriever) {
        this.vsmRetriever = vsmRetriever == null ? null : vsmRetriever.trim();
    }

    public String getProbabilityRetriever() {
        return probabilityRetriever;
    }

    public void setProbabilityRetriever(String probabilityRetriever) {
        this.probabilityRetriever = probabilityRetriever == null ? null : probabilityRetriever.trim();
    }

    public String getLanugaeRetriever() {
        return lanugaeRetriever;
    }

    public void setLanugaeRetriever(String lanugaeRetriever) {
        this.lanugaeRetriever = lanugaeRetriever == null ? null : lanugaeRetriever.trim();
    }

    public Integer getVsmScore() {
        return vsmScore;
    }

    public void setVsmScore(Integer vsmScore) {
        this.vsmScore = vsmScore;
    }

    public Integer getProbabilityScore() {
        return probabilityScore;
    }

    public void setProbabilityScore(Integer probabilityScore) {
        this.probabilityScore = probabilityScore;
    }

    public Integer getLanugaeScore() {
        return lanugaeScore;
    }

    public void setLanugaeScore(Integer lanugaeScore) {
        this.lanugaeScore = lanugaeScore;
    }
}