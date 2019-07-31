package cn.edu.whu.irlab.irep.base.mapper.experiment;

import cn.edu.whu.irlab.irep.base.entity.experiment.Result;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ResultMapper {
    int insert(Result record);

    int insertSelective(Result record);

    List<Result> select(Result result);

    int insertForEach(List<Result> results);
}