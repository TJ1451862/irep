package cn.edu.whu.irlab.irep.controller;

import cn.edu.whu.irlab.irep.base.entity.*;
import cn.edu.whu.irlab.irep.base.dao.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RestController
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
     * @param username 用户名
     * @param password 密码
     * @param request
     * @return
     */
    //请将userId存到session中
    @RequestMapping(value = "/login")
    public Map<String,String> loginController(@RequestParam(value = "username") String username,
                                              @RequestParam(value = "password") String password,
                                              HttpServletRequest request) {
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
    public Map<String,String> outController(HttpServletRequest request) {
        Map<String,String> map=new HashMap<>();
        User user = (User)request.getSession().getAttribute("user");
        int i = userService.updateOutTimeByUsernameService(user.getId());
        if(i == 1){
            request.getSession().invalidate();
            map.put("code", "1");
            map.put("message", "用户退出成功");
        }else{
            map.put("code", "1");
            map.put("message", "用户退出成功");
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
    @PostMapping(value = "/signIn")
    public Map<String,Object> signController(User user) {
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
     * @return
     */
    @RequestMapping(value = "/updatePassword")
    public Map<String,Object> updatePasswordController(@RequestBody User user){
        Map<String,Object> map=new HashMap<>();
        //通过手机号和用户名查询是否该用户注册过，通过邮箱和用户名查询是否注册过，二者任选一个成功就可以修改密码
        if(userService.selectUserByPhoneAndUsernameService(user) != null || userService.selectUserByEmailAndUsernameService(user) != null){
            int i = userService.updateUserByUsernameService(user);
            if(i != 1){
                map.put("message","修改失败，请联系网站管理员。");
                map.put("code",1);
            }else{
                map.put("code",0);
                map.put("message","修改成功。");
            }
        }else{
            map.put("message","请输入正确的手机号或者邮箱。");
            map.put("code",2);
        }
        return map;
    }

    /**
     * 后台管理修改用户信息
     * 此处的修改处理是后台管理员处理的修改，所有的个人信息都可以修改
     * 具体就看传入的user对象中的字段的值
     * @param user
     * @return
     */
    @RequestMapping(value = "/updateInfo")
    public Map<String,Object> updateUserController(@RequestBody User user){
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
    public Map<String,Object> deleteUserController(@RequestBody User user){
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
    public Map<String,Object> queryUserController(){
        Map<String,Object> map = new HashMap<>();
        List<User> list = userService.selectAllUserService();
        map.put("total",list.size());
        map.put("row",list);
        return map;
    }
}
