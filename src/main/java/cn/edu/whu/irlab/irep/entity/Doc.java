package cn.edu.whu.irlab.irep.entity;

import cn.edu.whu.irlab.irep.service.preProcess.PreProcessor;
import cn.edu.whu.irlab.irep.service.util.Find;
import cn.edu.whu.irlab.irep.service.util.ReadDoc;
import org.apache.lucene.analysis.Analyzer;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

public class Doc {

    @Autowired
    public PreProcessor preProcessor;

    private int id;
    private String filePath;
    private String fileName;
    private String title;
    private String content;

    public Doc(String filePath, String fileName) {
        this.filePath = filePath;
        this.fileName = fileName;
        this.content = ReadDoc.readDoc(filePath);
        this.id = Find.findId(fileName, true);
    }

    public Doc(int id) {
        this.id = id;
        this.title=Find.findTitle(id,true);
    }

    //预处理文档
    public ArrayList<String> preProcess(String analyzerName, boolean isRemoveStopWord) {
        return preProcessor.preProcess(content, analyzerName, isRemoveStopWord);
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
