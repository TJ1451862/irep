package cn.edu.whu.irlab.irep.base.entity.system;

public class UserAnalyticalScoreWithBLOBs extends UserAnalyticalScore {
    private String analyticalContent;

    private String teacherComment;

    public String getAnalyticalContent() {
        return analyticalContent;
    }

    public void setAnalyticalContent(String analyticalContent) {
        this.analyticalContent = analyticalContent == null ? null : analyticalContent.trim();
    }

    public String getTeacherComment() {
        return teacherComment;
    }

    public void setTeacherComment(String teacherComment) {
        this.teacherComment = teacherComment == null ? null : teacherComment.trim();
    }
}