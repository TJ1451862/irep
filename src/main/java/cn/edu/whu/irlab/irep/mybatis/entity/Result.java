package cn.edu.whu.irlab.irep.mybatis.entity;

public class Result {
    private Integer queryId;

    private String query;

    private Integer docId;

    private Integer docRank;

    private String title;

    private String retrieverId;

    private Integer score;

    public Integer getQueryId() {
        return queryId;
    }

    public void setQueryId(Integer queryId) {
        this.queryId = queryId;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query == null ? null : query.trim();
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
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

    @Override
    public String toString() {
        return "Result{" +
                "queryId=" + queryId +
                ", query='" + query + '\'' +
                ", docId=" + docId +
                ", docRank=" + docRank +
                ", title='" + title + '\'' +
                ", retrieverId='" + retrieverId + '\'' +
                ", score=" + score +
                '}';
    }
}