package cn.edu.whu.irlab.irep.service.retrievalModel.vsmmodel;

import cn.edu.whu.irlab.irep.entity.Doc;
import cn.edu.whu.irlab.irep.service.util.TFCalculator;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public class DocForVSM extends Doc {

    @Autowired
    public TFCalculator tfCalculator;

    private List<VectorI> vector;

    private Map<String,Double> tfMap;

    public DocForVSM(String filePath, String fileName) {
        super(filePath, fileName);
    }

    public DocForVSM(int id) {
        super(id);
    }

    public Map<String, Double> getTfMap() {
        return tfMap;
    }

    public void setTfMap(Map<String, Double> tfMap,int formulaID, double smoothParam) {
        this.tfMap = tfCalculator.calculateTF(tfMap,formulaID,smoothParam);
    }

    public List<VectorI> getVector() {
        return vector;
    }

    public void setVector(List<VectorI> vector) {
        this.vector = vector;
    }


}
