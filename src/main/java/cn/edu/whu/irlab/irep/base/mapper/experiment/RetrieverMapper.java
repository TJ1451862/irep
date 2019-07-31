package cn.edu.whu.irlab.irep.base.mapper.experiment;

import cn.edu.whu.irlab.irep.base.entity.experiment.Retriever;
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