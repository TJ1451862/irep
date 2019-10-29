package cn.edu.whu.irlab.irep.controller.system;

import cn.edu.whu.irlab.irep.base.dao.system.LoginCountService;
import cn.edu.whu.irlab.irep.base.entity.system.LoginCount;
import cn.edu.whu.irlab.irep.service.util.ResponseVoUtil;
import cn.edu.whu.irlab.irep.service.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value ="/log")
public class LoginCountController {
    @Autowired
    private LoginCountService loginCountService;
    @RequestMapping(value ="/count")
    public ResponseVo logCount( ){
        loginCountService.updateLoginCountService();
        return ResponseVoUtil.success(loginCountService.getLoginCountService());
    }
}
