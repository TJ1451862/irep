package cn.edu.whu.irlab.irep.base.dao.impl;

import cn.edu.whu.irlab.irep.base.entity.Result;
import cn.edu.whu.irlab.irep.base.mapper.ResultMapper;
import cn.edu.whu.irlab.irep.base.dao.ResultService;
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