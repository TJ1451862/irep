package cn.edu.whu.irlab.irep.base.mapper.system;

import cn.edu.whu.irlab.irep.base.entity.system.UserOperationRecord;

import java.util.List;

public interface UserOperationRecordMapper {
    int insert(UserOperationRecord record);

    int insertSelective(UserOperationRecord record);

    UserOperationRecord selectRecord(UserOperationRecord record);

    List<UserOperationRecord> select(UserOperationRecord record);
}