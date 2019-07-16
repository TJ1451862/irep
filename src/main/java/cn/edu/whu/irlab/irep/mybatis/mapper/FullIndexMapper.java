package cn.edu.whu.irlab.irep.mybatis.mapper;

import cn.edu.whu.irlab.irep.mybatis.entity.FullIndex;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface FullIndexMapper {
    int insert(FullIndex record);

    int insertSelective(FullIndex record);

    List<FullIndex> selectByIndexType(String indexType);
}