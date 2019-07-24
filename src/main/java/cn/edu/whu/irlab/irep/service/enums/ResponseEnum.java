package cn.edu.whu.irlab.irep.service.enums;

/**
 * @author fangrf
 * @version 1.0
 * @date 2019-07-23 16:20
 * @desc 枚举管理返回给前端的code
 **/
public enum ResponseEnum {

    SUCCESS(0,"成功"),
    UNKNOW_ERROR(-1,"未知错误"),
    ADMIN_LOGIN_SUCCESS(100,"管理员登录"),
    USER_LOGIN_SUCCESS(101,"用户登录"),
    USERNAME_ERROR(102,"用户名输入错误"),
    USERNAME_SIGN_ERROR(103,"用户名已经注册过了"),
    PASSWORD_ERROR(104,"密码输入错误"),
    PASSWORD_SIGN_ERROR(105,"密码已经注册过了"),
    PHONE_SIGN_ERROR(106,"手机号已经注册过了"),
    EMAIL_SIGN_ERROR(107,"邮箱已经注册过了"),
    PHONE_EMAIL_ERROR(108,"手机号或者邮箱输入错误"),
    SERVER_ERROR(500,"服务器错误"),
    ;

    private Integer code;

    private String msg;

    ResponseEnum(Integer code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
