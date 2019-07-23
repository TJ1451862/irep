package cn.edu.whu.irlab.irep.service.experiment;

import cn.edu.whu.irlab.irep.base.entity.FullIndex;
import cn.edu.whu.irlab.irep.base.entity.InvertedIndex;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author gcr19
 * @date 2019-07-20 15:12
 * @desc
 **/
public interface IndexService {
    //加载索引
    String loadIndex(HttpServletRequest request);

    //查询全体倒排索引表
    List<FullIndex> selectFullIndex();

    //查询倒排索引表
    List<InvertedIndex> selectInvertedIndex(String term);

    //查询Df值
    int selectDf(String term);

    //查询Tf值
    int selectTf(String term, int docId);

    //查询文档长度
    int selectDocLength(int docId);
}