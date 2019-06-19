package cn.edu.whu.irlab.irep.service;

import cn.edu.whu.irlab.irep.IrepApplication;
import cn.edu.whu.irlab.irep.entity.Result;
import cn.edu.whu.irlab.irep.service.impl.ResultServiceImpl;
import cn.edu.whu.irlab.irep.service.util.ReadDoc;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 向数据库中插入参考排序数据
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IrepApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PerformanceTest {

    @Autowired
    public ResultServiceImpl resultService;

    @Test
    public void insertStandard(){

        JSONObject standardObject=new JSONObject();

        String standardDir="resources/results/standard/standard.json";

        standardObject=JSONObject.parseObject(ReadDoc.readDoc(standardDir));//读标准排序

        int queryId=0;
        for (String s:
                standardObject.keySet()) {
            JSONArray jsonArray=standardObject.getJSONArray(s);
            for (int i = 0; i <jsonArray.size() ; i++) {
                Result result=new Result();
                JSONObject jsonObject =jsonArray.getJSONObject(i);
                result.setDocId(jsonObject.getIntValue("web_id"));
                result.setQuery(s);
                result.setQueryId(queryId);
                result.setDocRank(jsonObject.getIntValue("rank"));
                result.setTitle(jsonObject.getString("title"));
                result.setIsChinese(1);
                result.setIndexType("standard");
                result.setModelType("standard");
                System.out.println(result);
                resultService.insert(result);
            }
            queryId++;
        }

    }
    @Test
    public void insertStandard1(){

    }
}
