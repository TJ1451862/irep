package cn.edu.whu.irlab.irep.base.dao.experiment;

import cn.edu.whu.irlab.irep.base.entity.experiment.Retriever;

public interface RetrieverService {

    int insert(Retriever retriever);
    Retriever selectByPrimaryKey(String retrieverId);
}
