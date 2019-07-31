package cn.edu.whu.irlab.irep.base.mapper.system;


import cn.edu.whu.irlab.irep.base.entity.system.User;
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

    List<User> selectAllUser();

    User selectUserByPhone(User user);

    User selectUserByPhoneAndUsername(User user);

    User selectUserByEmail(User user);

    User selectUserByEmailAndUsername(User user);

    int updateUserByUsername(User user);

    int insertSelective(User user);

    User selectUserByUsername(@Param("username") String username);

    int updateLoginTimeByUsername(User user);

    int updateOutTimeByUsername(Integer id);

    int updateByPrimaryKeySelective(User user);

    int deleteByPrimaryKey(Integer id);
}
