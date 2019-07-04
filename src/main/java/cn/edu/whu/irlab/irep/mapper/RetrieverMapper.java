package cn.edu.whu.irlab.irep.mapper;

import cn.edu.whu.irlab.irep.entity.Retriever;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;


/**
 * @author gcr
 * @date 2019-07-04 07:31
 * @desc Retriever表接口
 */
@Mapper
@Component
public interface RetrieverMapper {
    int deleteByPrimaryKey(String retrieverId);

    int insert(Retriever record);

    int insertSelective(Retriever record);

    Retriever selectByPrimaryKey(String retrieverId);

    int updateByPrimaryKeySelective(Retriever record);

    int updateByPrimaryKey(Retriever record);
}