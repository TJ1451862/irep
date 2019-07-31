package cn.edu.whu.irlab.irep.service.experiment.perfomance.Impl;

import cn.edu.whu.irlab.irep.base.entity.experiment.Result;

import java.util.List;

public class Evaluator {

    /**
     * 计算查准率
     *
     * @param standardResults 标准检索结果
     * @param results         待评价模型的检索结果
     * @param length          取前length个结果
     * @return 查准率
     */
    public double calculatePrecision(List<Result> standardResults, List<Result> results, Integer length) {
        double precision;//查准率
        if (length != null) {
            results = results.subList(0, length - 1);
        }

        int relevant = relatedResultNumOfReturn(standardResults, results);//返回结果中的相关文档数
        int retrieved = results.size();//返回结果数

        precision = (double) relevant / retrieved;
        return precision;
    }

    /**
     * 计算召回率
     *
     * @param standardResults 标准检索结果
     * @param results         待评价模型的检索结果
     * @param length          取前length个结果
     * @return 召回率
     */
    public double calculateRecall(List<Result> standardResults, List<Result> results, Integer length) {
        double recall;
        if (length != null) {
            results = results.subList(0, length);
        }

        int relevant = standardResults.size();//所有相关文档数
        int retrieved = relatedResultNumOfReturn(standardResults, results);//返回结果中的相关文档数

        recall = (double) retrieved / relevant;
        return recall;
    }

    public int relatedResultNumOfReturn(List<Result> standardResults, List<Result> results) {
        int relatedResultNum = 0;
        for (int i = 0; i < standardResults.size(); i++) {
            for (int j = 0; j < results.size(); j++) {
                if (standardResults.get(i).getDocId().equals(results.get(j).getDocId())) {
                    relatedResultNum++;
                }
            }
        }
        return relatedResultNum;
    }

    public double calculateF1(List<Result> standardResults, List<Result> results) {
        double f1;
        double precision = calculatePrecision(standardResults, results, null);
        double recall = calculateRecall(standardResults, results, null);

        f1 = 2 * precision * recall / (precision + recall);

        return f1;
    }

    /**
     * 计算正确率均值
     *
     * @param standardResults 标准检索结果
     * @param results         待评价模型检索结果
     * @return 正确率均值
     */
    public double calculateAP(List<Result> standardResults, List<Result> results) {
        int positiveNum = standardResults.size();//正例数
        double ap = 0;
        for (int i = 0; i < positiveNum; i++) {
            double lowerLimitOfRecall = (double) i / positiveNum;
            double precisionMax = 0;
            for (int j = 0; j < results.size(); j++) {
                if (calculateRecall(standardResults, results, j) > lowerLimitOfRecall) {
                    double prcision = calculatePrecision(standardResults, results, j);
                    if (prcision > precisionMax) {
                        precisionMax = prcision;
                    }
                }
            }
            ap += precisionMax;
        }
        ap = ap / positiveNum;
        return ap;
    }

    public double calculateNDCG(List<Result> standardResults, List<Result> results, Integer length) {
        double dcg = 0;
        double idcg=0;
        double ndcg=0;

        if (length != null) {
            results = results.subList(0, length - 1);
        }

        //计算dcg
        for (int i = 0; i < results.size(); i++) {
            int score=0;
            for (int j = 0; j < standardResults.size(); j++) {
                Result standard = standardResults.get(j);
                Result result = results.get(i);
                if (standard.getDocId() == result.getDocId() && standard.getQueryId() == result.getQueryId()) {
                    score = standard.getScore();
                }
            }
            dcg += score / log2(i + 2);
        }

        //计算idcg
        for (int i = 0; i <results.size() ; i++) {
            if (i>=standardResults.size()) {
                break;
            }
            idcg+=standardResults.get(i).getScore()/log2(i+2);
        }

        //计算ndcg
        ndcg=dcg/idcg;
        return ndcg;
    }

    private double log2(double n) {
        return Math.log(n) / Math.log(2);
    }


    public double avg(){
        double avg=0;
        return avg;
    }

}
