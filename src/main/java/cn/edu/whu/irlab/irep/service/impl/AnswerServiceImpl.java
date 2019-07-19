package cn.edu.whu.irlab.irep.service.impl;

import cn.edu.whu.irlab.irep.entity.Answer;
import cn.edu.whu.irlab.irep.mapper.AnswerMapper;
import cn.edu.whu.irlab.irep.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author fangrf
 * @version 1.0
 * @date 2019-07-18 16:51
 * @desc 回复业务的实现类
 **/
@Service
@Transactional
public class AnswerServiceImpl implements AnswerService {

    @Autowired
    private AnswerMapper answerMapper;

    @Override
    public List<Answer> selectAnswerByCIdService(int cId) {
        return answerMapper.selectByCId(cId);
    }

    @Override
    public int insertAnswerService(Answer answer) {
        return answerMapper.insertSelective(answer);
    }

    @Override
    public int deleteAnswerByCIdService(int cId) {
        return answerMapper.deleteByCId(cId);
    }
}
