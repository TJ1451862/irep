package cn.edu.whu.irlab.irep.base.dao;

import cn.edu.whu.irlab.irep.base.entity.Experiment;

/**
 * @author gcr19
 * @date 2019-07-25 06:35
 * @desc 实验类服务 接口
 **/
public interface ExperimentService {

    //通过Id查询实验信息
    Experiment selectById(int id);

    //更新实验信息
    int updateById(Experiment experiment);

}
