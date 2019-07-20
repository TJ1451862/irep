package cn.edu.whu.irlab.irep.base.dao.impl;

import cn.edu.whu.irlab.irep.base.dao.DocumentService;
import cn.edu.whu.irlab.irep.base.entity.Document;
import cn.edu.whu.irlab.irep.base.mapper.DocumentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author gcr19
 * @date 2019-07-19 07:48
 * @desc
 **/
@Service
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    private DocumentMapper documentMapper;

    @Override
    public int insert(Document record) {
        return documentMapper.insert(record);
    }

    @Override
    public int insertSelective(Document record) {
        return documentMapper.insertSelective(record);
    }

    @Override
    public Document selectByDocId(int id) {
        return documentMapper.selectByDocId(id);
    }

    @Override
    public Document selectByDocName(String docName) {
        return documentMapper.selectByDocName(docName);
    }
}
