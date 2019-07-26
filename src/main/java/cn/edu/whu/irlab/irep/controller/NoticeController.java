package cn.edu.whu.irlab.irep.controller;

import cn.edu.whu.irlab.irep.base.entity.*;
import cn.edu.whu.irlab.irep.base.dao.NoticeService;
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
 * @date 2019-07-18 10:33
 * @desc 公告管理
 **/
@RestController
@RequestMapping(value = "/notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    /**
     * 查询所有的公告
     * @return
     */
    @RequestMapping(value = "/query")
    public ResponseVo<List<Notice>> selectNoticeController(){
        return ResponseVoUtil.success(noticeService.selectAllNoticeService());
    }

    /**
     * 添加公告信息,前端需要传入的参数title，content
     * @param notice
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/add")
    public ResponseVo addNoticeController(@RequestBody Notice notice, HttpServletRequest httpServletRequest){
        User user = (User)httpServletRequest.getSession().getAttribute("user");
        notice.setAuthor(user.getUsername());
        int i = noticeService.insertNoticeService(notice);
        if(i == 1){
            return ResponseVoUtil.success();
        }
        return ResponseVoUtil.error(ResponseEnum.UNKNOW_ERROR);
    }

    /**
     * 修改公告信息，前端需要传入的参数title，content
     * 还有公告的唯一标识ID
     * @param notice
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/update")
    public ResponseVo updateNoticeController(@RequestBody Notice notice, HttpServletRequest httpServletRequest){
        User user = (User)httpServletRequest.getSession().getAttribute("user");
        notice.setAuthor(user.getUsername());
        int i = noticeService.updateNoticeService(notice);
        if(i == 1){
            return ResponseVoUtil.success();
        }
        return ResponseVoUtil.error(ResponseEnum.UNKNOW_ERROR);
    }

    /**
     * 删除公告,前端需要传入公告的唯一标识ID
     * @param notice
     * @return
     */
    @RequestMapping(value = "/delete")
    public ResponseVo deleteNoticeController(@RequestBody Notice notice){
        int i = noticeService.deleteNoticeService(notice.getId());
        if(i == 1){
            return ResponseVoUtil.success();
        }
        return ResponseVoUtil.error(ResponseEnum.UNKNOW_ERROR);
    }
}
