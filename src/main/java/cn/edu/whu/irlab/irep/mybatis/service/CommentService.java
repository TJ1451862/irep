package cn.edu.whu.irlab.irep.mybatis.service;


import cn.edu.whu.irlab.irep.mybatis.entity.Comment;

import java.util.List;

/**
 * @author fangrf
 * @date 2019-07-18 16:55
 * @desc 评论的业务处理接口
 **/
public interface CommentService {

    //用户添加一条评论
    int insertCommentService(Comment comment);

    //管理员删除一条评论并且删除相关的回复
    int deleteCommentService(int id);

    //用户给一条评论点赞,即修改评论的support字段
    int updateCommentService(Comment comment);

    //查询所有评论信息
    List<Comment> selectAllCommentService();

    //查询一条评论信息
    Comment selectCommentByIdService(int id);
}
