package cn.edu.whu.irlab.irep.mybatis.service.impl;

import cn.edu.whu.irlab.irep.mybatis.entity.Result;
import cn.edu.whu.irlab.irep.mybatis.mapper.ResultMapper;
import cn.edu.whu.irlab.irep.mybatis.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResultServiceImpl implements ResultService {

    @Autowired
    public ResultMapper resultMapper;

    @Override
    public int insertSelective(Result result) {
        return resultMapper.insertSelective(result);
    }

    @Override
    public List<Result> select(Result result) {
        return resultMapper.select(result);
    }

}
