package cn.edu.whu.irlab.irep.base.dao;


import cn.edu.whu.irlab.irep.base.entity.Opinion;

import java.util.List;

/**
 * @author fangrf
 * @date 2019-07-18 10:27
 * @desc 留言处理业务接口
 **/
public interface OpinionService {

    //添加用户填写的留言信息
    int insertOpinionService(Opinion opinion);

    //处理留言信息
    int updateOpinoinService(Opinion opinion);

    //查询所有的留言信息
    List<Opinion> selectAllOpinionService();
}
