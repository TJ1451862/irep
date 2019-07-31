package cn.edu.whu.irlab.irep.service;

import cn.edu.whu.irlab.irep.IrepApplication;
import cn.edu.whu.irlab.irep.base.dao.experiment.StandardQueryService;
import cn.edu.whu.irlab.irep.base.entity.experiment.Result;
import cn.edu.whu.irlab.irep.base.dao.experiment.impl.ResultServiceImpl;
import cn.edu.whu.irlab.irep.base.entity.experiment.StandardQuery;
import cn.edu.whu.irlab.irep.service.util.ReadDoc;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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

    @Autowired
    private StandardQueryService standardQueryService;


    @Test
    public void insertStandard() {

        JSONObject standardObject = new JSONObject();

        String standardDir = "resources/results/standard/standard.json";

        standardObject = JSONObject.parseObject(ReadDoc.readDoc(standardDir));//读标准排序

        List<StandardQuery> standardQueries=standardQueryService.selectAll();

        for (String s :
                standardObject.keySet()) {
            int queryId=0;
            for (StandardQuery sq:
                 standardQueries) {
                if(s.equals(sq.getQueryContent())){
                    queryId=sq.getId();
                }
            }
            JSONArray jsonArray = standardObject.getJSONArray(s);
            for (int i = 0; i < jsonArray.size(); i++) {
                Result result = new Result();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                result.setDocId(jsonObject.getIntValue("web_id"));
                result.setQueryId(queryId);
                result.setDocRank(jsonObject.getIntValue("rank"));
                result.setRetrieverId("1000_00");
                result.setScore(jsonObject.getInteger("score"));
////                System.out.println(result);
                resultService.insertSelective(result);
            }
        }
    }

    /**
     * 获取标准query
     */
    @Test
    public void getStandardQuery() {
        List<StandardQuery> queries=standardQueryService.selectAll();
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < queries.size(); i++) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("queryId", queries.get(i).getId());
            jsonObject.put("query", queries.get(i).getQueryContent());
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