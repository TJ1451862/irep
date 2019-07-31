package cn.edu.whu.irlab.irep.base.dao.experiment.impl;

import cn.edu.whu.irlab.irep.base.entity.experiment.FullIndex;
import cn.edu.whu.irlab.irep.base.mapper.experiment.FullIndexMapper;
import cn.edu.whu.irlab.irep.base.dao.experiment.FullIndexService;
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

    @Override
    public FullIndex selectRecord(String term, String indexType) {
        FullIndex fullIndex=new FullIndex();
        fullIndex.setIndexType(indexType);
        fullIndex.setTerm(term);
        return fullIndexMapper.selectRecord(fullIndex);
    }

    @Override
    public Integer selectDf(String term, String indexType) {
        FullIndex fullIndex=new FullIndex();
        fullIndex.setIndexType(indexType);
        fullIndex.setTerm(term);
        return fullIndexMapper.selectDf(fullIndex);
    }
}
