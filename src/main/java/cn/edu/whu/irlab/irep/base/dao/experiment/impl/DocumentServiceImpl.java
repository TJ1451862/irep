package cn.edu.whu.irlab.irep.base.dao.experiment.impl;

import cn.edu.whu.irlab.irep.base.dao.experiment.DocumentService;
import cn.edu.whu.irlab.irep.base.entity.experiment.Document;
import cn.edu.whu.irlab.irep.base.mapper.experiment.DocumentMapper;
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
