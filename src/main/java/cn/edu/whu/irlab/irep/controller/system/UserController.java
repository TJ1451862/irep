package cn.edu.whu.irlab.irep.controller.system;

import cn.edu.whu.irlab.irep.base.dao.system.UserService;
import cn.edu.whu.irlab.irep.base.entity.system.User;
import cn.edu.whu.irlab.irep.config.session.MyHttpSessionListener;
import cn.edu.whu.irlab.irep.config.session.MyWebConfig;
import cn.edu.whu.irlab.irep.service.enums.ResponseEnum;
import cn.edu.whu.irlab.irep.service.util.ResponseVoUtil;
import cn.edu.whu.irlab.irep.service.vo.ResponseVo;
import org.apache.tomcat.util.http.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
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
     * 对用户进行校验，成功后判断用户的标识，根据标识来判断是管理员还是用户登录
     * 最后返回给前端响应的代码
     * @param request
     * @return
     */
    //请将userId存到session中


    @RequestMapping(value = "/login")
    public ResponseVo loginController(@RequestBody User user, HttpServletRequest request) {
        User user1 = userService.selectUserService(user.getUsername());
        if (user1 == null) {
            return ResponseVoUtil.error(ResponseEnum.USERNAME_ERROR);
        } else if(verifyPwd(user.getPassword(),user1.getPassword())){
            //用户信息存入该用户的session 中
            request.getSession().setAttribute("user",user1);
            userService.updateLoginTimeByUsernameService(user1);
            //管理员登录
            if(user1.getCategory() == 3){
                return ResponseVoUtil.success(ResponseEnum.ADMIN_LOGIN_SUCCESS,user1);
            }
            return ResponseVoUtil.success(ResponseEnum.USER_LOGIN_SUCCESS,user1);
        }else{
            return ResponseVoUtil.error(ResponseEnum.PASSWORD_ERROR);
        }
    }

    /**
     * 退出登录，记录退出时间，并且关闭seesion
     * @param request
     * @return
     */
    @RequestMapping(value = "/out")
    public ResponseVo outController(HttpServletRequest request) {
        User user = (User)request.getSession().getAttribute("user");
        int i = userService.updateOutTimeByUsernameService(user.getId());
        if(i == 1){
            request.getSession().removeAttribute("user");
            request.getSession().invalidate();
            return ResponseVoUtil.success();
        }else{
            return ResponseVoUtil.error(ResponseEnum.UNKNOW_ERROR);
        }
    }

    /**
     * 参数说明：User对象里面必须有用户名，密码，手机号和邮箱，工作单位
     * 此处逻辑和登录一样，先判断注册信息
     * 前端根据后端返回的code的值判断注册成功与否
     * @param user
     * @return
     */
    @RequestMapping(value = "/signIn")
    public ResponseVo signController(@RequestBody User user) {
        if (userService.selectUserByPhoneService(user) != null) {
            return ResponseVoUtil.error(ResponseEnum.PHONE_SIGN_ERROR);
        } else if (userService.selectUserByEmailService(user) != null) {
            return ResponseVoUtil.error(ResponseEnum.EMAIL_SIGN_ERROR);
        } else if (userService.selectUserService(user.getUsername()) != null) {
            return ResponseVoUtil.error(ResponseEnum.USERNAME_SIGN_ERROR);
        } else if (userService.insertUserService(user) != 1) {
            return ResponseVoUtil.error(ResponseEnum.UNKNOW_ERROR);
        } else {
            return ResponseVoUtil.success();
        }
    }

    /**
     * 参数说明：User对象里面必须有用户名，新的密码，手机号和邮箱
     * @param user
     * @return
     */
    @RequestMapping(value = "/updatePassword")
    public ResponseVo updatePasswordController(@RequestBody User user){
        //通过手机号和用户名查询是否该用户注册过，通过邮箱和用户名查询是否注册过，二者任选一个成功就可以修改密码
        if(userService.selectUserByPhoneAndUsernameService(user) != null || userService.selectUserByEmailAndUsernameService(user) != null){
            int i = userService.updateUserByUsernameService(user);
            if(i != 1){
                return ResponseVoUtil.error(ResponseEnum.UNKNOW_ERROR);
            }else{
                return ResponseVoUtil.success();
            }
        }else{
            return ResponseVoUtil.error(ResponseEnum.PHONE_EMAIL_ERROR);
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
    public ResponseVo updateUserController(@RequestBody User user){
        int i = userService.updateUserByIdService(user);
        if(i == 1){
            return ResponseVoUtil.success();
        }
        return ResponseVoUtil.error(ResponseEnum.UNKNOW_ERROR);
    }

    /**
     *
     * 后台管理根据id删除用户信息
     * 前端必须传入用户的唯一标识ID
     * @param user
     * @return
     */
    @RequestMapping(value = "/deleteInfo")
    public ResponseVo deleteUserController(@RequestBody User user){
        int i = userService.deleteUserByIdService(user.getId());
        /**
         * 此处根据业务需要，可能需要删除用户的行为信息
         */
        //
        if(i == 1){
            return ResponseVoUtil.success();
        }
        return ResponseVoUtil.error(ResponseEnum.UNKNOW_ERROR);
    }

    /**
     * 后台管理查询所有的用户信息
     * @return
     */
    @RequestMapping(value = "/query")
    public ResponseVo<List<User>> queryUserController(){
        return ResponseVoUtil.success(userService.selectAllUserService());
    }

    /**
     * 获取当前的在线人数
     * @return
     */
    @RequestMapping(value = "/queryOnline")
    public ResponseVo<Integer> querySessionOoline(){
        return ResponseVoUtil.success(MyHttpSessionListener.online);
    }
}
