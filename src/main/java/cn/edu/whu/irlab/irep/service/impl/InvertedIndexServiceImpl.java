package cn.edu.whu.irlab.irep.service.impl;

import cn.edu.whu.irlab.irep.entity.InvertedIndex;
import cn.edu.whu.irlab.irep.mapper.InvertedIndexMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvertedIndexServiceImpl {

    @Autowired
    public InvertedIndexMapper invertedIndexMapper;

    public int insert(InvertedIndex invertedIndex){
        return invertedIndexMapper.insert(invertedIndex);
    }

    public List<InvertedIndex> selectByIndexType(String indexType){
        return invertedIndexMapper.selectByIndexType(indexType);
    }

    public List<InvertedIndex> selectByIndexTypeAndTerm(String indexType,String term){
        InvertedIndex invertedIndex=new InvertedIndex();
        invertedIndex.setTerm(term);
        invertedIndex.setIndexType(indexType);
        return invertedIndexMapper.selectByIndexTypeAndTerm(invertedIndex);
    }
}
