package cn.edu.whu.irlab.irep.service.vo;

/**
 * @author gcr19
 * @date 2019-08-03 14:49
 * @desc 词云pojo
 **/
public class TfVo2 {

    private String term;
    private Integer tf;

    public TfVo2(String term, Integer tf) {
        this.term = term;
        this.tf = tf;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public Integer getTf() {
        return tf;
    }

    public void setTf(Integer tf) {
        this.tf = tf;
    }
}
