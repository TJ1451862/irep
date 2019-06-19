package cn.edu.whu.irlab.irep.entity;

public class Result {
    private Integer queryId;

    private String query;

    private Integer docId;

    private Integer docRank;

    private String title;

    private Integer isChinese;

    private String indexType;

    private String modelType;

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

    public Integer getIsChinese() {
        return isChinese;
    }

    public void setIsChinese(Integer isChinese) {
        this.isChinese = isChinese;
    }

    public String getIndexType() {
        return indexType;
    }

    public void setIndexType(String indexType) {
        this.indexType = indexType == null ? null : indexType.trim();
    }

    public String getModelType() {
        return modelType;
    }

    public void setModelType(String modelType) {
        this.modelType = modelType == null ? null : modelType.trim();
    }

    @Override
    public String toString() {
        return "Result{" +
                "queryId=" + queryId +
                ", query='" + query + '\'' +
                ", docId=" + docId +
                ", docRank=" + docRank +
                ", title='" + title + '\'' +
                ", isChinese=" + isChinese +
                ", indexType='" + indexType + '\'' +
                ", modelType='" + modelType + '\'' +
                '}';
    }
}