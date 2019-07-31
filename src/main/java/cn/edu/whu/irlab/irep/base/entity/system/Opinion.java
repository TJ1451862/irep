package cn.edu.whu.irlab.irep.base.entity.system;

import lombok.Data;

import java.util.Date;

@Data
public class Opinion {
    private Integer id;
    //邮箱
    private String oEmail;
    //电话
    private String oPhone;
    //留言内容
    private String oContent;
    //创建时间
    private Date createTime;
    //处理状态1代表未处理2代表已处理
    private Integer sign;
    //处理人
    private Integer callBy;
    //处理时间
    private Date callTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getoEmail() {
        return oEmail;
    }

    public void setoEmail(String oEmail) {
        this.oEmail = oEmail;
    }

    public String getoPhone() {
        return oPhone;
    }

    public void setoPhone(String oPhone) {
        this.oPhone = oPhone;
    }

    public String getoContent() {
        return oContent;
    }

    public void setoContent(String oContent) {
        this.oContent = oContent;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getSign() {
        return sign;
    }

    public void setSign(Integer sign) {
        this.sign = sign;
    }

    public Integer getCallBy() {
        return callBy;
    }

    public void setCallBy(Integer callBy) {
        this.callBy = callBy;
    }

    public Date getCallTime() {
        return callTime;
    }

    public void setCallTime(Date callTime) {
        this.callTime = callTime;
    }
}