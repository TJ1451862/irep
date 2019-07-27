package cn.edu.whu.irlab.irep.base.mapper;

import cn.edu.whu.irlab.irep.base.entity.UserOperationRecord;

import java.util.List;

public interface UserOperationRecordMapper {
    int insert(UserOperationRecord record);

    int insertSelective(UserOperationRecord record);

    UserOperationRecord selectRecord(UserOperationRecord record);

    List<UserOperationRecord> select(UserOperationRecord record);
}