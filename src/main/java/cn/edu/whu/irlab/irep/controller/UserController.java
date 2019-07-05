package cn.edu.whu.irlab.irep.controller;

import cn.edu.whu.irlab.irep.entity.User;
import cn.edu.whu.irlab.irep.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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

    /**
     * 此处的逻辑，前端ajax传入登录的用户名和密码
     * 此处获取用户名和密码，查询数据库，如果查询到了就将用户信息存到session中
     * 并且返回code为1，代表登录成功，否则返回code为0，代表登录失败
     * 获取返回的code的值，对登录成功进行判断
     * code为1，则用windows.localhref()跳转到主页面
     * code为0，则在登录页面弹出输入错误的提示信息
     * @param username
     * @param password
     * @param request
     * @return
     */
    //请将userId存到session中
    @RequestMapping(value = "/login")
    @ResponseBody
    public Map loginController(String username,String password, HttpServletRequest request) {
        Map<String,Object> map=new HashMap<>();
        User user = userService.selectUser(username,password);
        if (user == null) {
            map.put("code", "0");
            map.put("message", "用户名或者密码错误");
        } else {
            //用户信息存入该用户的session 中
            request.getSession().setAttribute("user",user);
            map.put("code", "1");
            map.put("message", "登录成功");
        }
        return map;
    }

    /**
     * 参数说明：User对象里面必须有用户名，密码，手机号和邮箱，工作单位
     * 此处逻辑和登录一样，先判断注册信息
     * 前端根据后端返回的code的值判断注册成功与否
     * @param user
     * @return
     */
    @RequestMapping(value = "/signIn")
    @ResponseBody
    public Map signController(User user) {
        Map<String,Object> map=new HashMap<>();
        if (userService.selectUserByPhone(user) != null) {
            map.put("code", 1);
            map.put("message", "当前手机号已经被注册过了");
        } else if (userService.selectUserByEmail(user) != null) {
            map.put("code", 2);
            map.put("message", "当前邮箱已经被注册过了");
        } else if (userService.selectUser(user.getUsername(),user.getPassword()) != null) {
            map.put("code", 3);
            map.put("message", "当前用户名或者密码已经被注册过了");
        } else if (userService.insertUserService(user) != 1) {
            map.put("code", 4);
            map.put("message", "注册失败，请联系系统管理员");
        } else {
            map.put("code", 0);
            map.put("message", "注册成功");
        }
        return map;
    }


    /**
     * 参数说明：User对象里面必须有用户名，新的密码，手机号和邮箱
     * @param user
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/update")
    public String updatePassword(User user,ModelMap modelMap){
        //通过手机号和用户名查询是否该用户注册过，通过邮箱和用户名查询是否注册过，二者任选一个成功就可以修改密码
        if(userService.selectUserByPhoneAndUsername(user) != null || userService.selectUserByEmailAndUsername(user) != null){
            int i = userService.updateUserByUsername(user);
            if(i != 1){
                modelMap.put("message","修改失败，请联系网站管理员。");
                modelMap.put("code",1);
            }
            modelMap.put("code",0);
            modelMap.put("message","修改成功。");
            //修改后跳转的页面
            return "a";
        }else{
            modelMap.put("message","请输入正确的手机号或者邮箱。");
            modelMap.put("code",1);
            //用户名和邮箱全输入错误跳转的页面
            return "b";
        }
    }
}
