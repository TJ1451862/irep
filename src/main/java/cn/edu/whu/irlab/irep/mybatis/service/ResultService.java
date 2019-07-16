package cn.edu.whu.irlab.irep.mybatis.service;

import cn.edu.whu.irlab.irep.mybatis.entity.Result;

import java.util.List;

public interface ResultService {

    int insertSelective(Result result);

    List<Result> select(Result result);
}
