package cn.edu.whu.irlab.irep.service.experiment.creatIndex;

import cn.edu.whu.irlab.irep.base.dao.FullIndexService;
import cn.edu.whu.irlab.irep.base.dao.InvertedIndexService;
import cn.edu.whu.irlab.irep.base.dao.RecordService;
import cn.edu.whu.irlab.irep.base.dao.impl.InvertedIndexServiceImpl;
import cn.edu.whu.irlab.irep.base.entity.FullIndex;
import cn.edu.whu.irlab.irep.base.entity.InvertedIndex;
import cn.edu.whu.irlab.irep.base.entity.Record;
import cn.edu.whu.irlab.irep.service.experiment.IndexService;
import cn.edu.whu.irlab.irep.service.util.Constructor;
import cn.edu.whu.irlab.irep.service.vo.IndexVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author gcr19
 * @date 2019-07-20 14:50
 * @desc
 **/
@Service
public class IndexServiceImpl implements IndexService {

    @Autowired
    private FullIndexService fullIndexService;

    @Autowired
    public InvertedIndexService invertedIndexService;

    @Autowired
    private RecordService recordService;

    @Autowired
    private IndexGenerator indexGenerator;


    private final static String folderPath = "resources/doc_ch";

    public String loadIndex(HttpServletRequest request) {

        String indexType = Constructor.indexTypeConstructor(request);
        List<FullIndex> fullIndexList = fullIndexService.selectByIndexType(indexType);
        if (fullIndexList.size() == 0) {
            indexGenerator.initIndexGenerator(folderPath, request);
            indexGenerator.generateIndex();
            fullIndexList = fullIndexService.selectByIndexType(indexType);
        }
        List<InvertedIndex> invertedIndexList = invertedIndexService.selectByIndexType(indexType);
        List<Record> recordList = recordService.selectByIndexType(indexType);

        IndexVo.setFullIndexList(fullIndexList);
        IndexVo.setInvertedIndexList(invertedIndexList);
        IndexVo.setRecordList(recordList);

        System.out.println("索引加载完成");
        return "索引加载完成";
    }

    public List<FullIndex> selectFullIndex() {

        List<FullIndex> fullIndexList = IndexVo.getFullIndexList();
        return fullIndexList;
    }

    public List<InvertedIndex> selectInvertedIndex(String term) {
        List<InvertedIndex> all = IndexVo.getInvertedIndexList();
        List<InvertedIndex> result = new ArrayList<>();
        for (int i = 0; i < all.size(); i++) {
            InvertedIndex index = all.get(i);
            if (term.equals(index.getTerm())) {
                result.add(index);
            }
        }
        return result;
    }

    @Override
    public int selectDf(String term) {
        List<FullIndex> all = IndexVo.getFullIndexList();
        int df = 0;
        for (FullIndex i :
                all) {
            if (term.equals(i.getTerm())) {
                df = i.getDf();
            }
        }
        return df;
    }

    @Override
    public int selectTf(String term, int docId) {
        List<InvertedIndex> all = IndexVo.getInvertedIndexList();
        int tf = 0;
        for (InvertedIndex i :
                all) {
            if ((term.equals(i.getTerm())) && (docId == i.getDocId())) {
                tf = i.getTf();
            }
        }
        return tf;
    }

    @Override
    public int selectDocLength(int docId) {
        List<Record> all = IndexVo.getRecordList();
        List<Record> result = new ArrayList<>();
        for (Record i :
                all) {
            if (i.getDocId() == docId) {
                result.add(i);
            }
        }
        return result.size();
    }
}
