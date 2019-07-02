package cn.edu.whu.irlab.irep.service.retrievalModel.vsmmodel;

import cn.edu.whu.irlab.irep.entity.Doc;
import cn.edu.whu.irlab.irep.service.util.Calculator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DocForVSM extends Doc {

    private List<VectorI> vector = new ArrayList<>();

    private Map<String, Double> tfMap = new HashMap<>();

    public DocForVSM(String filePath, String fileName) {
        super(filePath, fileName);
    }

    public DocForVSM(int id) {
        super(id);
    }

    public Map<String, Double> getTfMap() {
        return tfMap;
    }

    public void setTfMap(Map<String, Double> tfMap, int formulaID, double smoothParam) {
        this.tfMap = Calculator.calculateTF(tfMap, formulaID, smoothParam);
    }

    public List<VectorI> getVector() {
        return vector;
    }

    public void setVector(List<VectorI> vector) {
        this.vector = vector;
    }


}
