package cn.edu.whu.irlab.irep.base.dao;

import cn.edu.whu.irlab.irep.base.entity.UserRetriever;

public interface UserRetrieverService {
    /**
     * 删除记录 根据用户Id
     * @param userId 用户Id
     * @return
     */
    int deleteByPrimaryKey(Integer userId);

    /**
     * 插入记录
     * @param record 记录
     * @return
     */
    int insert(UserRetriever record);
    int insertSelective(UserRetriever record);

    UserRetriever selectByPrimaryKey(Integer userId);
    int updateByPrimaryKeySelective(UserRetriever record);
    int updateByPrimaryKey(UserRetriever record);
}
