package cn.edu.whu.irlab.irep.service.experiment.preProcess;

import cn.edu.whu.irlab.irep.service.vo.TfVo2;

import java.util.List;

/**
 * @author gcr19
 * @date 2019-08-03 16:37
 * @desc
 **/
public interface PreProcessorService {
    List<TfVo2> createTermCloud(int docId, String analyzerName, boolean isRemoveStopWord);
}
