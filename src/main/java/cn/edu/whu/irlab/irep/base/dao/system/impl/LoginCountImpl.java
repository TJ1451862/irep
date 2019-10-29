package cn.edu.whu.irlab.irep.base.dao.system.impl;

import cn.edu.whu.irlab.irep.base.dao.system.LoginCountService;
import cn.edu.whu.irlab.irep.base.entity.system.LoginCount;
import cn.edu.whu.irlab.irep.base.mapper.system.LoginCountMapper;
import cn.edu.whu.irlab.irep.service.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LoginCountImpl implements LoginCountService {
    @Autowired
    private LoginCountMapper loginCountMapper;

    @Override
    public Integer updateLoginCountService(){
        return loginCountMapper.updateLoginCount();
    }
    @Override
    public LoginCount getLoginCountService() {
        LoginCount c = loginCountMapper.getLoginCount();
        System.out.println(c);
        return c;
    }
}
