package cn.edu.whu.irlab.irep.mybatis.service;

import cn.edu.whu.irlab.irep.mybatis.entity.Record;

import java.util.List;

public interface RecordService {

    int insert(Record record);

    List<Record> selectByIndexType(String indexType);
}
