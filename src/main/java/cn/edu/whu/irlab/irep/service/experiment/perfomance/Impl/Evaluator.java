package cn.edu.whu.irlab.irep.service.experiment.perfomance.Impl;

import cn.edu.whu.irlab.irep.base.entity.experiment.Result;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class Evaluator {

    /**
     * 计算查准率
     *
     * @param standardResults 标准检索结果
     * @param results         待评价模型的检索结果
     * @param length          取前length个结果
     * @return 查准率
     */
    public BigDecimal calculatePrecision(List<Result> standardResults, List<Result> results, Integer length) {
        BigDecimal precision;//查准率
        if (length != null) {
            results = results.subList(0, length - 1);
        }

        int relevant = relatedResultNumOfReturn(standardResults, results);//返回结果中的相关文档数
        int retrieved = results.size();//返回结果数

        BigDecimal relevantB = BigDecimal.valueOf(relevant);
        BigDecimal retrievedB = BigDecimal.valueOf(retrieved);
        if (retrievedB.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        precision = relevantB.divide(retrievedB, 5, RoundingMode.HALF_UP);

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
    public BigDecimal calculateRecall(List<Result> standardResults, List<Result> results, Integer length) {
        BigDecimal recall;
        if (length != null) {
            results = results.subList(0, length);
        }

        int relevant = standardResults.size();//所有相关文档数
        int retrieved = relatedResultNumOfReturn(standardResults, results);//返回结果中的相关文档数

        recall = BigDecimal.valueOf(retrieved).divide(BigDecimal.valueOf(relevant), 5, RoundingMode.HALF_UP);
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

    public BigDecimal calculateF1(List<Result> standardResults, List<Result> results) {
        BigDecimal f1;
        BigDecimal precision = calculatePrecision(standardResults, results, null);
        BigDecimal recall = calculateRecall(standardResults, results, null);

        if (precision.compareTo(BigDecimal.valueOf(0)) == 0 && recall.compareTo(BigDecimal.valueOf(0)) == 0) {
            f1 = BigDecimal.valueOf(0);
        } else {
            f1 = (precision.multiply(recall).multiply(BigDecimal.valueOf(2))).divide(precision.add(recall), 5, RoundingMode.HALF_UP);
        }
        return f1;
    }

    /**
     * 计算正确率均值
     *
     * @param standardResults 标准检索结果
     * @param results         待评价模型检索结果
     * @return 正确率均值
     */
    public BigDecimal calculateAP(List<Result> standardResults, List<Result> results) {
        int positiveNum = standardResults.size();//正例数
        BigDecimal ap = new BigDecimal(0);
        for (int i = 0; i < positiveNum; i++) {
            BigDecimal lowerLimitOfRecall = BigDecimal.valueOf(i).divide(BigDecimal.valueOf(positiveNum), 5, RoundingMode.HALF_UP);
            BigDecimal precisionMax = new BigDecimal(0);
            for (int j = 1; j < results.size(); j++) {
                if (calculateRecall(standardResults, results, j).compareTo(lowerLimitOfRecall) > 0) {
                    BigDecimal prcision = calculatePrecision(standardResults, results, j);
                    if (prcision.compareTo(precisionMax) > 0) {
                        precisionMax = prcision;
                    }
                }
            }
            ap = ap.add(precisionMax);
        }
        ap = ap.divide(BigDecimal.valueOf(positiveNum), 5, RoundingMode.HALF_UP);
        return ap;
    }

    public BigDecimal calculateNDCG(List<Result> standardResults, List<Result> results, Integer length) {
        BigDecimal dcg = new BigDecimal(0);
        BigDecimal idcg = new BigDecimal(0);
        BigDecimal ndcg = new BigDecimal(0);

        if (length != null) {
            results = results.subList(0, length - 1);
        }

        //计算dcg
        for (int i = 0; i < results.size(); i++) {
            int score = 0;
            for (int j = 0; j < standardResults.size(); j++) {
                Result standard = standardResults.get(j);
                Result result = results.get(i);
                if (standard.getDocId() == result.getDocId() && standard.getQueryId() == result.getQueryId()) {
                    score = standard.getScore();
                }
            }
            dcg = dcg.add(BigDecimal.valueOf(score / log2(i + 2)));
        }

        //计算idcg
        for (int i = 0; i < results.size(); i++) {
            if (i >= standardResults.size()) {
                break;
            }
            idcg = idcg.add(BigDecimal.valueOf(standardResults.get(i).getScore() / log2(i + 2)));
        }

        //计算ndcg
        ndcg = dcg.divide(idcg, 5, RoundingMode.HALF_UP);
        return ndcg;
    }

    private double log2(double n) {
        return Math.log(n) / Math.log(2);
    }


    public double avg() {
        double avg = 0;
        return avg;
    }

}
