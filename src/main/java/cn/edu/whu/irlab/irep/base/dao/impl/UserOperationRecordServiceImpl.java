package cn.edu.whu.irlab.irep.base.dao.impl;

import cn.edu.whu.irlab.irep.base.dao.UserOperationRecordService;
import cn.edu.whu.irlab.irep.base.entity.UserOperationRecord;
import cn.edu.whu.irlab.irep.base.mapper.UserOperationRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author gcr19
 * @date 2019-07-27 20:00
 * @desc
 **/
@Service
public class UserOperationRecordServiceImpl implements UserOperationRecordService {

    @Autowired
    private UserOperationRecordMapper userOperationRecordMapper;

    @Override
    public int insert(UserOperationRecord record) {
        return userOperationRecordMapper.insert(record);
    }

    @Override
    public int insertSelective(UserOperationRecord record) {
        return userOperationRecordMapper.insertSelective(record);
    }

    @Override
    public UserOperationRecord selectRecord(UserOperationRecord record) {
        return userOperationRecordMapper.selectRecord(record);
    }

    @Override
    public List<UserOperationRecord> select(UserOperationRecord record) {
        return userOperationRecordMapper.select(record);
    }

    @Override
    public int createRecord(UserOperationRecord record) {
        UserOperationRecord temp=userOperationRecordMapper.selectRecord(record);
        if (temp!=null){
            return 0;
        }else {
            return userOperationRecordMapper.insertSelective(record);
        }
    }
}
