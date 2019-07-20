package cn.edu.whu.irlab.irep.service.vo;

/**
 * @author gcr19
 * @date 2019-07-18 23:48
 * @desc
 **/
public class SearchResultVo {
    private int docId;
    private String title;
    private String url;
    private String content;

    public SearchResultVo(int docId, String title, String url, String content) {
        this.docId = docId;
        this.title = title;
        this.url = url;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getDocId() {
        return docId;
    }

    public void setDocId(int docId) {
        this.docId = docId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "SearchResultVo{" +
                "docId=" + docId +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
