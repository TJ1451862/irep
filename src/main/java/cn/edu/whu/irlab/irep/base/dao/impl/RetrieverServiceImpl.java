package cn.edu.whu.irlab.irep.base.dao.impl;

import cn.edu.whu.irlab.irep.base.entity.Retriever;
import cn.edu.whu.irlab.irep.base.mapper.RetrieverMapper;
import cn.edu.whu.irlab.irep.base.dao.RetrieverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RetrieverServiceImpl implements RetrieverService {

    @Autowired
    public RetrieverMapper retrieverMapper;

    @Override
    public int insert(Retriever retriever) {
        return retrieverMapper.insert(retriever);
    }

    @Override
    public Retriever selectByPrimaryKey(String retrieverId) {
        return retrieverMapper.selectByPrimaryKey(retrieverId);
    }
}
