package cn.edu.whu.irlab.irep.controller;

import cn.edu.whu.irlab.irep.entity.User;
import cn.edu.whu.irlab.irep.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author fangrf
 * @version 1.0
 * @date 2019-06-25 11:33
 * @desc 用户登录注册的交互层
 **/
@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login")
    @ResponseBody
    public Map<String,String> loginController(User user){
        Map<String,String> map = new HashMap<>();
        if(userService.selectUser(user) == null){
            map.put("code","0");
            map.put("message","用户名或者密码错误");
        }else{
            map.put("code","1");
            map.put("message","登录成功");
        }
        return map;
    }

    @RequestMapping(value = "/sign")
    public String signController(User user, ModelMap modelMap){
        if(userService.selectUserByPhone(user) != null){
            modelMap.put("code",1);
            modelMap.put("message","当前手机号已经被注册过了");
        }else if(userService.selectUserByEmail(user) != null){
            modelMap.put("code",2);
            modelMap.put("message","当前邮箱已经被注册过了");
        }else if(userService.selectUser(user) != null){
            modelMap.put("code",3);
            modelMap.put("message","当前用户名或者密码已经被注册过了");
        }else if(userService.insertUserService(user) != 1){
            modelMap.put("code",4);
            modelMap.put("message","注册失败，请联系系统管理员");
        }else{
            modelMap.put("code",0);
            modelMap.put("message","注册成功");
        }
        return "signIn.html";
    }
}
