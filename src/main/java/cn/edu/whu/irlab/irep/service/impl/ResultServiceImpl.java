package cn.edu.whu.irlab.irep.service.impl;

import cn.edu.whu.irlab.irep.entity.Result;
import cn.edu.whu.irlab.irep.mapper.ResultMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResultServiceImpl {

    @Autowired
    public ResultMapper resultMapper;

    public int insertSelective(Result result){
        return resultMapper.insertSelective(result);
    }

    public List<Result> selectResult(Result result){
        return resultMapper.selectResult(result);
    }
}
