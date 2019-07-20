package cn.edu.whu.irlab.irep.mybatis.service;


import cn.edu.whu.irlab.irep.mybatis.entity.User;

import java.util.List;

/**
 * @author fangrf
 * @date 2019-06-25 11:29
 * @desc 用户表的业务处理接口
 **/
public interface UserService {
    //添加用户信息
    int insertUserService(User user);

    //查询所有的用户信息
    List<User> selectAllUserService();

    //根据用户名来查询用户信息
    User selectUserService(String username);

    //根据手机号码查询用户信息
    User selectUserByPhoneService(User user);

    //根据手机号码和用户名查询用户信息
    User selectUserByPhoneAndUsernameService(User user);

    //根据邮箱查询用户信息
    User selectUserByEmailService(User user);

    //根据邮箱和用户名查询用户信息
    User selectUserByEmailAndUsernameService(User user);

    //根据用户名修改密码
    int updateUserByUsernameService(User user);

    //修改最近的登录时间
    int updateLoginTimeByUsernameService(User user);

    //修改最近的登出时间
    int updateOutTimeByUsernameService(int id);

    //修改用户信息
    int updateUserByIdService(User user);

    //删除用户信息
    int deleteUserByIdService(int id);
}
