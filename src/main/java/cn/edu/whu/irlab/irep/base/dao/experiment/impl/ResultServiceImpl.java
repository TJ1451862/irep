package cn.edu.whu.irlab.irep.base.dao.experiment.impl;

import cn.edu.whu.irlab.irep.base.entity.experiment.Result;
import cn.edu.whu.irlab.irep.base.mapper.experiment.ResultMapper;
import cn.edu.whu.irlab.irep.base.dao.experiment.ResultService;
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
    public int insertForEach(List<Result> results){
        return resultMapper.insertForEach(results);
    }

    @Override
    public List<Result> select(Result result) {
        return resultMapper.select(result);
    }

    @Override
    public List<Result> selectByRetrieverId(String retrieverId) {
        Result result=new Result();
        result.setRetrieverId(retrieverId);
        return resultMapper.select(result);
    }

    @Override
    public List<Result> selectByQueryIdAndRetrieverId(int queryId,String retrieverId) {
        Result result=new Result();
        result.setRetrieverId(retrieverId);
        result.setQueryId(queryId);
        return resultMapper.select(result);
    }
}
