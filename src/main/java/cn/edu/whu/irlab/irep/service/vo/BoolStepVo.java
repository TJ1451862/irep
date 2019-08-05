package cn.edu.whu.irlab.irep.service.vo;

import java.util.Set;

/**
 * @author gcr19
 * @date 2019-08-01 22:01
 * @desc 布尔模型操作记录
 **/
public class BoolStepVo {

    private String operator;

    private String leftTerm;

    private String rightTerm;

    private Set<Integer> leftSet;

    private Set<Integer> rightSet;

    private Set<Integer> resultSet;

    public BoolStepVo(String operator, String leftTerm, String rightTerm, Set<Integer> leftSet, Set<Integer> rightSet, Set<Integer> resultSet) {
        this.operator = operator;
        this.leftTerm = leftTerm;
        this.rightTerm = rightTerm;
        this.leftSet = leftSet;
        this.rightSet = rightSet;
        this.resultSet = resultSet;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getLeftTerm() {
        return leftTerm;
    }

    public void setLeftTerm(String leftTerm) {
        this.leftTerm = leftTerm;
    }

    public String getRightTerm() {
        return rightTerm;
    }

    public void setRightTerm(String rightTerm) {
        this.rightTerm = rightTerm;
    }

    public Set<Integer> getLeftSet() {
        return leftSet;
    }

    public void setLeftSet(Set<Integer> leftSet) {
        this.leftSet = leftSet;
    }

    public Set<Integer> getRightSet() {
        return rightSet;
    }

    public void setRightSet(Set<Integer> rightSet) {
        this.rightSet = rightSet;
    }

    public Set<Integer> getResultSet() {
        return resultSet;
    }

    public void setResultSet(Set<Integer> resultSet) {
        this.resultSet = resultSet;
    }
}
