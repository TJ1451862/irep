package cn.edu.whu.irlab.irep.service.experiment.retrieval.boolmodel;

import cn.edu.whu.irlab.irep.service.vo.Query;



public class QueryForBool extends Query {
    /**
     * 对检索式进行预处理
     * @param content 检索式
     * @param analyzerName 分词器
     * @param isRemoveStopWord 是否去除停用词
     */


    public QueryForBool(String content, String analyzerName, boolean isRemoveStopWord) {
        super(content, analyzerName, isRemoveStopWord);
    }



}
