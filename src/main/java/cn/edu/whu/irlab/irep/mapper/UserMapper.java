package cn.edu.whu.irlab.irep.mapper;

import cn.edu.whu.irlab.irep.entity.User;

import java.util.List;

/**
 * @author fangrf
 * @date 2019-06-25 11:10
 * @desc 用户表接口
 **/
public interface UserMapper {
    int addUser(User user);

    int insertUser(User user);

    List<User> selectAllUser();

    User selectUserByUsernameAndPassword(User user);

    User selectUserByPhone(User user);

    User selectUserByEmail(User user);
}
