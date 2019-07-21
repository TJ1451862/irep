package cn.edu.whu.irlab.irep.base.dao;



import cn.edu.whu.irlab.irep.base.entity.Notice;

import java.util.List;

/**
 * @author fangrf
 * @date 2019-07-18 10:26
 * @desc 公告的业务处理接口
 **/
public interface NoticeService {

    //查询所有的公告信息
    List<Notice> selectAllNoticeService();

    //添加一条公告信息
    int insertNoticeService(Notice notice);

    //修改公告信息
    int updateNoticeService(Notice notice);

    //删除公告信息
    int deleteNoticeService(int id);
}
