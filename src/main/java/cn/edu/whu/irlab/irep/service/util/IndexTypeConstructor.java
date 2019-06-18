package cn.edu.whu.irlab.irep.service.util;

import org.springframework.stereotype.Service;

/**
 * @author gcr
 * @version 1.0
 * @date 2019-06-15 14:18
 * @desc IndexType构造器
 **/

@Service
public class IndexTypeConstructor {

    public String indexTypeConstructor(String analyzer,boolean isRemoveStopWord){
        String indexType;
        String suffixPositive="_remove";
        String suffixNegative="_not_remove";
        if (isRemoveStopWord) indexType=analyzer+suffixPositive;
        else indexType=analyzer+suffixNegative;
        return indexType;
    }


}
