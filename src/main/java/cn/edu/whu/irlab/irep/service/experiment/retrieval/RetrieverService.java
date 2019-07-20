package cn.edu.whu.irlab.irep.service.experiment.retrieval;


import cn.edu.whu.irlab.irep.service.vo.ResultVo;
import cn.edu.whu.irlab.irep.service.vo.SearchResultVo;

import java.util.List;

public interface RetrieverService {

    List<SearchResultVo> search();
}
