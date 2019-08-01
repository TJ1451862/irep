package cn.edu.whu.irlab.irep.service.vo;

import java.util.List;
import java.util.Set;

/**
 * @author gcr19
 * @date 2019-08-01 20:47
 * @desc 布尔向量pojo类
 **/
public class BoolVectorVo {

    private String term;

    private Set<Integer> docIds;

    public BoolVectorVo(String term, Set<Integer> docIds) {
        this.term = term;
        this.docIds = docIds;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public Set<Integer> getDocIds() {
        return docIds;
    }

    public void setDocIds(Set<Integer> docIds) {
        this.docIds = docIds;
    }
}
