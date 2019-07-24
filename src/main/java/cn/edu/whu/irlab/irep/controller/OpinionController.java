package cn.edu.whu.irlab.irep.controller;

import cn.edu.whu.irlab.irep.base.entity.*;
import cn.edu.whu.irlab.irep.base.dao.OpinionService;
import cn.edu.whu.irlab.irep.service.enums.ResponseEnum;
import cn.edu.whu.irlab.irep.service.util.ResponseVoUtil;
import cn.edu.whu.irlab.irep.service.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author fangrf
 * @version 1.0
 * @date 2019-07-18 10:34
 * @desc 留言处理业务类
 **/
@RestController
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
    public ResponseVo addOpinionController(@RequestBody Opinion opinion){
        opinion.setSign(1);
        int i= opinionService.insertOpinionService(opinion);
        if(i == 1){
            return ResponseVoUtil.success();
        }
        return ResponseVoUtil.error(ResponseEnum.UNKNOW_ERROR);
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
    public ResponseVo updateOpinionController(@RequestBody Opinion opinion, HttpServletRequest httpServletRequest){
        User user = (User)httpServletRequest.getSession().getAttribute("user");
        opinion.setCallBy(user.getId());
        opinion.setSign(2);
        int i = opinionService.updateOpinoinService(opinion);
        if(i == 1){
            return ResponseVoUtil.success();
        }
        return ResponseVoUtil.error(ResponseEnum.UNKNOW_ERROR);
    }

    /**
     * 查询所有的留言信息
     * @return
     */
    @RequestMapping(value = "/query")
    public ResponseVo<List<Opinion>> selectOpinionController(){
        return ResponseVoUtil.success(opinionService.selectAllOpinionService());
    }
}
