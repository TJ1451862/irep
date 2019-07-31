package cn.edu.whu.irlab.irep.base.entity.experiment;

public class StandardQuery {
    private Integer id;

    private String queryContent;

    public StandardQuery(Integer id, String queryContent) {
        this.id = id;
        this.queryContent = queryContent;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQueryContent() {
        return queryContent;
    }

    public void setQueryContent(String queryContent) {
        this.queryContent = queryContent == null ? null : queryContent.trim();
    }
}