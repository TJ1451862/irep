package cn.edu.whu.irlab.irep.controller;

import cn.edu.whu.irlab.irep.mybatis.entity.*;
import cn.edu.whu.irlab.irep.mybatis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cn.edu.whu.irlab.irep.service.util.MD5Util.verifyPwd;

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
     * 并且返回code为1,3 代表登录成功，否则返回code为0,2，代表登录失败
     * 获取返回的code的值，对登录成功进行判断
     * code为1，则用windows.localhref()跳转到用户主页面
     * code为3，则用windows.localhref()跳转到管理员主页面
     * code为0，则在登录页面弹出输入用户名错误的提示信息
     * code为2，则在登录页面弹出输入密码输入错误的提示信息
     * @param username
     * @param password
     * @param request
     * @return
     */
    //请将userId存到session中
    @RequestMapping(value = "/login")
    @ResponseBody
    public Map<String,String> loginController(String username,String password, HttpServletRequest request) {
        Map<String,String> map=new HashMap<>();
        User user = userService.selectUserService(username);
        if (user == null) {
            map.put("code", "0");
            map.put("message", "用户名输入错误！");
        } else if(verifyPwd(password,user.getPassword())){
            //用户信息存入该用户的session 中
            request.getSession().setAttribute("user",user);
            userService.updateLoginTimeByUsernameService(user);
            //如果是管理员登录，那么前端接收到的code会是3
            if(user.getCategory() == 3){
                map.put("code", "3");
                map.put("message", "管理员登录成功");
            }
            map.put("code", "1");
            map.put("message", "用户登录成功");
        }else{
            map.put("code", "2");
            map.put("message", "密码输入错误！");
        }
        return map;
    }

    /**
     * 退出登录，记录退出时间，并且关闭seesion
     * @param request
     * @return
     */
    @RequestMapping(value = "/out")
    public String outController(HttpServletRequest request) {
        User user = (User)request.getSession().getAttribute("user");
        int i = userService.updateOutTimeByUsernameService(user.getId());
        request.getSession().invalidate();
        return "login";
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
        if (userService.selectUserByPhoneService(user) != null) {
            map.put("code", 1);
            map.put("message", "当前手机号已经被注册过了");
        } else if (userService.selectUserByEmailService(user) != null) {
            map.put("code", 2);
            map.put("message", "当前邮箱已经被注册过了");
        } else if (userService.selectUserService(user.getUsername()) != null) {
            map.put("code", 3);
            map.put("message", "当前用户名已经被注册过了");
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
    @RequestMapping(value = "/updatePassword")
    public String updatePasswordController(User user,ModelMap modelMap){
        //通过手机号和用户名查询是否该用户注册过，通过邮箱和用户名查询是否注册过，二者任选一个成功就可以修改密码
        if(userService.selectUserByPhoneAndUsernameService(user) != null || userService.selectUserByEmailAndUsernameService(user) != null){
            int i = userService.updateUserByUsernameService(user);
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

    /**
     * 后台管理修改用户信息
     * 此处的修改处理是后台管理员处理的修改，所有的个人信息都可以修改
     * 具体就看传入的user对象中的字段的值
     * @param user
     * @return
     */
    @RequestMapping(value = "/updateInfo")
    @ResponseBody
    public Map<String,Object> updateUserController(User user){
        Map<String,Object> map = new HashMap<>();
        int i = userService.updateUserByIdService(user);
        if(i == 1){
            map.put("code", 0);
            map.put("message", "修改成功");
        }else{
            map.put("code", 1);
            map.put("message", "修改失败");
        }
        return map;
    }

    /**
     *
     * 后台管理根据id删除用户信息
     * 前端必须传入用户的唯一标识ID
     * @param user
     * @return
     */
    @RequestMapping(value = "/deleteInfo")
    @ResponseBody
    public Map<String,Object> deleteUserController(User user){
        Map<String,Object> map = new HashMap<>();
        int i = userService.deleteUserByIdService(user.getId());
        /**
         * 此处根据业务需要，可能需要删除用户的行为信息
         */
        //
        if(i == 1){
            map.put("code", 0);
            map.put("message", "删除成功");
        }else{
            map.put("code", 1);
            map.put("message", "删除失败");
        }
        return map;
    }

    /**
     * 后台管理查询所有的用户信息
     * @return
     */
    @RequestMapping(value = "/query")
    @ResponseBody
    public Map<String,Object> queryUserController(){
        Map<String,Object> map = new HashMap<>();
        List<User> list = userService.selectAllUserService();
        map.put("total",list.size());
        map.put("row",list);
        return map;
    }
}
