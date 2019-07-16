package cn.edu.whu.irlab.irep.service.util;

import cn.edu.whu.irlab.irep.service.retrievalModel.vsmModel.VectorI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

public class Calculator {


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
    public static double calculateModule(List<VectorI> vector) {
        double module = 0;
        for (int i = 0; i < vector.size(); i++) {
            module += Math.pow(vector.get(i).getValue(), 2);
        }
        module = Math.pow(module, 0.5);
        return module;
    }

}
