package cn.edu.whu.irlab.irep.mapper;

import cn.edu.whu.irlab.irep.entity.Record;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface RecordMapper {
    int insert(Record record);

    int insertSelective(Record record);

    List<Record> selectByIndexType(String indexType);
}