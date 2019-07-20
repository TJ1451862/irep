package cn.edu.whu.irlab.irep.service.experiment.retrieval.languagemodel;

public class ResultForLM {
    private int docID;
    private String title;
    private double GenerateProbability;

    public ResultForLM(int docID, String title, double GenerateProbability) {
        this.docID = docID;
        this.title = title;
        this.GenerateProbability = GenerateProbability;
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

    public double getGenerateProbability() {
        return GenerateProbability;
    }

    public void setGenerateProbability(double GenerateProbability) {
        this.GenerateProbability = GenerateProbability;
    }
}
