package cn.edu.whu.irlab.irep.base.dao.experiment;

import cn.edu.whu.irlab.irep.base.entity.experiment.FullIndex;

import java.util.List;

public interface FullIndexService {

    int insert(FullIndex fullIndex);

    List<FullIndex> selectByIndexType(String indexType);

    //查询一条记录
    FullIndex selectRecord(String term, String indexType);

    //查询df值
    Integer selectDf(String term, String indexType);
}
