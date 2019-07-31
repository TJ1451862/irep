package cn.edu.whu.irlab.irep.base.entity;

public class Result {

    private Integer queryId;

    private Integer docId;

    private Integer docRank;

    private String retrieverId;

    private Integer score;

    private Boolean isExisting;

    public Result() {
    }

    public Result(Integer queryId, Integer docId, Integer docRank, String retrieverId, Boolean isExisting) {
        this.queryId = queryId;
        this.docId = docId;
        this.docRank = docRank;
        this.retrieverId = retrieverId;
        this.isExisting = isExisting;
    }

    public Integer getQueryId() {
        return queryId;
    }

    public void setQueryId(Integer queryId) {
        this.queryId = queryId;
    }

    public Integer getDocId() {
        return docId;
    }

    public void setDocId(Integer docId) {
        this.docId = docId;
    }

    public Integer getDocRank() {
        return docRank;
    }

    public void setDocRank(Integer docRank) {
        this.docRank = docRank;
    }

    public String getRetrieverId() {
        return retrieverId;
    }

    public void setRetrieverId(String retrieverId) {
        this.retrieverId = retrieverId == null ? null : retrieverId.trim();
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Boolean getIsExisting() {
        return isExisting;
    }

    public void setIsExisting(Boolean isExisting) {
        this.isExisting = isExisting;
    }
}