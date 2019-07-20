package cn.edu.whu.irlab.irep.service.vo;

import cn.edu.whu.irlab.irep.base.entity.FullIndex;
import cn.edu.whu.irlab.irep.base.entity.InvertedIndex;
import cn.edu.whu.irlab.irep.base.entity.Record;

import java.util.List;

/**
 * @author gcr19
 * @date 2019-07-20 14:44
 * @desc
 **/
public class IndexVo {

    private static List<Record> recordList;

    private static List<InvertedIndex> invertedIndexList;

    private static List<FullIndex> fullIndexList;

    public static List<Record> getRecordList() {
        return recordList;
    }

    public static void setRecordList(List<Record> recordList) {
        IndexVo.recordList = recordList;
    }

    public static List<InvertedIndex> getInvertedIndexList() {
        return invertedIndexList;
    }

    public static void setInvertedIndexList(List<InvertedIndex> invertedIndexList) {
        IndexVo.invertedIndexList = invertedIndexList;
    }

    public static List<FullIndex> getFullIndexList() {
        return fullIndexList;
    }

    public static void setFullIndexList(List<FullIndex> fullIndexList) {
        IndexVo.fullIndexList = fullIndexList;
    }
}
