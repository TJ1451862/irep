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
    private String webId;
    private String filePath;
    private String fileName;
    private String title;
    private String content;

    public Doc(String filePath,String fileName){
        this.filePath=filePath;
        this.content= ReadDoc.readDoc(filePath);
        this.id= Find.findId(fileName,true);
    }

    //预处理文档
    public ArrayList<String> preProcess(Analyzer analyzer,boolean isRemoveStopWord){
        return preProcessor.preProcess(content,analyzer,isRemoveStopWord);
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

    public void setWebId(String webId) {
        this.webId = webId;
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

    public String getWebId() {
        return webId;
    }
}
