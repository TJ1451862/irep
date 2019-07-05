package cn.edu.whu.irlab.irep.mapper;

import cn.edu.whu.irlab.irep.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author fangrf
 * @date 2019-06-25 11:10
 * @desc 用户表接口
 **/
@Mapper
@Component
public interface UserMapper {
    int addUser(User user);

    int insertUser(User user);

    List<User> selectAllUser();

    User selectUserByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    User selectUserByPhone(User user);

    User selectUserByPhoneAndUsername(User user);

    User selectUserByEmail(User user);

    User selectUserByEmailAndUsername(User user);

    int updateUserByUsername(User user);
}
