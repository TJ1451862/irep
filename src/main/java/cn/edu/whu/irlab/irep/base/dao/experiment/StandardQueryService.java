package cn.edu.whu.irlab.irep.base.dao.experiment;

import cn.edu.whu.irlab.irep.base.entity.experiment.StandardQuery;

import java.util.List;

/**
 * @author gcr19
 * @date 2019-07-29 07:04
 * @desc
 **/
public interface StandardQueryService {

    /**
     * 增
     * @param record
     * @return
     */
    int insert(StandardQuery record);

    int insertSelective(StandardQuery record);

    List<StandardQuery> selectAll();

    StandardQuery select(StandardQuery standardQuery);

    StandardQuery selectById(int id);

    StandardQuery selectByQueryContent(String queryContent);
}
