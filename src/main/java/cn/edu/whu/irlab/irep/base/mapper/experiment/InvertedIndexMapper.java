package cn.edu.whu.irlab.irep.base.mapper.experiment;

import cn.edu.whu.irlab.irep.base.entity.experiment.InvertedIndex;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface InvertedIndexMapper {
    int insert(InvertedIndex record);

    int insertSelective(InvertedIndex record);

    List<InvertedIndex> selectByIndexType(String indexType);

    List<InvertedIndex> select(InvertedIndex invertedIndex);

    //查询一条记录
    InvertedIndex selectRecord(InvertedIndex invertedIndex);

    //查询tf值
    Integer selectTf(InvertedIndex invertedIndex);
}