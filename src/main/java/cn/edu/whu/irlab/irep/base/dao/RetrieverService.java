package cn.edu.whu.irlab.irep.base.dao;

import cn.edu.whu.irlab.irep.base.entity.Retriever;

public interface RetrieverService {

    int insert(Retriever retriever);
    Retriever selectByPrimaryKey(String retrieverId);
}
