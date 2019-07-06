package cn.edu.whu.irlab.irep.mapper;

import cn.edu.whu.irlab.irep.entity.Retriever;
import org.springframework.stereotype.Component;

@Component
public interface RetrieverMapper {
    int deleteByPrimaryKey(String retrieverId);

    int insert(Retriever record);

    int insertSelective(Retriever record);

    Retriever selectByPrimaryKey(String retrieverId);

    int updateByPrimaryKeySelective(Retriever record);

    int updateByPrimaryKey(Retriever record);
}