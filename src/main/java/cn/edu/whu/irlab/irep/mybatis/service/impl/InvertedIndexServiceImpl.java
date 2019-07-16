package cn.edu.whu.irlab.irep.mybatis.service.impl;

import cn.edu.whu.irlab.irep.mybatis.entity.InvertedIndex;
import cn.edu.whu.irlab.irep.mybatis.mapper.InvertedIndexMapper;
import cn.edu.whu.irlab.irep.mybatis.service.InvertedIndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvertedIndexServiceImpl implements InvertedIndexService {

    @Autowired
    public InvertedIndexMapper invertedIndexMapper;

    @Override
    public int insert(InvertedIndex invertedIndex) {
        return invertedIndexMapper.insert(invertedIndex);
    }

    @Override
    public List<InvertedIndex> selectByIndexType(String indexType) {
        return invertedIndexMapper.selectByIndexType(indexType);
    }

    @Override
    public List<InvertedIndex> select(InvertedIndex invertedIndex) {
        return invertedIndexMapper.select(invertedIndex);
    }
}
