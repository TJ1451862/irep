package cn.edu.whu.irlab.irep.mybatis.service.impl;

import cn.edu.whu.irlab.irep.mybatis.entity.User;
import cn.edu.whu.irlab.irep.mybatis.mapper.UserMapper;
import cn.edu.whu.irlab.irep.mybatis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static cn.edu.whu.irlab.irep.service.util.MD5Util.createSaltValue;
import static cn.edu.whu.irlab.irep.service.util.MD5Util.getFinalPwd;
import static cn.edu.whu.irlab.irep.service.util.MD5Util.md5EncodePwd;

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
        String userPwd = user.getPassword();
        String salt = createSaltValue();
        //System.out.println("盐值>>"+salt);
        String md5encodePwd = md5EncodePwd(userPwd);
        //System.out.println("md5>>"+md5encodePwd);
        String finalPwd = getFinalPwd(salt,md5encodePwd);
        //System.out.println("最终密码>>"+finalPwd);
        user.setPassword(finalPwd);
        user.setSalt(salt);
        return userMapper.insertSelective(user);
    }

    @Override
    public List<User> selectAllUserService() {
        return userMapper.selectAllUser();
    }

    @Override
    public User selectUserService(String username) {
        return userMapper.selectUserByUsername(username);
    }

    @Override
    public User selectUserByPhoneService(User user) {
        return userMapper.selectUserByPhone(user);
    }

    @Override
    public User selectUserByPhoneAndUsernameService(User user) {
        return userMapper.selectUserByPhoneAndUsername(user);
    }

    @Override
    public User selectUserByEmailService(User user) {
        return userMapper.selectUserByEmail(user);
    }

    @Override
    public User selectUserByEmailAndUsernameService(User user) {
        return userMapper.selectUserByEmailAndUsername(user);
    }

    @Override
    public int updateUserByUsernameService(User user) {
        String userPwd = user.getPassword();
        String salt = createSaltValue();
        String md5encodePwd = md5EncodePwd(userPwd);
        String finalPwd = getFinalPwd(salt,md5encodePwd);
        user.setPassword(finalPwd);
        user.setSalt(salt);
        return userMapper.updateUserByUsername(user);
    }

    @Override
    public int updateLoginTimeByUsernameService(User user) {
        return userMapper.updateLoginTimeByUsername(user);
    }

    @Override
    public int updateOutTimeByUsernameService(int id) {
        return userMapper.updateOutTimeByUsername(id);
    }

    @Override
    public int updateUserByIdService(User user) {
        String userPwd = user.getPassword();
        String salt = createSaltValue();
        String md5encodePwd = md5EncodePwd(userPwd);
        String finalPwd = getFinalPwd(salt,md5encodePwd);
        user.setPassword(finalPwd);
        user.setSalt(salt);
        return userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public int deleteUserByIdService(int id) {
        return userMapper.deleteByPrimaryKey(id);
    }

}
