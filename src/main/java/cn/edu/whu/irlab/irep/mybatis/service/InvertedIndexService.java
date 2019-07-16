package cn.edu.whu.irlab.irep.mybatis.service;

import cn.edu.whu.irlab.irep.mybatis.entity.InvertedIndex;

import java.util.List;

public interface InvertedIndexService {

    int insert(InvertedIndex invertedIndex);

    List<InvertedIndex> selectByIndexType(String indexType);

    List<InvertedIndex> select(InvertedIndex invertedIndex);
}
