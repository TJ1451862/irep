package cn.edu.whu.irlab.irep.service.vo;

import cn.edu.whu.irlab.irep.service.experiment.preProcess.Impl.PreProcessorServiceImpl;
import org.apache.commons.collections.map.HashedMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author gcr
 */
public class Query {

    //query内容
    private String content;

    //预处理结果
    private ArrayList<String> preProcessResult;

    //tf值
    private List<TfVo> tf = new ArrayList<>();


    public Query(String content, String analyzerName, boolean isRemoveStopWord) {
        this.content = content;
        preProcessQuery(analyzerName, isRemoveStopWord);
        initTf();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ArrayList<String> getPreProcessResult() {
        return preProcessResult;
    }

    public void setPreProcessResult(ArrayList<String> preProcessResult) {
        this.preProcessResult = preProcessResult;
    }

    public List<TfVo> getTf() {
        return tf;
    }

    public void setTf(List<TfVo> tf) {
        this.tf = tf;
    }

    private void preProcessQuery(String analyzerName, boolean isRemoveStopWord) {
        this.preProcessResult = PreProcessorServiceImpl.preProcess(content, analyzerName, isRemoveStopWord);
    }

    private void initTf() {
        List<TfVo> tfVoList = new ArrayList<>();
        Map<String, Integer> tfMap = new HashedMap();
        for (String i :
                preProcessResult) {
            if (tfMap.containsKey(i)) {
                tfMap.put(i, tfMap.get(i) + 1);
            } else {
                tfMap.put(i, 1);
            }
        }
        for (Map.Entry<String, Integer> entry :
                tfMap.entrySet()) {
            tfVoList.add(new TfVo(null, entry.getKey(), entry.getValue()));
        }
        tf=tfVoList;
    }

}

