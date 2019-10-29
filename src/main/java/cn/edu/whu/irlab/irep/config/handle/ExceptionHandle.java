package cn.edu.whu.irlab.irep.config.handle;

import cn.edu.whu.irlab.irep.service.exception.MyException;
import cn.edu.whu.irlab.irep.service.util.ResponseVoUtil;
import cn.edu.whu.irlab.irep.service.vo.ResponseVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author fangrf
 * @version 1.0
 * @date 2019-07-23 17:00
 * @desc 统一处理拦截器异常
 **/
@RestControllerAdvice
public class ExceptionHandle {

    private final static Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);

    @ExceptionHandler(value = Exception.class)
    public ResponseVo handle(Exception e){
       if(e instanceof MyException){
          MyException myException = (MyException) e;
          logger.error(e.getMessage());
          return ResponseVoUtil.error(myException.getCode(),myException.getMessage());
       }
       logger.error("【系统异常】{}", e);
       return ResponseVoUtil.error(-1,"未知错误");
    }
}
