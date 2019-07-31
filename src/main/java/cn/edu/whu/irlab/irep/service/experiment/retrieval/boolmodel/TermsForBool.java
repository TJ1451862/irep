package cn.edu.whu.irlab.irep.service.experiment.retrieval.boolmodel;

import java.util.List;
import java.util.Map;

public class TermsForBool {
    private String term;
    private List<Integer> term_id;

    public TermsForBool(String term,List<Integer> term_id){
        this.term=term;
        this.term_id=term_id;
    }

    public String getTerm(){
        return term;
    }

    public List<Integer> getTerm_id(){
        return term_id;
    }
}
