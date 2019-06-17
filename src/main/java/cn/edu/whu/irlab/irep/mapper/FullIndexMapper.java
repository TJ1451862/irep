package cn.edu.whu.irlab.irep.mapper;

import cn.edu.whu.irlab.irep.entity.FullIndex;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface FullIndexMapper {
    int insert(FullIndex record);

    int insertSelective(FullIndex record);

    List<FullIndex> selectFullIndexByIndexType(String indexType);

}