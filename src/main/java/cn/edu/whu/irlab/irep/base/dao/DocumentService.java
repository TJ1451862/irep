package cn.edu.whu.irlab.irep.base.dao;

import cn.edu.whu.irlab.irep.base.entity.Document;

/**
 * @author gcr19
 * @date 2019-07-19 07:48
 * @desc
 **/
public interface DocumentService {
    int insert(Document record);

    int insertSelective(Document record);

    //按照文档id 查询
    Document selectByDocId(int docId);

    //按照文档名称查询
    Document selectByDocName(String docName);
}
