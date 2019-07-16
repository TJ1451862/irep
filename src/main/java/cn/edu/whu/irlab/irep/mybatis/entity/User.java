package cn.edu.whu.irlab.irep.mybatis.entity;

import java.util.Date;

/**
 * @author fangrf
 * @version 1.0
 * @date 2019-06-25 10:59
 * @desc 用户表
 **/
public class User {
    //主键
    private int id;
    //用户名
    private String username;
    //密码
    private String password;
    //手机号码
    private String phone;
    //邮箱
    private String email;
    //学校、工作单位
    private String works;
    //注册时间
    private Date createTime;
    //备用字段
    private String u1;
    private String u2;
    private String u3;
    private String u4;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWorks() {
        return works;
    }

    public void setWorks(String works) {
        this.works = works;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getU1() {
        return u1;
    }

    public void setU1(String u1) {
        this.u1 = u1;
    }

    public String getU2() {
        return u2;
    }

    public void setU2(String u2) {
        this.u2 = u2;
    }

    public String getU3() {
        return u3;
    }

    public void setU3(String u3) {
        this.u3 = u3;
    }

    public String getU4() {
        return u4;
    }

    public void setU4(String u4) {
        this.u4 = u4;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", works='" + works + '\'' +
                ", createTime=" + createTime +
                ", u1='" + u1 + '\'' +
                ", u2='" + u2 + '\'' +
                ", u3='" + u3 + '\'' +
                ", u4='" + u4 + '\'' +
                '}';
    }
}
