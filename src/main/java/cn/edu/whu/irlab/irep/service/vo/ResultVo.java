package cn.edu.whu.irlab.irep.service.vo;

public class ResultVo {
    private int docId;
    private String title;
    private double similarity;

    public ResultVo(int docId, String title, double similarity) {
        this.docId = docId;
        this.title = title;
        this.similarity = similarity;
    }

    public int getDocId() {
        return docId;
    }

    public void setDocId(int docId) {
        this.docId = docId;
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
                "docId=" + docId +
                ", title='" + title + '\'' +
                ", similarity=" + similarity +
                '}';
    }
}
