package cn.edu.whu.irlab.irep.service.impl;

import cn.edu.whu.irlab.irep.entity.FullIndex;
import cn.edu.whu.irlab.irep.mapper.FullIndexMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FullIndexServiceImpl {

    @Autowired
    public FullIndexMapper fullIndexMapper;

    public int insert(FullIndex fullIndex){
        return fullIndexMapper.insert(fullIndex);
    }

}
