package cn.edu.whu.irlab.irep.service.entity;

import cn.edu.whu.irlab.irep.service.preProcess.PreProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

public class Query {

    private String content;

    private ArrayList<String> preProcessResult;

    public Query(String content, String analyzerName, boolean isRemoveStopWord) {
        this.content = content;
        setPreProcessResult(analyzerName, isRemoveStopWord);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ArrayList<String> getPreProcessResult() {
        return preProcessResult;
    }

    public void setPreProcessResult(String analyzerName, boolean isRemoveStopWord) {
        this.preProcessResult = PreProcessor.preProcess(content, analyzerName, isRemoveStopWord);
    }
}
