package cn.edu.whu.irlab.irep.base.dao.experiment.impl;

import cn.edu.whu.irlab.irep.base.dao.experiment.StandardQueryService;
import cn.edu.whu.irlab.irep.base.entity.experiment.StandardQuery;
import cn.edu.whu.irlab.irep.base.mapper.experiment.StandardQueryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author gcr19
 * @date 2019-07-29 07:05
 * @desc
 **/
@Service
public class StandardQueryServiceImpl implements StandardQueryService {

    @Autowired
    private StandardQueryMapper standardQueryMapper;

    @Override
    public int insert(StandardQuery record) {
        return standardQueryMapper.insert(record);
    }

    @Override
    public int insertSelective(StandardQuery record) {
        return standardQueryMapper.insertSelective(record);
    }

    @Override
    public List<StandardQuery> selectAll() {
        return standardQueryMapper.selectAll();
    }

    @Override
    public StandardQuery select(StandardQuery standardQuery) {
        return standardQueryMapper.select(standardQuery);
    }

    @Override
    public StandardQuery selectById(int id){
        StandardQuery standardQuery=new StandardQuery(id,null);
        return standardQueryMapper.select(standardQuery);
    }

    @Override
    public StandardQuery selectByQueryContent(String queryContent){
        StandardQuery standardQuery=new StandardQuery(null,queryContent);
        return standardQueryMapper.select(standardQuery);
    }
}
