package cn.edu.whu.irlab.irep.base.dao.experiment.impl;

import cn.edu.whu.irlab.irep.base.entity.experiment.Record;
import cn.edu.whu.irlab.irep.base.mapper.experiment.RecordMapper;
import cn.edu.whu.irlab.irep.base.dao.experiment.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author gcr
 * @version 1.0
 * @date 2019-06-15 13:40
 * @desc RecordService
 **/

@Service
public class RecordServiceImpl implements RecordService {

    @Autowired
    public RecordMapper recordMapper;

    @Override
    public int insert(Record record) {
        return recordMapper.insert(record);
    }

    @Override
    public List<Record> selectByIndexType(String indexType) {
        return recordMapper.selectByIndexType(indexType);
    }

    @Override
    public List<Record> selectByDocIdAndIndexType(int docId, String indexType) {
        Record record=new Record();
        record.setDocId(docId);
        record.setIndexType(indexType);
        return recordMapper.select(record);
    }

    @Override
    public Integer selectLengthByDocIdAndIndexType(int docId, String indexType) {
        Record record=new Record();
        record.setDocId(docId);
        record.setIndexType(indexType);
        return recordMapper.selectLength(record);
    }
}
