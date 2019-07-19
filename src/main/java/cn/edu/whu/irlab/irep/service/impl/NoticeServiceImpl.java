package cn.edu.whu.irlab.irep.service.impl;

import cn.edu.whu.irlab.irep.entity.Notice;
import cn.edu.whu.irlab.irep.mapper.NoticeMapper;
import cn.edu.whu.irlab.irep.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author fangrf
 * @version 1.0
 * @date 2019-07-18 10:28
 * @desc 公告处理业务实现类
 **/
@Service
@Transactional
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private NoticeMapper noticeMapper;

    @Override
    public List<Notice> selectAllNoticeService() {
        return noticeMapper.selectAll();
    }

    @Override
    public int insertNoticeService(Notice notice) {
        return noticeMapper.insertSelective(notice);
    }

    @Override
    public int updateNoticeService(Notice notice) {
        return noticeMapper.updateByPrimaryKeySelective(notice);
    }

    @Override
    public int deleteNoticeService(int id) {
        return noticeMapper.deleteByPrimaryKey(id);
    }
}
