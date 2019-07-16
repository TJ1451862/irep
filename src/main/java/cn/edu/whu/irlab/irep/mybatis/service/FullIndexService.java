package cn.edu.whu.irlab.irep.mybatis.service;

import cn.edu.whu.irlab.irep.mybatis.entity.FullIndex;

import java.util.List;

public interface FullIndexService {

    int insert(FullIndex fullIndex);
    List<FullIndex> selectByIndexType(String indexType);
}
