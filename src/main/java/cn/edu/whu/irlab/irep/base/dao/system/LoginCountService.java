package cn.edu.whu.irlab.irep.base.dao.system;

import cn.edu.whu.irlab.irep.base.entity.system.LoginCount;


public interface LoginCountService {

    //检测到登录就更新
    Integer updateLoginCountService();
    //从数据库中查询出已登录人数
    LoginCount getLoginCountService();
}
