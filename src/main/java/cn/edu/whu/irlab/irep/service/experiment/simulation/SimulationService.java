package cn.edu.whu.irlab.irep.service.experiment.simulation;

import cn.edu.whu.irlab.irep.service.vo.SearchResultVo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author gcr19
 * @date 2019-08-08 07:40
 * @desc
 **/
public interface SimulationService {
    int selectModel(String modelName, HttpServletRequest request);

    List<SearchResultVo> search(String query, HttpServletRequest request);
}
