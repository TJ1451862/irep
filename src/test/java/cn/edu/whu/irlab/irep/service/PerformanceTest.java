package cn.edu.whu.irlab.irep.service;

import cn.edu.whu.irlab.irep.IrepApplication;
import cn.edu.whu.irlab.irep.entity.Result;
import cn.edu.whu.irlab.irep.service.impl.ResultServiceImpl;
import cn.edu.whu.irlab.irep.service.util.ReadDoc;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * 向数据库中插入参考排序数据
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IrepApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PerformanceTest {

    @Autowired
    public ResultServiceImpl resultService;


    @Test
    public void insertStandard() {

        JSONObject standardObject = new JSONObject();

        String standardDir = "resources/results/standard/standard.json";

        standardObject = JSONObject.parseObject(ReadDoc.readDoc(standardDir));//读标准排序

        int queryId = 0;
        for (String s :
                standardObject.keySet()) {
            JSONArray jsonArray = standardObject.getJSONArray(s);
            for (int i = 0; i < jsonArray.size(); i++) {
                Result result = new Result();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                result.setDocId(jsonObject.getIntValue("web_id"));
                result.setQuery(s);
                result.setQueryId(queryId);
                result.setDocRank(jsonObject.getIntValue("rank"));
                result.setTitle(jsonObject.getString("title"));
                result.setRetrieverId("1000_00");
                result.setScore(jsonObject.getInteger("score"));
////                System.out.println(result);
                resultService.insertSelective(result);
            }
            queryId++;
        }
    }

    /**
     * 获取标准query
     */
    @Test
    public void getStandardQuery() {
        Result result = new Result();
        result.setRetrieverId("1000_00");
        result.setDocRank(1);
        List<Result> resultList = resultService.select(result);
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < resultList.size(); i++) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("queryId", resultList.get(i).getQueryId());
            jsonObject.put("query", resultList.get(i).getQuery());
            jsonArray.add(jsonObject);
        }
//        System.out.println(jsonArray);
    }

    @Test
    public void importStandardQuery() {
//        System.out.println(ReadDoc.readDoc("resources/results/standardQuery"));
    }

    @Test
    public void initStandardListTest(){
        Result standard = new Result();
        standard.setQueryId(6);
        standard.setRetrieverId("1000_00");
        List<Result> standardList = resultService.select(standard);
//        System.out.println(standardList);

    }
}