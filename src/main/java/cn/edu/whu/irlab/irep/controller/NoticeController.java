package cn.edu.whu.irlab.irep.controller;

import cn.edu.whu.irlab.irep.entity.Notice;
import cn.edu.whu.irlab.irep.entity.User;
import cn.edu.whu.irlab.irep.service.NoticeService;
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
 * @date 2019-07-18 10:33
 * @desc 公告管理
 **/
@Controller
@RequestMapping(value = "/notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    /**
     * 查询所有的公告
     * @return
     */
    @RequestMapping(value = "/query")
    @ResponseBody
    public Map<String,Object> selectNoticeController(){
        Map<String,Object> map = new HashMap<>();
        List<Notice> list = noticeService.selectAllNoticeService();
        map.put("total",list.size());
        map.put("row",list);
        return map;
    }

    /**
     * 添加公告信息,前端需要传入的参数title，content
     * @param notice
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Map<String,Object> addNoticeController(Notice notice, HttpServletRequest httpServletRequest){
        Map<String,Object> map = new HashMap<>();
        User user = (User)httpServletRequest.getSession().getAttribute("user");
        notice.setAuthor(user.getUsername());
        int i = noticeService.insertNoticeService(notice);
        if(i == 1){
            map.put("code",0);
            map.put("message","添加公告成功");
        }else{
            map.put("code",1);
            map.put("message","添加公告出错，请联系管理员");
        }
        return map;
    }

    /**
     * 修改公告信息，前端需要传入的参数title，content
     * 还有公告的唯一标识ID
     * @param notice
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Map<String,Object> updateNoticeController(Notice notice, HttpServletRequest httpServletRequest){
        Map<String,Object> map = new HashMap<>();
        User user = (User)httpServletRequest.getSession().getAttribute("user");
        notice.setAuthor(user.getUsername());
        int i = noticeService.updateNoticeService(notice);
        if(i == 1){
            map.put("code",0);
            map.put("message","修改公告成功");
        }else{
            map.put("code",1);
            map.put("message","修改公告出错，请联系管理员");
        }
        return map;
    }

    /**
     * 删除公告,前端需要传入公告的唯一标识ID
     * @param notice
     * @return
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Map<String,Object> deleteNoticeController(Notice notice){
        Map<String,Object> map = new HashMap<>();
        int i = noticeService.deleteNoticeService(notice.getId());
        if(i == 1){
            map.put("code",0);
            map.put("message","删除公告成功");
        }else{
            map.put("code",1);
            map.put("message","删除公告出错，请联系管理员");
        }
        return map;
    }
}
