package cn.edu.whu.irlab.irep.service.vo;

public class ResultVo {
    private int docID;
    private String title;
    private double similarity;

    public ResultVo(int docID, String title, double similarity) {
        this.docID = docID;
        this.title = title;
        this.similarity = similarity;
    }

    public int getDocID() {
        return docID;
    }

    public void setDocID(int docID) {
        this.docID = docID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getSimilarity() {
        return similarity;
    }

    public void setSimilarity(double similarity) {
        this.similarity = similarity;
    }

    @Override
    public String toString() {
        return "ResultVo{" +
                "docID=" + docID +
                ", title='" + title + '\'' +
                ", similarity=" + similarity +
                '}';
    }
}
