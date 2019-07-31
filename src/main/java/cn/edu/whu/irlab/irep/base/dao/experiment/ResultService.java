package cn.edu.whu.irlab.irep.base.dao.experiment;

import cn.edu.whu.irlab.irep.base.entity.experiment.Result;

import java.util.List;

public interface ResultService {

    int insertSelective(Result result);

    int insertForEach(List<Result> results);

    List<Result> select(Result result);

    /**
     * 按retrieverId查询
     * @param retrieverId
     * @return
     */
    List<Result> selectByRetrieverId(String retrieverId);

    /**
     * 按照query和retrieverId查询
     * @param retrieverId
     * @param queryId
     * @return
     */
    List<Result> selectByQueryIdAndRetrieverId(int queryId,String retrieverId);
}
