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

    private List<Record> recordList;

    private List<InvertedIndex> invertedIndexList;

    private List<FullIndex> fullIndexList;

    public IndexVo(List<Record> recordList, List<InvertedIndex> invertedIndexList, List<FullIndex> fullIndexList) {
        this.recordList = recordList;
        this.invertedIndexList = invertedIndexList;
        this.fullIndexList = fullIndexList;
    }

    public List<Record> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<Record> recordList) {
        this.recordList = recordList;
    }

    public List<InvertedIndex> getInvertedIndexList() {
        return invertedIndexList;
    }

    public void setInvertedIndexList(List<InvertedIndex> invertedIndexList) {
        this.invertedIndexList = invertedIndexList;
    }

    public List<FullIndex> getFullIndexList() {
        return fullIndexList;
    }

    public void setFullIndexList(List<FullIndex> fullIndexList) {
        this.fullIndexList = fullIndexList;
    }
}
