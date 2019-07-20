package cn.edu.whu.irlab.irep.base.dao;

import cn.edu.whu.irlab.irep.base.entity.Result;

import java.util.List;

public interface ResultService {

    int insertSelective(Result result);

    List<Result> select(Result result);
}
