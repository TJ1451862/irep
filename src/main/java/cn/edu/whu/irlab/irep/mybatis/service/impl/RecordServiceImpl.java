package cn.edu.whu.irlab.irep.mybatis.service.impl;

import cn.edu.whu.irlab.irep.mybatis.entity.Record;
import cn.edu.whu.irlab.irep.mybatis.mapper.RecordMapper;
import cn.edu.whu.irlab.irep.mybatis.service.RecordService;
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

    /**
     * 插入一条记录
     *
     * @param record 用户信息
     * @return 结果
     */


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


}
