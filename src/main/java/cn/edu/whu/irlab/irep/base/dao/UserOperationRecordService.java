package cn.edu.whu.irlab.irep.base.dao;

import cn.edu.whu.irlab.irep.base.entity.UserOperationRecord;

import java.util.List;

/**
 * @author gcr19
 * @date 2019-07-27 19:12
 * @desc 记录用户操作服务
 **/
public interface UserOperationRecordService {

    int insert(UserOperationRecord record);

    int insertSelective(UserOperationRecord record);

    UserOperationRecord selectRecord(UserOperationRecord record);

    List<UserOperationRecord> select(UserOperationRecord record);

    //新增操作记录（重复操作不记录）
    int createRecord(UserOperationRecord record);
}
