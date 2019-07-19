package cn.edu.whu.irlab.irep.mybatis.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author fangrf
 * @version 1.0
 * @date 2019-07-18 16:28
 * @desc 评论和回复的实体类
 **/
@Data
public class CommentVo {
    private Integer id;
    //评论者用户名
    private String cUsername;
    //评论内容
    private String cContent;
    //发布时间
    private Date cCreateTime;
    //点赞数
    private Integer cSupport;
    //备用字段
    private String c1;

    private String c2;

    private Integer c3;
    //回复
    private List<Answer> answerList;
}
