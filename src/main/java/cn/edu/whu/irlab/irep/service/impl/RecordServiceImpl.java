package cn.edu.whu.irlab.irep.service.impl;

import cn.edu.whu.irlab.irep.mapper.RecordMapper;
import cn.edu.whu.irlab.irep.entity.Record;
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
public class RecordServiceImpl {

    /**
     * 插入一条记录
     *
     * @param record 用户信息
     * @return 结果
     */


    @Autowired
    public RecordMapper recordMapper;

    public int insert(Record record) {
        return recordMapper.insert(record);
    }

    public List<Record> selectByIndexType(String indexType){
        return recordMapper.selectByIndexType(indexType);
    }



}
