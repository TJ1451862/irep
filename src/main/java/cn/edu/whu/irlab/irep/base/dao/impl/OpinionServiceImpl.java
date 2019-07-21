package cn.edu.whu.irlab.irep.base.dao.impl;

import cn.edu.whu.irlab.irep.base.entity.Opinion;
import cn.edu.whu.irlab.irep.base.mapper.OpinionMapper;
import cn.edu.whu.irlab.irep.base.dao.OpinionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author fangrf
 * @version 1.0
 * @date 2019-07-18 10:28
 * @desc 留言处理业务接口
 **/
@Service
@Transactional
public class OpinionServiceImpl implements OpinionService {

    @Autowired
    private OpinionMapper opinionMapper;

    @Override
    public int insertOpinionService(Opinion opinion) {
        return opinionMapper.insertSelective(opinion);
    }


    public int updateOpinoinService(Opinion opinion) {
        return opinionMapper.updateByPrimaryKeySelective(opinion);
    }

    @Override
    public List<Opinion> selectAllOpinionService() {
        return opinionMapper.selectAll();
    }
}
