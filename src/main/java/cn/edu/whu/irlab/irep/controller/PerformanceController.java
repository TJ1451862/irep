package cn.edu.whu.irlab.irep.controller;

import cn.edu.whu.irlab.irep.mybatis.entity.Result;
import cn.edu.whu.irlab.irep.mybatis.entity.Retriever;
import cn.edu.whu.irlab.irep.mybatis.entity.User;
import cn.edu.whu.irlab.irep.mybatis.entity.UserRetriever;
import cn.edu.whu.irlab.irep.mybatis.service.ResultService;
import cn.edu.whu.irlab.irep.mybatis.service.RetrieverService;
import cn.edu.whu.irlab.irep.mybatis.service.UserRetrieverService;
import cn.edu.whu.irlab.irep.service.perfomance.Evaluator;
import cn.edu.whu.irlab.irep.service.util.ReadDoc;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 检索模型性能 控制层
 */
@Controller
@RequestMapping("IRforCN/performance")
public class PerformanceController {

    @Autowired
    public ResultService resultService;

    @Autowired
    public UserRetrieverService userRetrieverService;

    @Autowired
    public RetrieverService retrieverService;

    /**
     * 返回标准排序
     *
     * @param queryId 标注查询Id
     * @return 标准排序
     */
    @RequestMapping("/standardSorting")
    @ResponseBody
    public JSONArray standardSortingController(@RequestParam("queryId") int queryId) {
        JSONArray standardArray = new JSONArray();
        List<Result> standardList = initStandardList(queryId);
        for (int i = 0; i < standardList.size(); i++) {
            Result standard = standardList.get(i);
            standardArray.add(JSONObject.parse(JSON.toJSONString(standard)));
        }
        return standardArray;
    }

    /**
     * 返回检索器参数
     *
     * @param retrieverNum 检索器序号
     * @param request
     * @return 检索器参数
     */
    @RequestMapping("/modelParam")
    @ResponseBody
    public JSONObject modelParamController(@RequestParam("retrievalNum") int retrieverNum, HttpServletRequest request) {
        JSONObject modelParam = new JSONObject();
        User user = (User) request.getSession().getAttribute("user");
        UserRetriever userRetriever = userRetrieverService.selectByPrimaryKey(user.getId());
        String retrieverId = initRetrieverId(userRetriever, retrieverNum);
        Retriever retriever = retrieverService.selectByPrimaryKey(retrieverId);

        if (retriever.getIsRemoveStopWord()) {
            modelParam.put("是否去停用词", "是");
        } else {
            modelParam.put("是否去停用词", "否");
        }

        switch (retriever.getAnalyzer()) {
            case "standard":
                modelParam.put("分词器名称", "标准分词器");
                break;
            case "whitespace":
                modelParam.put("分词器名称", "空格分词器");
                break;
            case "simple":
                modelParam.put("分词器名称", "简单分词器");
                break;
            case "CJK":
                modelParam.put("分词器名称", "二分法分词器");
                break;
            case "smartChinese":
                modelParam.put("分词器名称", "中文智能分词器");
                break;
            default:
                break;
        }

        modelParam.put("模型名称", retriever.getModel());
        switch (retriever.getFormulaId()) {
            case 1:
                modelParam.put("TF计算公式", "<math><msub><mi>tf</mi><mi>t,d</mi></msub></math>");
                break;
            case 2:
                modelParam.put("TF计算公式", "<math><mrow><mn>1</mn><mo>+</mo><mi>log(</mi><msub><mi>tf</mi><mi>t,d</mi></msub><mo>)</mo></mrow></math>");
                break;
            case 3:
                modelParam.put("TF计算公式", "<math><mi>a</mi><mo>+</mo><mrow><mfrac><mrow><mi>a</mi><mo>&#215</mo><msub><mi>tf</mi><mi>t,d</mi></msub></mrow><mrow><msub><mi>max</mi><mi>t</mi></msub><mo>(</mo><msub><mi>tf</mi><mi>t,d</mi></msub><mo>)</mo></mrow></mfrac></mrow></math>");
                break;
            case 4:
                modelParam.put("TF计算公式", "<math><mrow><msub><mi>tf</mi><mi>t,d</mi></msub><mo>&#62</mo><mn>0</mn><mo>?</mo><mn>1</mn><mo>:</mo><mn>0</mn></mrow></math>");
                break;
            case 5:
                modelParam.put("TF计算公式", "<math><mrow><mfrac><mrow><mn>1</mn><mo>+</mo><mi>log(</mi><msub><mi>tf</mi><mi>t,d</mi></msub><mo>)</mo></mrow><mrow><mn>1</mn><mo>+</mo><mi>log(</mi><msub><mi>ave</mi><mi>t &#8712d</mi></msub><mo>(</mo><msub><mi>tf</mi><mi>t,d</mi></msub><mo>)</mo><mo>)</mo></mrow></mfrac></mrow></math>");
                break;
            default:
                break;
        }
        if (retriever.getParamName1() != null) {
            modelParam.put(retriever.getParamName1(), retriever.getParam1());
        }
        if (retriever.getParamName2() != null) {
            modelParam.put(retriever.getParamName2(), retriever.getParam2());
        }
        return modelParam;
    }

