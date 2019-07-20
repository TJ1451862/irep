package cn.edu.whu.irlab.irep.service.experiment.retrieval.vsmModel;

public class VectorI {

    private String term;

    private int num;

    private double value;

    public VectorI(String term, int num, double value) {
        this.term = term;
        this.num = num;
        this.value = value;
    }

    public String getTerm() {
        return term;
    }

    public int getNum() {
        return num;
    }

    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "VectorI{" +
                "term:'" + term + '\'' +
                ", num:" + num +
                ", value:" + value +
                '}';
    }
}
