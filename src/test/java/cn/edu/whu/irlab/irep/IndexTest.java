package cn.edu.whu.irlab.irep;

import cn.edu.whu.irlab.irep.entity.Doc;
import cn.edu.whu.irlab.irep.entity.FullIndex;
import cn.edu.whu.irlab.irep.entity.InvertedIndex;
import cn.edu.whu.irlab.irep.entity.Record;
import cn.edu.whu.irlab.irep.service.impl.FullIndexServiceImpl;
import cn.edu.whu.irlab.irep.service.impl.InvertedIndexServiceImpl;
import cn.edu.whu.irlab.irep.service.impl.RecordServiceImpl;
import cn.edu.whu.irlab.irep.service.preProcess.PreProcessor;
import cn.edu.whu.irlab.irep.service.util.IndexTypeConstructor;
import org.apache.lucene.analysis.Analyzer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = IrepApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IndexTest {


    @Autowired
    public PreProcessor preProcessor;

    @Autowired
    public RecordServiceImpl recordService;

    @Autowired
    public InvertedIndexServiceImpl invertedIndexService;

    @Autowired
    public FullIndexServiceImpl fullIndexService;

    @Autowired
    public IndexTypeConstructor indexTypeConstructor;

    public String folderPath = "resources/doc_ch";//待处理文档的文件夹路径

    @Test
    public void indexTest() {
        File fileFolder = new File(folderPath);
        String analyzerName = "standard";
        boolean isRemoveStopWord = true;
        String indexType = indexTypeConstructor.indexTypeConstructor(analyzerName, isRemoveStopWord);

        //向records表中插入数据
        if (fileFolder.exists()) {
            File[] files = fileFolder.listFiles();
            for (File file :
                    files) {
                Doc doc = new Doc(file.getPath(), file.getName());
                ArrayList<String> termList = doc.preProcess(analyzerName, isRemoveStopWord);
                int num = 0;
                for (String s :
                        termList) {
                    Record record = new Record();
                    record.setTerm(s);
                    record.setDocId(doc.getId());
                    record.setLocation(num);
                    record.setIndexType(indexType);
                    //System.out.println(record.toString());
                    recordService.insert(record);
                    num++;
                }
            }
        }
    }

    @Test
    public void createInvertedIndexTest() {
        List<Record> recordList = new ArrayList<>();
        recordList = recordService.selectByIndexType("standard_remove");
        System.out.println(recordList.size());
        Map<String, List<Record>> termId_Record = new HashMap<>();
        String termID;

        //按照term和doc_id聚合record
        for (Record r :
                recordList) {
            List<Record> records = new ArrayList<>();
            termID = r.getTerm() + r.getDocId();
            if (termId_Record.containsKey(termID)) {
                records = termId_Record.get(termID);
                records.add(r);
                termId_Record.put(termID, records);
            } else {
                records.add(r);
                termId_Record.put(termID, records);
            }
        }
        System.out.println(termId_Record.size());
        for (String s :
                termId_Record.keySet()) {
            String locations = "";
            Record tempRecord = termId_Record.get(s).get(0);
            InvertedIndex invertedIndex = new InvertedIndex();
            invertedIndex.setDocId(tempRecord.getDocId());
            invertedIndex.setIndexType(tempRecord.getIndexType());
            invertedIndex.setTerm(tempRecord.getTerm());
            invertedIndex.setTf(termId_Record.get(s).size());
            //构造locations
            for (Record r :
                    termId_Record.get(s)) {
                locations += r.getLocation() + ":";
            }
            locations = locations.substring(0, locations.length() - 1);
            invertedIndex.setLocations(locations);
//            System.out.println(invertedIndex);
            invertedIndexService.insert(invertedIndex);
        }
    }

    @Test
    public void createFullIndex() {
        List<InvertedIndex> invertedIndexList = new ArrayList<>();
        invertedIndexList = invertedIndexService.selectByIndexType("standard_remove");
        System.out.println(invertedIndexList.size());
        Map<String, List<InvertedIndex>> termInvertedIndex = new HashMap<>();
        String term;
        //按term聚合Inverted Index
        for (InvertedIndex i :
                invertedIndexList) {
            List<InvertedIndex> invertedIndexes = new ArrayList<>();
            term = i.getTerm();
            if (termInvertedIndex.containsKey(term)) {
                invertedIndexes = termInvertedIndex.get(term);
                invertedIndexes.add(i);
                termInvertedIndex.put(term, invertedIndexes);
            } else {
                invertedIndexes.add(i);
                termInvertedIndex.put(term, invertedIndexes);
            }
        }
        System.out.println(termInvertedIndex.size());
        for (String s :
                termInvertedIndex.keySet()) {
            String ids = "";
            InvertedIndex tempInvertedIndex = termInvertedIndex.get(s).get(0);
            FullIndex fullIndex = new FullIndex();
            fullIndex.setTerm(tempInvertedIndex.getTerm());
            fullIndex.setIndexType(tempInvertedIndex.getIndexType());
            fullIndex.setDf(termInvertedIndex.get(s).size());
            //构造ids
            for (InvertedIndex i :
                    termInvertedIndex.get(s)) {
                ids+=i.getDocId()+":";
            }
            ids=ids.substring(0,ids.length()-1);
            fullIndex.setIds(ids);
            fullIndexService.insert(fullIndex);
        }

    }


}
