package cn.edu.whu.irlab.irep.base.dao.experiment.impl;

import cn.edu.whu.irlab.irep.base.entity.experiment.InvertedIndex;
import cn.edu.whu.irlab.irep.base.mapper.experiment.InvertedIndexMapper;
import cn.edu.whu.irlab.irep.base.dao.experiment.InvertedIndexService;
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

    @Override
    public InvertedIndex selectRecord(String term, int docId, String indexType) {
        InvertedIndex index=new InvertedIndex();
        index.setIndexType(indexType);
        index.setDocId(docId);
        index.setTerm(term);
        return invertedIndexMapper.selectRecord(index);
    }

    @Override
    public List<InvertedIndex> selectByDocId(int docId) {
        InvertedIndex index=new InvertedIndex();
        index.setDocId(docId);
        return invertedIndexMapper.select(index);
    }

    @Override
    public Integer selectTf(String term, int docId, String indexType) {
        InvertedIndex index=new InvertedIndex();
        index.setIndexType(indexType);
        index.setDocId(docId);
        index.setTerm(term);
        return invertedIndexMapper.selectTf(index);
    }

    @Override
    public List<InvertedIndex> selectByDocIdAndIndexType(int docId,String indexType){
        InvertedIndex invertedIndex=new InvertedIndex();
        invertedIndex.setDocId(docId);
        invertedIndex.setIndexType(indexType);
        return select(invertedIndex);
    }
}
