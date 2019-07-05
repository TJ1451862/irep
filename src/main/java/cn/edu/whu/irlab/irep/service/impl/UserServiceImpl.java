package cn.edu.whu.irlab.irep.service.impl;

import cn.edu.whu.irlab.irep.entity.User;
import cn.edu.whu.irlab.irep.mapper.UserMapper;
import cn.edu.whu.irlab.irep.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author fangrf
 * @version 1.0
 * @date 2019-06-25 11:31
 * @desc 用户表的业务处理接口实现类
 **/
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public int insertUserService(User user) {
        return userMapper.insertUser(user);
    }

    @Override
    public List<User> selectAllUserService() {
        return userMapper.selectAllUser();
    }

    @Override
    public User selectUser(String username,String password) {
        return userMapper.selectUserByUsernameAndPassword(username,password);
    }

    @Override
    public User selectUserByPhone(User user) {
        return userMapper.selectUserByPhone(user);
    }

    @Override
    public User selectUserByPhoneAndUsername(User user) {
        return userMapper.selectUserByPhoneAndUsername(user);
    }

    @Override
    public User selectUserByEmail(User user) {
        return userMapper.selectUserByEmail(user);
    }

    @Override
    public User selectUserByEmailAndUsername(User user) {
        return userMapper.selectUserByEmailAndUsername(user);
    }

    @Override
    public int updateUserByUsername(User user) {
        return userMapper.updateUserByUsername(user);
    }
}
