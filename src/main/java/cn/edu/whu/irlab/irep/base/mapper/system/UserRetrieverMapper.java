package cn.edu.whu.irlab.irep.base.mapper.system;

import cn.edu.whu.irlab.irep.base.entity.system.UserRetriever;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UserRetrieverMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(UserRetriever record);

    int insertSelective(UserRetriever record);

    UserRetriever selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(UserRetriever record);

    int updateByPrimaryKey(UserRetriever record);
}