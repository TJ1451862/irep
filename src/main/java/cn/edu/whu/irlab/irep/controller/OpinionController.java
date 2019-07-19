package cn.edu.whu.irlab.irep.controller;

import cn.edu.whu.irlab.irep.entity.Opinion;
import cn.edu.whu.irlab.irep.entity.User;
import cn.edu.whu.irlab.irep.service.OpinionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fangrf
 * @version 1.0
 * @date 2019-07-18 10:34
 * @desc 留言处理业务类
 **/
@Controller
@RequestMapping(value = "/opinion")
public class OpinionController {

    @Autowired
    private OpinionService opinionService;

    /**
     * 添加留言信息,需要输入的字段必须要oPhone,oEmail,oCotent
     * 电话，邮箱，留言内容
     * @param opinion
     * @return
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Map<String,Object> addOpinionController(Opinion opinion){
        Map<String,Object> map = new HashMap<>();
        opinion.setSign(1);
        int i= opinionService.insertOpinionService(opinion);
        if(i == 1){
            map.put("code",0);
            map.put("message","添加留言成功");
        }else{
            map.put("code",1);
            map.put("message","添加留言出错，请联系管理员");
        }
        return map;
    }

    /**
     * 管理员处理留言业务
     * 业务逻辑是，管理员查看留言，并且对留言进行回复后，点击此处之后
     * 留言的状态由1修改为2，同时记录下管理员的ID，当然目前没有回复留言的功能，此处仅能修改留言的状态
     * 参数对象必须传入id,也就留言的id序号
     * @param opinion
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Map<String,Object> updateOpinionController(Opinion opinion, HttpServletRequest httpServletRequest){
        Map<String,Object> map = new HashMap<>();
        User user = (User)httpServletRequest.getSession().getAttribute("user");
        opinion.setCallBy(user.getId());
        opinion.setSign(2);
        int i = opinionService.updateOpinoinService(opinion);
        if(i == 1){
            map.put("code",0);
            map.put("message","处理留言成功");
        }else{
            map.put("code",1);
            map.put("message","处理留言出错，请联系管理员");
        }
        return map;
    }

    /**
     * 查询所有的留言信息
     * @return
     */
    @RequestMapping(value = "/query")
    @ResponseBody
    public Map<String,Object> selectOpinionController(){
        Map<String,Object> map = new HashMap<>();
        List<Opinion> list = opinionService.selectAllOpinionService();
        map.put("total",list.size());
        map.put("row",list);
        return map;
    }
}
