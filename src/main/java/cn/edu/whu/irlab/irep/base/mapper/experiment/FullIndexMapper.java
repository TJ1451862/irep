package cn.edu.whu.irlab.irep.base.mapper.experiment;

import cn.edu.whu.irlab.irep.base.entity.experiment.FullIndex;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface FullIndexMapper {
    int insert(FullIndex record);

    int insertSelective(FullIndex record);

    List<FullIndex> selectByIndexType(String indexType);

    //查询一条记录
    FullIndex selectRecord(FullIndex fullIndex);

    //查询df值
    Integer selectDf(FullIndex fullIndex);
}