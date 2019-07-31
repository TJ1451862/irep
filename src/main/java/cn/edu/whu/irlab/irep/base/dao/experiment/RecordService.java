package cn.edu.whu.irlab.irep.base.dao.experiment;

import cn.edu.whu.irlab.irep.base.entity.experiment.Record;

import java.util.List;

public interface RecordService {

    int insert(Record record);

    List<Record> selectByIndexType(String indexType);

    //按照文档Id和IndexType查询
    List<Record> selectByDocIdAndIndexType(int docId, String indexType);

    //按照文档Id和IndexType查询符合条件的记录
    Integer selectLengthByDocIdAndIndexType(int docId, String indexType);
}
