package cn.edu.whu.irlab.irep.service;

import cn.edu.whu.irlab.irep.entity.User;

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

    //根据用户名和密码来查询用户信息
    User selectUser(String username,String password);

    //根据手机号码查询用户信息
    User selectUserByPhone(User user);

    //根据手机号码和用户名查询用户信息
    User selectUserByPhoneAndUsername(User user);

    //根据邮箱查询用户信息
    User selectUserByEmail(User user);

    //根据邮箱和用户名查询用户信息
    User selectUserByEmailAndUsername(User user);

    //根据用户名修改密码
    int updateUserByUsername(User user);
}
