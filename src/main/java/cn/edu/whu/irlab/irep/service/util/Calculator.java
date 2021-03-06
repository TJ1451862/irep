package cn.edu.whu.irlab.irep.service.util;

import cn.edu.whu.irlab.irep.service.vo.LMVo;
import cn.edu.whu.irlab.irep.service.vo.VectorIVo;
import cn.edu.whu.irlab.irep.service.vo.TfVo;

import java.util.*;

public class Calculator {

    public static List<TfVo> calculateTF1(List<TfVo> tf, int formulaID, double smoothParam) {
        List<TfVo> result = new ArrayList<>();

        double tfValue;

        double max = calculateBasicParam1(tf, "max");
        double avg = calculateBasicParam1(tf, "avg");


        for (TfVo i :
                tf) {
            TfVo tfVo = new TfVo(i.getDocId(),i.getTerm(),i.getTf());
            tfValue=tfVo.getTf();
            switch (formulaID) {
                case 1:
                    break;
                case 2:
                    tfValue = 1 + Math.log(tfValue);
                    break;
                case 3:
                    tfValue = smoothParam + (1 - smoothParam) * tfValue / max;
                    break;
                case 4:
                    tfValue = tfValue > 0 ? 1 : 0;
                    break;
                case 5:
                    tfValue = (1 + Math.log(tfValue)) / (1 + Math.log(avg));
                    break;
                default:
                    break;
            }
            tfVo.setTf(tfValue);
            result.add(tfVo);
        }
        return result;
    }

    private static double calculateBasicParam1(List<TfVo> tf, String sign) {
        double param = 0;
        double temptf = 0;
        double sum = 0;
        double max = 0;
        double min = 0;
        double avg = 0;

        for (TfVo i :
                tf) {
            temptf = i.getTf();
            if (max < temptf) max = temptf;
            if (min > temptf) min = temptf;
            sum += temptf;
        }
        switch (sign) {
            case "max":
                param = max;
                break;
            case "min":
                param = min;
                break;
            case "avg":
                avg = sum / tf.size();
                param = avg;
                break;
            default:
                break;
        }
        return param;
    }

    /**
     * @param tfMap     原始tf数据
     * @param formulaID 公式ID
     *                  1:tf=tf
     *                  2:tf=1+log(tf)
     *                  3:tf=a+(1-a)tf/max(tfs) a为平滑系数，max（tfs）为所有tf中的最大值
     *                  4:tf = tf > 0 ? 1 : 0;
     *                  5:(1+log(tf))/(1+log(avg(tfs))) avg(tfs)为所有tf的平均值
     */
    public static Map<String, Double> calculateTF(Map<String, Double> tfMap, int formulaID, double smoothParam) {
        Map<String, Double> result = new HashMap<>();
        double tf = 0;
        double max = calculateBasicParam(tfMap, "max");
        double avg = calculateBasicParam(tfMap, "avg");
        for (String s :
                tfMap.keySet()) {
            tf = tfMap.get(s);
            switch (formulaID) {
                case 1:
                    break;
                case 2:
                    tf = 1 + Math.log(tf);
                    break;
                case 3:
                    tf = smoothParam + (1 - smoothParam) * tf / max;
                    break;
                case 4:
                    tf = tf > 0 ? 1 : 0;
                    break;
                case 5:
                    tf = (1 + Math.log(tf)) / (1 + Math.log(avg));
                    break;
                default:
                    break;
            }
            result.put(s, tf);
        }

        return result;
    }


    private static double calculateBasicParam(Map<String, Double> tfMap, String sign) {
        double param = 0;
        double temptf = 0;
        double sum = 0;
        double max = 0;
        double min = 0;
        double avg = 0;

        for (String s :
                tfMap.keySet()) {
            temptf = tfMap.get(s);
            if (max < temptf) max = temptf;
            if (min > temptf) min = temptf;
            sum += temptf;
        }
        switch (sign) {
            case "max":
                param = max;
                break;
            case "min":
                param = min;
                break;
            case "avg":
                avg = sum / tfMap.size();
                param = avg;
                break;
            default:
                break;
        }
        return param;
    }

    /**
     * 计算向量的模长
     *
     * @param vector 向量
     * @return
     */
    public static double calculateModule(List<VectorIVo> vector) {
        double module = 0;
        for (int i = 0; i < vector.size(); i++) {
            module += Math.pow(vector.get(i).getValue(), 2);
        }
        module = Math.pow(module, 0.5);
        return module;
    }

    /**
     * 计算文档LM
     *
     * @param tfMap 倒排索引中得到的词项在文档中出现的次数
     * @return
     */
    public static Map<String, Double> calculateLM(Map<String, Double> tfMap) {
        Map<String, Double> resultForLM = new HashMap<>();
        double tfForLM;
        double numberOfTerms = 0;
        for (String s :
                tfMap.keySet()) {
            numberOfTerms = tfMap.get(s) + numberOfTerms;
        }
        for (String s :
                tfMap.keySet()) {
            try {
                tfForLM = tfMap.get(s) / numberOfTerms;
            } catch (ArithmeticException exc) {
                tfForLM = 0.0;
            }
//            tfForLM= tfMap.get(s)/numberOfTerms;
            resultForLM.put(s, tfForLM);
        }
        return resultForLM;
    }

}
