package cn.edu.whu.irlab.irep.service.retrievalModel.vsmmodel;

public class ResultI implements Comparable<ResultI> {
    private int docID;
    private String title;
    private double similarity;

    public ResultI(int docID, String title, double similarity) {
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
        return "ResultI{" +
                "docID=" + docID +
                ", title='" + title + '\'' +
                ", similarity=" + similarity +
                '}';
    }

    @Override
    public int compareTo(ResultI resultI) {
        return (int) (resultI.getSimilarity() - this.getSimilarity());
    }
}
