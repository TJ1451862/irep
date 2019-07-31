package cn.edu.whu.irlab.irep.controller.system;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author fangrf
 * @version 1.0
 * @date 2019-07-17 14:29
 * @desc 后台管理的页面跳转
 **/
@Controller
@RequestMapping(value = "/manage")
public class ManageIndexController {

    @RequestMapping(value = "/index.html")
    public String indexController(){
        return "manage/index";
    }
}
