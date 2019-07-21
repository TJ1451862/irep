package cn.edu.whu.irlab.irep.base.dao;

import cn.edu.whu.irlab.irep.base.entity.Answer;

import java.util.List;

/**
 * @author fangrf
 * @date 2019-07-18 16:47
 * @desc 回复的业务处理接口
 **/
public interface AnswerService {

    //通过cId来查询所有的回复
    List<Answer> selectAnswerByCIdService(int cId);

    //添加提交回复
    int insertAnswerService(Answer answer);

    //删除一条评论的所有回复
    int deleteAnswerByCIdService(int cId);
}
