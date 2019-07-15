package cn.edu.whu.irlab.irep.mapper;

import cn.edu.whu.irlab.irep.entity.InvertedIndex;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface InvertedIndexMapper {
    int insert(InvertedIndex record);

    int insertSelective(InvertedIndex record);

    List<InvertedIndex> selectByIndexType(String indexType);

    List<InvertedIndex> select(InvertedIndex invertedIndex);
}