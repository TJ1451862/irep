package cn.edu.whu.irlab.irep.service.experiment.retrieval.boolmodel;

import cn.edu.whu.irlab.irep.base.entity.Result;
import cn.edu.whu.irlab.irep.service.entity.Doc;

import java.util.HashMap;
import java.util.Map;


public class ResultForBool extends Result {

    private int docid;
    private String doctitle;

    public ResultForBool(int id) {
        this.docid=id;
    }


    public int getDocID() {
        return docid;
    }

    public void setDocID(int docID) {
        this.docid = docID;
    }

    public String getTitle() {
        return doctitle;
    }

    public void setTitle(String title) {
        this.doctitle = title;
    }



}