package cn.edu.whu.irlab.irep.controller;

import cn.edu.whu.irlab.irep.base.entity.*;
import cn.edu.whu.irlab.irep.base.dao.AnswerService;
import cn.edu.whu.irlab.irep.base.dao.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fangrf
 * @version 1.0
 * @date 2019-07-18 16:56
 * @desc 评论业务交互接口
 **/
@RestController
@RequestMapping(value = "/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private AnswerService answerService;

    /**
     * 发布评论，用户传入的参数cContent
     * 业务逻辑，当前登录的用户发布评论内容，后台接收评论内容
     * 然后后台通过session获取当前登录用户的用户名
     * 将用户名存入Comment对象中，还有评论内容也存入其中
     * @param comment
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/add")
    public Map<String,Object> addCommentController(@RequestBody Comment comment, HttpServletRequest httpServletRequest){
        Map<String,Object> map = new HashMap<>();
        User user = (User)httpServletRequest.getSession().getAttribute("user");
        comment.setcUsername(user.getUsername());
        try {
            commentService.insertCommentService(comment);
        }catch (Exception e){
            e.printStackTrace();
            map.put("code",1);
            map.put("message","发表评论出错，请联系管理员");
            return map;
        }
        map.put("code",0);
        map.put("message","发表评论成功");
        return map;
    }

    /**
     * 点赞功能，前端传入被点赞的评论的唯一标识ID，后台根据ID修改support字段
     * @param id
     * @return
     */
    @RequestMapping(value = "/support")
    public Map<String,Object> updateCommentController(@RequestParam(value = "id") Integer id) {
        Map<String, Object> map = new HashMap<>();
        int support = 0;
        try{
            support = commentService.selectCommentByIdService(id).getcSupport();
        }catch (java.lang.NullPointerException e){
            e.printStackTrace();
            support = 0;
        }
        Comment comment = new Comment();
        comment.setId(id);
        comment.setcSupport(support + 1);
        try {
            commentService.updateCommentService(comment);
        }catch (Exception e){
            e.printStackTrace();
            map.put("code",1);
            map.put("message","点赞失败，请联系管理员");
            return map;
        }
        map.put("code",0);
        map.put("message","点赞成功");
        return map;
    }

    /**
     * 删除评论，并且删除相关的回复，参数是评论的唯一标识ID
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete")
    public Map<String,Object> deleteCommentController(@RequestParam(value = "id") Integer id){
        Map<String, Object> map = new HashMap<>();
        try {
            //先删除回复
            answerService.deleteAnswerByCIdService(id);
            //在删除评论
            commentService.deleteCommentService(id);
        }catch (Exception e){
            e.printStackTrace();
            map.put("code",1);
            map.put("message","删除评论失败，请联系管理员");
            return map;
        }
        map.put("code",0);
        map.put("message","删除评论成功");
        return map;
    }

    /**
     * 其他用户回复评论的功能
     * 传入的参数评论的唯一标识ID 回复的内容aContent
     * 后台将回复的用户存入answer 评论的id存入answer,回复的内容存入answer
     * @param id
     * @param aContent
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/answer")
    public Map<String,Object> answerCommentController(@RequestParam(value = "id") Integer id,@RequestParam(value = "aContent") String aContent,HttpServletRequest httpServletRequest){
        Map<String,Object> map = new HashMap<>();
        User user = (User)httpServletRequest.getSession().getAttribute("user");
        Answer answer = new Answer();
        answer.setaContent(aContent);
        answer.setaUsername(user.getUsername());
        answer.setcId(id);
        try {
            answerService.insertAnswerService(answer);
        }catch (Exception e){
            e.printStackTrace();
            map.put("code",1);
            map.put("message","回复评论失败，请联系管理员");
            return map;
        }
        map.put("code",0);
        map.put("message","回复评论成功");
        return map;
    }

    /**
     * 后台管理查询所有的评论内容，但不包括回复
     * @return
     */
    @RequestMapping(value = "/query")
    public Map<String,Object> queryCommentController(){
        Map<String,Object> map = new HashMap<>();
        List<Comment> list = commentService.selectAllCommentService();
        map.put("total",list.size());
        map.put("row",list);
        return map;
    }

    /**
     * 查询所有的评论以及回复的内容
     * @return
     */
    @RequestMapping(value = "/queryAll")
    public Map<String,Object> queryAllCommentController(){
        Map<String,Object> map = new HashMap<>();
        List<CommentVo> voList = new ArrayList<>();
        List<Comment> list = commentService.selectAllCommentService();
        for (Comment comment:list) {
            CommentVo commentVo = new CommentVo();
            commentVo.setId(comment.getId());
            commentVo.setcUsername(comment.getcUsername());
            commentVo.setcContent(comment.getcContent());
            commentVo.setcSupport(comment.getcSupport());
            commentVo.setcCreateTime(comment.getcCreateTime());
            commentVo.setC1(comment.getC1());
            commentVo.setC2(comment.getC2());
            commentVo.setC3(comment.getC3());
            commentVo.setAnswerList(answerService.selectAnswerByCIdService(comment.getId()));
            voList.add(commentVo);
        }
        map.put("total",voList.size());
        map.put("row",voList);
        return map;
    }
}
