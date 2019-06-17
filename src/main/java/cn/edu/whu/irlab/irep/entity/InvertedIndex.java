package cn.edu.whu.irlab.irep.entity;

public class InvertedIndex {
    private String term;

    private Integer docId;

    private Integer tf;

    private String locations;

    private String indexType;

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term == null ? null : term.trim();
    }

    public Integer getDocId() {
        return docId;
    }

    public void setDocId(Integer docId) {
        this.docId = docId;
    }

    public Integer getTf() {
        return tf;
    }

    public void setTf(Integer tf) {
        this.tf = tf;
    }

    public String getLocations() {
        return locations;
    }

    public void setLocations(String locations) {
        this.locations = locations == null ? null : locations.trim();
    }

    public String getIndexType() {
        return indexType;
    }

    public void setIndexType(String indexType) {
        this.indexType = indexType == null ? null : indexType.trim();
    }

    @Override
    public String toString() {
        return "InvertedIndex{" +
                "term='" + term + '\'' +
                ", docId=" + docId +
                ", tf=" + tf +
                ", locations='" + locations + '\'' +
                ", indexType='" + indexType + '\'' +
                '}';
    }
}