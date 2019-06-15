package cn.edu.whu.irlab.irep.mapper;

import cn.edu.whu.irlab.irep.entity.Record;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

@Mapper
@Service
public interface RecordMapper {

    int insert(Record record);

    int insertSelective(Record record);
}