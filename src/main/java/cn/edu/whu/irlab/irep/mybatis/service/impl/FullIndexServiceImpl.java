package cn.edu.whu.irlab.irep.mybatis.service.impl;

import cn.edu.whu.irlab.irep.mybatis.entity.FullIndex;
import cn.edu.whu.irlab.irep.mybatis.mapper.FullIndexMapper;
import cn.edu.whu.irlab.irep.mybatis.service.FullIndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FullIndexServiceImpl implements FullIndexService {

    @Autowired
    public FullIndexMapper fullIndexMapper;

    @Override
    public int insert(FullIndex fullIndex) {
        return fullIndexMapper.insert(fullIndex);
    }

    @Override
    public List<FullIndex> selectByIndexType(String indexType) {
        return fullIndexMapper.selectByIndexType(indexType);
    }

}
