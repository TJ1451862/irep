package cn.edu.whu.irlab.irep.service.creatIndex;

import cn.edu.whu.irlab.irep.mapper.RecordMapper;
import cn.edu.whu.irlab.irep.entity.Doc;
import cn.edu.whu.irlab.irep.entity.Record;
import cn.edu.whu.irlab.irep.service.impl.RecordServiceImpl;
import cn.edu.whu.irlab.irep.service.preProcess.PreProcessor;
import cn.edu.whu.irlab.irep.service.util.IndexTypeConstructor;
import org.apache.lucene.analysis.Analyzer;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.ArrayList;

public class CreateIndex {

@Autowired
public PreProcessor preProcessor;

@Autowired
public RecordServiceImpl recordService;

    public String folderPath="resources/doc_ch";//待处理文档的文件夹路径

    //建立索引数据
    public void createIndex(String analyzerName,boolean isRemoveStopWord){

        File fileFolder=new File(folderPath);
        Record record=new Record();
        String indexType=IndexTypeConstructor.indexTypeConstructor(analyzerName,isRemoveStopWord);

        //向records表中插入数据
        if (fileFolder.exists()){
            File[] files=fileFolder.listFiles();
            for (File file:
                 files) {
                Doc doc=new Doc(file.getPath(),file.getName());
                Analyzer analyzer= preProcessor.analyzerSelector(analyzerName);
                ArrayList<String> termList=doc.preProcess(analyzer,isRemoveStopWord);
                int num=0;
                for (String s:
                     termList) {
                    record.setTerm(s);
                    record.setDocId(doc.getId());
                    record.setLocation(num);
                    record.setIndexType(indexType);
                    System.out.println(record.toString());
                    recordService.insertRecord(record);
                    num++;
                }
            }
        }


    }


}
