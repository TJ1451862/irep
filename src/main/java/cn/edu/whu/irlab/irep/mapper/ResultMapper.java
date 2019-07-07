package cn.edu.whu.irlab.irep.mapper;

import cn.edu.whu.irlab.irep.entity.Result;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ResultMapper {
    int insert(Result record);

    int insertSelective(Result record);

    List<Result> select(Result result);
}