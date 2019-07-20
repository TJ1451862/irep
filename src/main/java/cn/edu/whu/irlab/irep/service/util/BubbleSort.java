package cn.edu.whu.irlab.irep.service.util;

import cn.edu.whu.irlab.irep.service.vo.ResultVo;

import java.util.List;

/**
 * @author gcr19
 * @date 2019-07-18 23:34
 * @desc 冒泡排序工具类
 **/
public class BubbleSort {

    public static List<ResultVo> bubbleSort(List<ResultVo> result) {
        for (int i = 0; i < result.size(); i++) {
            for (int j = 0; j < result.size() - 1 - i; j++) {
                if (result.get(j).getSimilarity() < result.get(j + 1).getSimilarity()) {
                    ResultVo temp = result.get(j + 1);
                    result.set(j + 1, result.get(j));
                    result.set(j, temp);
                }
            }
        }
        return result;
    }

}
