package cn.edu.whu.irlab.irep.service.vo;

/**
 * @author gcr19
 * @date 2019-08-06 10:31
 * @desc
 **/
public class LMVo {

    //文档Id
    private Integer docId;

    //词项
    private String term;

    //tf值
    private double tf;

    public LMVo(Integer docId, String term, double tf) {
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
