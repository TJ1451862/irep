package cn.edu.whu.irlab.irep.service.vo;

/**
 * @author gcr19
 * @date 2019-07-23 22:07
 * @desc
 **/
public class IdfVo {

    //词项
    private String term;

    //词典位置
    private int num;

    //idf值
    private double idf;

    public IdfVo(String term, int num, double idf) {
        this.term = term;
        this.num = num;
        this.idf = idf;
    }

    public String getTerm() {
        return term;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public double getIdf() {
        return idf;
    }

    public void setIdf(double idf) {
        this.idf = idf;
    }


}