    /**
     * 返回检索结果
     *
     * @param queryId      查询Id
     * @param retrieverNum 检索器序号
     * @param request
     * @return 检索结果
     */
    @RequestMapping("/searchResult")
    @ResponseBody
    public JSONArray searchResultController(@RequestParam("queryId") int queryId, @RequestParam("retrievalNum") int retrieverNum, HttpServletRequest request) {
        JSONArray jsonArray = new JSONArray();
        List<Result> standardList = initStandardList(queryId);
        List<Result> resultList = initResultList(queryId, retrieverNum, request);
        for (int i = 0; i < resultList.size(); i++) {
            JSONObject jsonResult = new JSONObject();
            Result result = resultList.get(i);
            jsonResult.put("docRank", result.getDocRank());
            jsonResult.put("docId", result.getDocId());
            jsonResult.put("title", result.getTitle());
            jsonResult.put("isExisting", "");
            for (int j = 0; j < standardList.size(); j++) {
                Result standardResult = standardList.get(j);
                if (standardResult.getDocId() == result.getDocId()) {
                    jsonResult.put("isExisting", "&radic;");
                }
            }
            jsonArray.add(jsonResult);
        }
        return jsonArray;
    }

    /**
     * 返回针对单个query的性能指标
     * @param queryId 查询Id
     * @param retrieverNum 检索器序号
     * @param request
     * @return 针对单个query的性能指标
     */
    @RequestMapping("/individual")
    @ResponseBody
    public JSONArray individualPerformanceController(@RequestParam("queryId") int queryId, @RequestParam("retrievalNum") int retrieverNum, HttpServletRequest request) {
        JSONArray performance = new JSONArray();
        Evaluator evaluator = new Evaluator();

        List<Result> standardList = initStandardList(queryId);
        List<Result> resultList = initResultList(queryId, retrieverNum, request);

        JSONObject precision = new JSONObject();
        JSONObject p_5 = new JSONObject();
        JSONObject p_10 = new JSONObject();
        JSONObject p_20 = new JSONObject();

        JSONObject recall = new JSONObject();
        JSONObject r_5 = new JSONObject();
        JSONObject r_10 = new JSONObject();
        JSONObject r_20 = new JSONObject();

        JSONObject ndcg = new JSONObject();
        JSONObject ndcg_5 = new JSONObject();
        JSONObject ndcg_10 = new JSONObject();
        JSONObject ndcg_20 = new JSONObject();

        JSONObject ap = new JSONObject();
        JSONObject f1 = new JSONObject();

        precision.put("precision", evaluator.calculatePrecision(standardList, resultList, null));
        p_5.put("p@5", evaluator.calculatePrecision(standardList, resultList, 5));
        p_10.put("p@10", evaluator.calculatePrecision(standardList, resultList, 10));
        p_20.put("p@20", evaluator.calculatePrecision(standardList, resultList, 20));

        recall.put("recall", evaluator.calculateRecall(standardList, resultList, null));
        r_5.put("r@5", evaluator.calculateRecall(standardList, resultList, 5));
        r_10.put("r@10", evaluator.calculateRecall(standardList, resultList, 10));
        r_20.put("r@20", evaluator.calculateRecall(standardList, resultList, 20));

        ndcg.put("ndcg", evaluator.calculateNDCG(standardList, resultList, null));
        ndcg_5.put("ndcg@5", evaluator.calculateNDCG(standardList, resultList, 5));
        ndcg_10.put("ndcg@10", evaluator.calculateNDCG(standardList, resultList, 10));
        ndcg_20.put("ndcg@20", evaluator.calculateNDCG(standardList, resultList, 20));

        ap.put("ap", evaluator.calculateAP(standardList, resultList));
        f1.put("F1", evaluator.calculateF1(standardList, resultList));

        performance.add(precision);
        performance.add(p_5);
        performance.add(p_10);
        performance.add(p_20);

        performance.add(recall);
        performance.add(r_5);
        performance.add(r_10);
        performance.add(r_20);

        performance.add(ndcg);
        performance.add(ndcg_5);
        performance.add(ndcg_10);
        performance.add(ndcg_20);

        performance.add(ap);
        performance.add(f1);

        return performance;
    }

