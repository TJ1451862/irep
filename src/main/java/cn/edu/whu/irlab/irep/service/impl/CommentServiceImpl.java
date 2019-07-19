package cn.edu.whu.irlab.irep.service.impl;

import cn.edu.whu.irlab.irep.entity.Comment;
import cn.edu.whu.irlab.irep.mapper.CommentMapper;
import cn.edu.whu.irlab.irep.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author fangrf
 * @version 1.0
 * @date 2019-07-18 16:55
 * @desc 评论的业务处理实现类
 **/
@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public int insertCommentService(Comment comment) {
        return commentMapper.insertSelective(comment);
    }

    @Override
    public int deleteCommentService(int id) {
        return commentMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int updateCommentService(Comment comment) {
        return commentMapper.updateByPrimaryKeySelective(comment);
    }

    @Override
    public List<Comment> selectAllCommentService() {
        return commentMapper.selectAll();
    }

    @Override
    public Comment selectCommentByIdService(int id) {
        return commentMapper.selectByPrimaryKey(id);
    }
}
