package cn.edu.whu.irlab.irep.service.experiment.retrieval.languagemodel;
import cn.edu.whu.irlab.irep.service.entity.Query;

public class QueryForLM extends Query {
    /**
     * 对检索式进行预处理
     * @param content 检索式
     * @param analyzerName 分词器
     * @param isRemoveStopWord 是否去除停用词
     */
    public QueryForLM(String content, String analyzerName, boolean isRemoveStopWord) {
        super(content, analyzerName, isRemoveStopWord);
    }



}
