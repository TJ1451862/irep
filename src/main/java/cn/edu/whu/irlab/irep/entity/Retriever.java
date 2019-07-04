package cn.edu.whu.irlab.irep.entity;

public class Retriever {
    private String retrieverId;

    private Boolean isChinese;

    private String analyzer;

    private Boolean isRemoveStopWord;

    private String model;

    private Integer formulaId;

    private String paramName1;

    private Integer param1;

    private String paramName2;

    private Integer param2;

    public String getRetrieverId() {
        return retrieverId;
    }

    public void setRetrieverId(String retrieverId) {
        this.retrieverId = retrieverId == null ? null : retrieverId.trim();
    }

    public Boolean getIsChinese() {
        return isChinese;
    }

    public void setIsChinese(Boolean isChinese) {
        this.isChinese = isChinese;
    }

    public String getAnalyzer() {
        return analyzer;
    }

    public void setAnalyzer(String analyzer) {
        this.analyzer = analyzer == null ? null : analyzer.trim();
    }

    public Boolean getIsRemoveStopWord() {
        return isRemoveStopWord;
    }

    public void setIsRemoveStopWord(Boolean isRemoveStopWord) {
        this.isRemoveStopWord = isRemoveStopWord;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model == null ? null : model.trim();
    }

    public Integer getFormulaId() {
        return formulaId;
    }

    public void setFormulaId(Integer formulaId) {
        this.formulaId = formulaId;
    }

    public String getParamName1() {
        return paramName1;
    }

    public void setParamName1(String paramName1) {
        this.paramName1 = paramName1 == null ? null : paramName1.trim();
    }

    public Integer getParam1() {
        return param1;
    }

    public void setParam1(Integer param1) {
        this.param1 = param1;
    }

    public String getParamName2() {
        return paramName2;
    }

    public void setParamName2(String paramName2) {
        this.paramName2 = paramName2 == null ? null : paramName2.trim();
    }

    public Integer getParam2() {
        return param2;
    }

    public void setParam2(Integer param2) {
        this.param2 = param2;
    }
}