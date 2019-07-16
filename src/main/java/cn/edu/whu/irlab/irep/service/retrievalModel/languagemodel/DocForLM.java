package cn.edu.whu.irlab.irep.service.retrievalModel.languagemodel;

import cn.edu.whu.irlab.irep.service.entity.Doc;
import cn.edu.whu.irlab.irep.service.preProcess.PreProcessor;
import cn.edu.whu.irlab.irep.service.util.Calculator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DocForLM extends Doc {

    private Map<String, Double> LMap = new HashMap<>();

    public DocForLM(String filePath, String fileName) {
        super(filePath, fileName);
    }

    public DocForLM(int id) {
        super(id);
    }

    public Map<String,Double> setLMap(Map<String,Double>tfMap){
//        this.LMap=Calculator.calculateLM(tfMap);
        return this.LMap;
    }

    public Map<String, Double> getLMap() {
        return LMap;
    }

}