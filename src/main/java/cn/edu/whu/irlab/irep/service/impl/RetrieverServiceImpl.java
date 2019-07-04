package cn.edu.whu.irlab.irep.service.impl;

import cn.edu.whu.irlab.irep.entity.Retriever;
import cn.edu.whu.irlab.irep.mapper.RetrieverMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RetrieverServiceImpl {

    @Autowired
    public RetrieverMapper retrieverMapper;

    public int insert(Retriever retriever) {
        return retrieverMapper.insert(retriever);
    }

    public Retriever selectByPrimaryKey(String retrieverId){
        return retrieverMapper.selectByPrimaryKey(retrieverId);
    }
}
