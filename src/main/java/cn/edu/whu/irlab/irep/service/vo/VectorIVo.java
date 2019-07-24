package cn.edu.whu.irlab.irep.service.vo;

public class VectorIVo {

    private String term;

    private int num;

    private double value;

    public VectorIVo(String term, int num, double value) {
        this.term = term;
        this.num = num;
        this.value = value;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setValue(double value) {
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
        return "VectorIVo{" +
                "term:'" + term + '\'' +
                ", num:" + num +
                ", value:" + value +
                '}';
    }
}
