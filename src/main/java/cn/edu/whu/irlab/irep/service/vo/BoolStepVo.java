package cn.edu.whu.irlab.irep.service.vo;

import java.util.Set;

/**
 * @author gcr19
 * @date 2019-08-01 22:01
 * @desc
 **/
public class BoolStepVo {

    private String operator;

    private Set<Integer> perStep;

    private Set<Integer> termSet;

    private String term;

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Set<Integer> getPerStep() {
        return perStep;
    }

    public void setPerStep(Set<Integer> perStep) {
        this.perStep = perStep;
    }

    public Set<Integer> getTermSet() {
        return termSet;
    }

    public void setTermSet(Set<Integer> termSet) {
        this.termSet = termSet;
    }
}
