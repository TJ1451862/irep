package cn.edu.whu.irlab.irep.mybatis.entity;

public class UserRetriever {
    private Integer userId;

    private String retriever1;

    private String retriever2;

    private String retriever3;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getRetriever1() {
        return retriever1;
    }

    public void setRetriever1(String retriever1) {
        this.retriever1 = retriever1 == null ? null : retriever1.trim();
    }

    public String getRetriever2() {
        return retriever2;
    }

    public void setRetriever2(String retriever2) {
        this.retriever2 = retriever2 == null ? null : retriever2.trim();
    }

    public String getRetriever3() {
        return retriever3;
    }

    public void setRetriever3(String retriever3) {
        this.retriever3 = retriever3 == null ? null : retriever3.trim();
    }
}