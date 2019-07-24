package cn.edu.whu.irlab.irep.service.exception;

import cn.edu.whu.irlab.irep.service.enums.ResponseEnum;

/**
 * @author fangrf
 * @version 1.0
 * @date 2019-07-23 16:56
 * @desc 自定义抛出的异常信息
 **/
public class MyException extends RuntimeException {

    private Integer code;

    public MyException(ResponseEnum responseEnum){
        super(responseEnum.getMsg());
        this.code = responseEnum.getCode();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
