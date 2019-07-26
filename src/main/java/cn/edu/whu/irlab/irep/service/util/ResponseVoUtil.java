package cn.edu.whu.irlab.irep.service.util;

import cn.edu.whu.irlab.irep.service.enums.ResponseEnum;
import cn.edu.whu.irlab.irep.service.vo.ResponseVo;

/**
 * @author fangrf
 * @version 1.0
 * @date 2019-07-23 17:07
 * @desc 异常处理结果返回工具类
 **/
public class ResponseVoUtil {

    public static ResponseVo error(ResponseEnum responseEnum){
        ResponseVo responseVo = new ResponseVo();
        responseVo.setCode(responseEnum.getCode());
        responseVo.setMsg(responseEnum.getMsg());
        return responseVo;
    }

    public static ResponseVo error(Integer code,String message){
        ResponseVo responseVo = new ResponseVo();
        responseVo.setCode(code);
        responseVo.setMsg(message);
        return responseVo;
    }

    public static ResponseVo success(Object object){
        ResponseVo responseVo = new ResponseVo();
        responseVo.setCode(0);
        responseVo.setMsg("成功");
        responseVo.setData(object);
        return responseVo;
    }

    public static ResponseVo success(){
        ResponseVo responseVo = new ResponseVo();
        responseVo.setCode(0);
        responseVo.setMsg("成功");
        return responseVo;
    }

    public static ResponseVo success(ResponseEnum responseEnum){
        ResponseVo responseVo = new ResponseVo();
        responseVo.setCode(responseEnum.getCode());
        responseVo.setMsg(responseEnum.getMsg());
        return responseVo;
    }

    public static ResponseVo success(ResponseEnum responseEnum,Object object){
        ResponseVo responseVo = new ResponseVo();
        responseVo.setCode(responseEnum.getCode());
        responseVo.setMsg(responseEnum.getMsg());
        responseVo.setData(object);
        return responseVo;
    }
}
