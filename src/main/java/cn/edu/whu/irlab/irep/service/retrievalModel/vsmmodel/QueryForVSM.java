package cn.edu.whu.irlab.irep.service.retrievalModel.vsmmodel;

import cn.edu.whu.irlab.irep.entity.Query;
import cn.edu.whu.irlab.irep.service.util.Calculator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryForVSM extends Query {

    private List<VectorI> vector=new ArrayList<>();

    private Map<String,Double> tfMap=new HashMap<>();

    public QueryForVSM(String content, String analyzerName, boolean isRemoveStopWord) {
        super(content, analyzerName, isRemoveStopWord);
    }

    public QueryForVSM(String content, String analyzerName, boolean isRemoveStopWord, int formulaID, double smoothParam) {
        super(content, analyzerName, isRemoveStopWord);
        setTfMap(formulaID,smoothParam);
    }


    public Map<String, Double> getTfMap() {
        return tfMap;
    }

    public void setTfMap(int formulaID, double smoothParam) {
        ArrayList<String> termList=this.getPreProcessResult();
        for (int i = 0; i <termList.size() ; i++) {
            String term =termList.get(i);
            if (tfMap.containsKey(term)){
                double tf=tfMap.get(term);
                tfMap.put(term,tf+1);
            }else {
                tfMap.put(term,1.0);
            }
        }
        tfMap= Calculator.calculateTF(tfMap,formulaID,smoothParam);
    }

    public List<VectorI> getVector() {
        return vector;
    }

    public void setVector(List<VectorI> vector) {
        this.vector = vector;
    }

}
