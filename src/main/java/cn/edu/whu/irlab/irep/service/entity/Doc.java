package cn.edu.whu.irlab.irep.service.entity;

import cn.edu.whu.irlab.irep.service.preProcess.PreProcessor;
import cn.edu.whu.irlab.irep.service.util.ReadDoc;
import org.apache.lucene.analysis.Analyzer;

import java.util.ArrayList;

public class Doc {
    private String id;
    private String webId;
    private String filePath;
    private String title;
    private String content;

    public Doc(String filePath){
        this.filePath=filePath;
        this.content= ReadDoc.readDoc(filePath);
    }

    //预处理文档
    public ArrayList<String> preProcess(Analyzer analyzer,boolean isRemoveStopWord){
        return PreProcessor.preProcess(content,analyzer,isRemoveStopWord);
    }



}
