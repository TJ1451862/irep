package cn.edu.whu.irlab.irep.mybatis.service;

import cn.edu.whu.irlab.irep.mybatis.entity.Retriever;

public interface RetrieverService {

    int insert(Retriever retriever);
    Retriever selectByPrimaryKey(String retrieverId);
}
