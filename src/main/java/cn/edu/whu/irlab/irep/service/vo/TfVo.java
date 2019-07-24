package cn.edu.whu.irlab.irep.service.vo;

/**
 * @author gcr19
 * @date 2019-07-23 23:09
 * @desc
 **/
public class TfVo {

    //文档Id
    private Integer docId;

    //词项
    private String term;

    //tf值
    private double tf;

    public TfVo(Integer docId, String term, double tf) {
        this.docId = docId;
        this.term = term;
        this.tf = tf;
    }

    public Integer getDocId() {
        return docId;
    }

    public void setDocId(Integer docId) {
        this.docId = docId;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public double getTf() {
        return tf;
    }

    public void setTf(double tf) {
        this.tf = tf;
    }
}
