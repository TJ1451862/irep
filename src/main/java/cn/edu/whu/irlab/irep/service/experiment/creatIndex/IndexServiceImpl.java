package cn.edu.whu.irlab.irep.service.experiment.creatIndex;

import cn.edu.whu.irlab.irep.base.dao.experiment.FullIndexService;
import cn.edu.whu.irlab.irep.base.dao.experiment.InvertedIndexService;
import cn.edu.whu.irlab.irep.base.dao.experiment.RecordService;
import cn.edu.whu.irlab.irep.base.entity.experiment.FullIndex;
import cn.edu.whu.irlab.irep.base.entity.experiment.InvertedIndex;
import cn.edu.whu.irlab.irep.base.entity.experiment.Record;
import cn.edu.whu.irlab.irep.service.experiment.IndexService;
import cn.edu.whu.irlab.irep.service.util.Constructor;
import cn.edu.whu.irlab.irep.service.vo.IndexVo;
import cn.edu.whu.irlab.irep.service.vo.TfVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
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

    private HttpSession session;


    private final static String folderPath = "resources/doc_ch";

    public int loadIndex(HttpServletRequest request) {

        this.session=request.getSession();
        String indexType = Constructor.indexTypeConstructor(request);
        //检查数据库是否存在该类索引，不存在则创建索引
        List<FullIndex> fullIndexList = fullIndexService.selectByIndexType(indexType);
        if (fullIndexList.size() == 0) {
            indexGenerator.initIndexGenerator(folderPath, request);
            indexGenerator.generateIndex();
            fullIndexList = fullIndexService.selectByIndexType(indexType);
        }

        //此处可能存在冲突
        List<InvertedIndex> invertedIndexList = invertedIndexService.selectByIndexType(indexType);
        List<Record> recordList = recordService.selectByIndexType(indexType);

        IndexVo indexVo=new IndexVo(recordList,invertedIndexList,fullIndexList);
        session.setAttribute("indexVo",indexVo);

        return 1;
    }

    public List<FullIndex> selectFullIndex() {
        IndexVo indexVo=(IndexVo)session.getAttribute("indexVo");
        List<FullIndex> fullIndexList = indexVo.getFullIndexList();
        return fullIndexList;
    }

    public List<InvertedIndex> selectInvertedIndex(String term) {
        IndexVo indexVo=(IndexVo)session.getAttribute("indexVo");
        List<InvertedIndex> all = indexVo.getInvertedIndexList();
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
        IndexVo indexVo=(IndexVo)session.getAttribute("indexVo");
        List<FullIndex> all = indexVo.getFullIndexList();
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
        IndexVo indexVo=(IndexVo)session.getAttribute("indexVo");
        List<InvertedIndex> all = indexVo.getInvertedIndexList();
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
        IndexVo indexVo=(IndexVo)session.getAttribute("indexVo");
        List<Record> all = indexVo.getRecordList();
        List<Record> result = new ArrayList<>();
        for (Record i :
                all) {
            if (i.getDocId() == docId) {
                result.add(i);
            }
        }
        return result.size();
    }

    @Override
    public List<TfVo> selectDocTf(int docId) {
        IndexVo indexVo=(IndexVo)session.getAttribute("indexVo");
        List<InvertedIndex> all = indexVo.getInvertedIndexList();
        List<TfVo> tfVoList=new ArrayList<>();
        for (InvertedIndex i :
                all) {
            if (i.getDocId()==docId){
                TfVo tfVo=new TfVo(i.getDocId(),i.getTerm(),i.getTf());
                tfVoList.add(tfVo);
            }
        }
        return tfVoList;
    }
}
