package cn.edu.whu.irlab.irep.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Answer {
    private Integer id;
    //回复者用户名
    private String aUsername;
    //回复内容
    private String aContent;
    //回复时间
    private Date aCreateTime;
    //回复的评论的ID
    private Integer cId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getaUsername() {
        return aUsername;
    }

    public void setaUsername(String aUsername) {
        this.aUsername = aUsername == null ? null : aUsername.trim();
    }

    public String getaContent() {
        return aContent;
    }

    public void setaContent(String aContent) {
        this.aContent = aContent == null ? null : aContent.trim();
    }

    public Date getaCreateTime() {
        return aCreateTime;
    }

    public void setaCreateTime(Date aCreateTime) {
        this.aCreateTime = aCreateTime;
    }

    public Integer getcId() {
        return cId;
    }

    public void setcId(Integer cId) {
        this.cId = cId;
    }
}