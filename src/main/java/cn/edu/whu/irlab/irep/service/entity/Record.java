package cn.edu.whu.irlab.irep.service.entity;

public class Record {

    private String term;
    private String docID;
    private int location;
    private String indexType;

    public void setTerm(String term) {
        this.term = term;
    }

    public void setDocID(String docID) {
        this.docID = docID;
    }

    public void setIndexType(String indexType) {
        this.indexType = indexType;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public String getTerm() {
        return term;
    }

    public int getLocation() {
        return location;
    }

    public String getDocID() {
        return docID;
    }

    public String getIndexType() {
        return indexType;
    }
}
