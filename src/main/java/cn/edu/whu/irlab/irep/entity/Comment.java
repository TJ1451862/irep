package cn.edu.whu.irlab.irep.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Comment {
    private Integer id;
    //评论者用户名
    private String cUsername;
    //评论内容
    private String cContent;
    //发布时间
    private Date cCreateTime;
    //点赞数
    private Integer cSupport;
    //备用字段
    private String c1;

    private String c2;

    private Integer c3;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getcUsername() {
        return cUsername;
    }

    public void setcUsername(String cUsername) {
        this.cUsername = cUsername == null ? null : cUsername.trim();
    }

    public String getcContent() {
        return cContent;
    }

    public void setcContent(String cContent) {
        this.cContent = cContent == null ? null : cContent.trim();
    }

    public Date getcCreateTime() {
        return cCreateTime;
    }

    public void setcCreateTime(Date cCreateTime) {
        this.cCreateTime = cCreateTime;
    }

    public Integer getcSupport() {
        return cSupport;
    }

    public void setcSupport(Integer cSupport) {
        this.cSupport = cSupport;
    }

    public String getC1() {
        return c1;
    }

    public void setC1(String c1) {
        this.c1 = c1 == null ? null : c1.trim();
    }

    public String getC2() {
        return c2;
    }

    public void setC2(String c2) {
        this.c2 = c2 == null ? null : c2.trim();
    }

    public Integer getC3() {
        return c3;
    }

    public void setC3(Integer c3) {
        this.c3 = c3;
    }
}