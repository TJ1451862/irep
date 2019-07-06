package cn.edu.whu.irlab.irep.service.util;

import cn.edu.whu.irlab.irep.entity.Retriever;

/**
 * @author gcr
 * @version 1.0
 * @date 2019-06-15 14:18
 * @desc IndexType构造器
 **/

public class Constructor {

    public static String indexTypeConstructor(String analyzer, boolean isRemoveStopWord) {
        String indexType;
        String suffixPositive = "_remove";
        String suffixNegative = "_not_remove";
        if (isRemoveStopWord) indexType = analyzer + suffixPositive;
        else indexType = analyzer + suffixNegative;
        return indexType;
    }

    public static String retrieverIdConstructor(Retriever retriever) {

        String retrieverId = "";

        //语言代号
        if (retriever.getIsChinese()) {
            retrieverId += '1';
        } else {
            retrieverId += '0';
        }

        //分词器代号
        switch (retriever.getAnalyzer()) {
            case "standard":
                retrieverId += "01";
                break;
            case "whitespace":
                retrieverId += "02";
                break;
            case "simple":
                retrieverId += "03";
                break;
            case "CJK":
                retrieverId += "04";
                break;
            case "smartChinese":
                retrieverId += "05";
                break;
            default:
                retrieverId += "00";
        }

        //去停用词代号
        if (retriever.getIsRemoveStopWord()) {
            retrieverId += '1';
        } else {
            retrieverId += '0';
        }


        retrieverId += "_";

        //模型代号
        switch (retriever.getModel()) {
            case "boolModel":
                retrieverId += '1';
                retriever.setModel("布尔模型");
                break;
            case "vsm":
                retrieverId += '2';
                retriever.setModel("向量空间模型");
                break;
            case "probabilityModel":
                retrieverId += '3';
                retriever.setModel("概率检索模型");
                break;
            case "languageModel":
                retriever.setModel("语言模型");
                retrieverId += '4';
                break;
            default:
                retrieverId += '5';
                break;
        }

        //公式Id
        retrieverId += retriever.getFormulaId();

        //参数1
        if (retriever.getParam1() != null) {
            retrieverId += "_" + retriever.getParam1();
        }

        //参数2
        if (retriever.getParam2() != null) {
            retrieverId += "_" + retriever.getParam2();
        }
        return retrieverId;
    }

}
