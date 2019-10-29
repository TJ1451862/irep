package cn.edu.whu.irlab.irep.base.mapper.system;

import cn.edu.whu.irlab.irep.base.entity.system.LoginCount;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface LoginCountMapper {

    //检测到登录就更新
    Integer updateLoginCount();
    //从数据库中查询出已登录人数
    LoginCount getLoginCount();
}
