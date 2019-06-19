package cn.edu.whu.irlab.irep.mapper;

import cn.edu.whu.irlab.irep.entity.Result;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface ResultMapper {
    int insert(Result record);

    int insertSelective(Result record);
}