    @RequestMapping("/average")
    @ResponseBody
    public JSONArray averageController(@RequestParam("retrievalNum") int retrieverNum, HttpServletRequest request) {
        Evaluator evaluator = new Evaluator();
        JSONArray averagePerformance = new JSONArray();
        String standardQueryDir = "resources/results/standardQuery.json";
        JSONArray standardQueryArray = JSONArray.parseArray(ReadDoc.readDoc(standardQueryDir));

        int size = standardQueryArray.size();

        JSONObject precision = new JSONObject();
        JSONObject p_5 = new JSONObject();
        JSONObject p_10 = new JSONObject();
        JSONObject p_20 = new JSONObject();

        JSONObject recall = new JSONObject();
        JSONObject r_5 = new JSONObject();
        JSONObject r_10 = new JSONObject();
        JSONObject r_20 = new JSONObject();

        JSONObject ndcg = new JSONObject();
        JSONObject ndcg_5 = new JSONObject();
        JSONObject ndcg_10 = new JSONObject();
        JSONObject ndcg_20 = new JSONObject();

        JSONObject map = new JSONObject();
        JSONObject f1 = new JSONObject();

        double p = 0, p5 = 0, p10 = 0, p20 = 0;
        double r = 0, r5 = 0, r10 = 0, r20 = 0;
        double n = 0, n5 = 0, n10 = 0, n20 = 0;
        double apDoule = 0;
        double f1Double = 0;


        for (int i = 0; i < standardQueryArray.size(); i++) {
            JSONObject standardQuery = standardQueryArray.getJSONObject(i);
            int queryId = standardQuery.getIntValue("queryId");
            List<Result> standardList = initStandardList(queryId);
            List<Result> resultList = initResultList(queryId, retrieverNum, request);

            p += evaluator.calculatePrecision(standardList, resultList, null);
            p5 += evaluator.calculatePrecision(standardList, resultList, 5);
            p10 += evaluator.calculatePrecision(standardList, resultList, 10);
            p20 += evaluator.calculatePrecision(standardList, resultList, 20);

            r += evaluator.calculateRecall(standardList, resultList, null);
            r5 += evaluator.calculateRecall(standardList, resultList, 5);
            r10 += evaluator.calculateRecall(standardList, resultList, 10);
            r20 += evaluator.calculateRecall(standardList, resultList, 20);

            n += evaluator.calculateNDCG(standardList, resultList, null);
            n5 += evaluator.calculateNDCG(standardList, resultList, 5);
            n10 += evaluator.calculateNDCG(standardList, resultList, 10);
            n20 += evaluator.calculateNDCG(standardList, resultList, 20);

            apDoule += evaluator.calculateAP(standardList, resultList);
            f1Double += evaluator.calculateF1(standardList, resultList);
        }

        precision.put("precision", average(p, size));
        p_5.put("p@5", average(p5, size));
        p_10.put("p@10", average(p10, size));
        p_5.put("p@20", average(p20, size));

        recall.put("recall",average(r,size));
        r_5.put("r@5",average(r5,size));
        r_10.put("r@10",average(r10,size));
        r_20.put("r@20",average(r20,size));

        ndcg.put("ndcg",average(n,size));
        ndcg_5.put("ndcg@5",average(n5,size));
        ndcg_10.put("ndcg@10",average(n10,size));
        ndcg_20.put("ndcg@20",average(n20,size));

        map.put("MAP",average(apDoule,size));
        f1.put("F1",average(f1Double,size));

        averagePerformance.add(precision);
        averagePerformance.add(p_5);
        averagePerformance.add(p_10);
        averagePerformance.add(p_20);

        averagePerformance.add(recall);
        averagePerformance.add(r_5);
        averagePerformance.add(r_10);
        averagePerformance.add(r_20);

        averagePerformance.add(ndcg);
        averagePerformance.add(ndcg_5);
        averagePerformance.add(ndcg_10);
        averagePerformance.add(ndcg_20);

        averagePerformance.add(map);
        averagePerformance.add(f1);

        return averagePerformance;
    }

    private List<Result> initResultList(int queryId, int retrieverNum, HttpServletRequest request) {

        User user = (User) request.getSession().getAttribute("user");
        UserRetriever userRetriever = userRetrieverService.selectByPrimaryKey(user.getId());
        String retrieverId = initRetrieverId(userRetriever, retrieverNum);

        Result result = new Result();
        result.setQueryId(queryId);
        result.setRetrieverId(retrieverId);
        List<Result> resultList = resultService.select(result);

        return resultList;
    }

    private List<Result> initStandardList(int queryId) {
        Result standard = new Result();
        standard.setQueryId(queryId);
        standard.setRetrieverId("1000_00");
        List<Result> standardList = resultService.select(standard);
        return standardList;
    }

    private String initRetrieverId(UserRetriever userRetriever, int retrieverNum) {
        String retrieverId = "";
        switch (retrieverNum) {
            case 1:
                retrieverId = userRetriever.getRetriever1();
                break;
            case 2:
                retrieverId = userRetriever.getRetriever2();
                break;
            case 3:
                retrieverId = userRetriever.getRetriever3();
                break;
            default:
                break;
        }
        return retrieverId;
    }

    private double average(double sum, int size) {
        double avg = sum / size;
        return avg;
    }

}
