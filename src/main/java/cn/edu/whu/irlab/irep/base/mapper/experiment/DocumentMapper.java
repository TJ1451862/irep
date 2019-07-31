package cn.edu.whu.irlab.irep.base.mapper.experiment;

import cn.edu.whu.irlab.irep.base.entity.experiment.Document;
import org.springframework.stereotype.Component;

@Component
public interface DocumentMapper {
    int insert(Document record);

    int insertSelective(Document record);

    Document selectByDocId(Integer docId);

    Document selectByDocName(String name);
}