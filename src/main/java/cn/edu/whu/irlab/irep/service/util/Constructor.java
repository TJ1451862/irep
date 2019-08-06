package cn.edu.whu.irlab.irep.service.util;

import cn.edu.whu.irlab.irep.base.entity.experiment.Retriever;

import javax.servlet.http.HttpServletRequest;

/**
 * @author gcr
 * @version 1.0
 * @date 2019-06-15 14:18
 * @desc IndexType构造器
 **/

public class Constructor {

    public static String indexTypeConstructor(String analyzer, boolean removeStopWord, boolean chinese) {
        String indexType = "";

        //语言代号
        if (chinese) {
            indexType += '1';
        } else {
            indexType += '0';
        }

        //分词器代号
        switch (analyzer) {
            case "standard":
                indexType += "01";
                break;
            case "whitespace":
                indexType += "02";
                break;
            case "simple":
                indexType += "03";
                break;
            case "CJK":
                indexType += "04";
                break;
            case "smartChinese":
                indexType += "05";
                break;
            default:
                indexType += "00";
        }

        //去停用词代号
        if (removeStopWord) {
            indexType += '1';
        } else {
            indexType += '0';
        }

        return indexType;
    }

    /**
     * 解析分词器名称
     * @param indexType
     * @return
     */
    public static String analyzerNameConstructor(String indexType) {
        String analyzerType = indexType.substring(1, 3);
        switch (analyzerType) {
            case "01":
                return "standard";
            case "02":
                return "whitespace";
            case "03":
                return "simple";
            case "04":
                return "CJK";
            case "05":
                return "smartChinese";
            default:
                return "";
        }
    }

    public static boolean removeStopWord(String indexType){
        String temp=indexType.substring(4);
        if (temp.equals(0)){
            return false;
        }else {
            return true;
        }
    }


    public static String indexTypeConstructor(HttpServletRequest request) {

        boolean chinese = true;
        String analyzer = (String) request.getSession().getAttribute("analyzer");
        boolean removeStopWord = (boolean) request.getSession().getAttribute("removeStopWord");

        return indexTypeConstructor(analyzer, removeStopWord, chinese);
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
