package cn.edu.whu.irlab.irep.service.vo;

/**
 * @author gcr19
 * @date 2019-07-18 14:43
 * @desc
 **/
public class BijVo {

    //词项
    private String term;

    //文档Id
    private int docId;

    //Bij系数
    private double bij;

    public BijVo(String term, int docId, double bij) {
        this.term = term;
        this.docId = docId;
        this.bij = bij;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public int getDocId() {
        return docId;
    }

    public void setDocId(int docId) {
        this.docId = docId;
    }

    public double getBij() {
        return bij;
    }

    public void setBij(double bij) {
        this.bij = bij;
    }
}
