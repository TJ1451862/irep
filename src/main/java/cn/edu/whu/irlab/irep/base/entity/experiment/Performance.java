package cn.edu.whu.irlab.irep.base.entity.experiment;

import java.math.BigDecimal;

public class Performance {
    private Integer id;

    private String query;

    private String retrieverId;

    private BigDecimal precision;

    private BigDecimal p5;

    private BigDecimal p10;

    private BigDecimal p20;

    private BigDecimal recall;

    private BigDecimal r5;

    private BigDecimal r10;

    private BigDecimal r20;

    private BigDecimal ndcg;

    private BigDecimal ndcg5;

    private BigDecimal ndcg10;

    private BigDecimal ndcg20;

    private BigDecimal map;

    private BigDecimal f1;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query == null ? null : query.trim();
    }

    public String getRetrieverId() {
        return retrieverId;
    }

    public void setRetrieverId(String retrieverId) {
        this.retrieverId = retrieverId == null ? null : retrieverId.trim();
    }

    public BigDecimal getPrecision() {
        return precision;
    }

    public void setPrecision(BigDecimal precision) {
        this.precision = precision;
    }

    public BigDecimal getP5() {
        return p5;
    }

    public void setP5(BigDecimal p5) {
        this.p5 = p5;
    }

    public BigDecimal getP10() {
        return p10;
    }

    public void setP10(BigDecimal p10) {
        this.p10 = p10;
    }

    public BigDecimal getP20() {
        return p20;
    }

    public void setP20(BigDecimal p20) {
        this.p20 = p20;
    }

    public BigDecimal getRecall() {
        return recall;
    }

    public void setRecall(BigDecimal recall) {
        this.recall = recall;
    }

    public BigDecimal getR5() {
        return r5;
    }

    public void setR5(BigDecimal r5) {
        this.r5 = r5;
    }

    public BigDecimal getR10() {
        return r10;
    }

    public void setR10(BigDecimal r10) {
        this.r10 = r10;
    }

    public BigDecimal getR20() {
        return r20;
    }

    public void setR20(BigDecimal r20) {
        this.r20 = r20;
    }

    public BigDecimal getNdcg() {
        return ndcg;
    }

    public void setNdcg(BigDecimal ndcg) {
        this.ndcg = ndcg;
    }

    public BigDecimal getNdcg5() {
        return ndcg5;
    }

    public void setNdcg5(BigDecimal ndcg5) {
        this.ndcg5 = ndcg5;
    }

    public BigDecimal getNdcg10() {
        return ndcg10;
    }

    public void setNdcg10(BigDecimal ndcg10) {
        this.ndcg10 = ndcg10;
    }

    public BigDecimal getNdcg20() {
        return ndcg20;
    }

    public void setNdcg20(BigDecimal ndcg20) {
        this.ndcg20 = ndcg20;
    }

    public BigDecimal getMap() {
        return map;
    }

    public void setMap(BigDecimal map) {
        this.map = map;
    }

    public BigDecimal getF1() {
        return f1;
    }

    public void setF1(BigDecimal f1) {
        this.f1 = f1;
    }
}