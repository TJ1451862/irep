package cn.edu.whu.irlab.irep.base.dao.experiment;

import cn.edu.whu.irlab.irep.base.entity.experiment.InvertedIndex;

import java.util.List;

public interface InvertedIndexService {

    int insert(InvertedIndex invertedIndex);

    List<InvertedIndex> selectByIndexType(String indexType);

    List<InvertedIndex> select(InvertedIndex invertedIndex);

    //按文档Id查询
    List<InvertedIndex> selectByDocId(int docId);

    //查询一条记录
    InvertedIndex selectRecord(String term, int docId, String indexType);

    //查询tf值
    Integer selectTf(String term, int docId, String indexType);

}
