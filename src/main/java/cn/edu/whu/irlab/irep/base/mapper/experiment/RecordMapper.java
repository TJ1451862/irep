package cn.edu.whu.irlab.irep.base.mapper.experiment;

import cn.edu.whu.irlab.irep.base.entity.experiment.Record;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface RecordMapper {
    int insert(Record record);

    List<Record> select(Record record);

    int insertSelective(Record record);

    //按indexType查询
    List<Record> selectByIndexType(String indexType);

    //查询符合条件的记录数量
    Integer selectLength(Record record);
}