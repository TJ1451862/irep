package cn.edu.whu.irlab.irep.controller.experiment;


import cn.edu.whu.irlab.irep.base.dao.impl.ResultServiceImpl;
import cn.edu.whu.irlab.irep.base.dao.impl.RetrieverServiceImpl;
import cn.edu.whu.irlab.irep.base.dao.impl.UserRetrieverServiceImpl;
import cn.edu.whu.irlab.irep.base.entity.Result;
import cn.edu.whu.irlab.irep.base.entity.Retriever;
import cn.edu.whu.irlab.irep.base.entity.User;
import cn.edu.whu.irlab.irep.base.entity.UserRetriever;
import cn.edu.whu.irlab.irep.service.experiment.retrieval.boolmodel.BoolRetriever;
import cn.edu.whu.irlab.irep.service.experiment.retrieval.boolmodel.ResultForBool;
import cn.edu.whu.irlab.irep.service.experiment.retrieval.boolmodel.TermsForBool;
import cn.edu.whu.irlab.irep.service.util.Constructor;
import cn.edu.whu.irlab.irep.service.util.Find;
import cn.edu.whu.irlab.irep.service.util.ReadDoc;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chr
 * @version 1.0
 * @date 2019-07-26 17:08
 * @desc 布尔模型交互层
 **/

@RestController
@RequestMapping(value = "IRforCN/Retrieval/boolModel")
public class BoolController {

    @Autowired
    public BoolRetriever boolRetriever;

    @Autowired
    public RetrieverServiceImpl retrieverService;

    @Autowired
    public UserRetrieverServiceImpl userRetrieverService;

    @Autowired
    public ResultServiceImpl resultService;


    /**
     * 返回检索结果
     *
     * @param termarray            检索式
     * @param operatorarray        操作符
     * @return 检索结果
     */
    @PostMapping("/blSearch")
    public JSONArray BoolSearchController(@RequestParam(name = "termarray") String[] termarray,
                                          @RequestParam(name = "operatorarray") String[] operatorarray,
                                        HttpServletRequest request) {

        boolRetriever.initBoolRetriever(termarray,operatorarray,request);
        boolRetriever.search();
        JSONArray searchResult = new JSONArray();
        List<ResultForBool> resultList = boolRetriever.getDocResult();
        for (int i = 0; i < resultList.size(); i++) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("title", resultList.get(i).getTitle());
            jsonObject.put("content", Find.findDoc(resultList.get(i).getDocID(), true));
            searchResult.add(jsonObject);
        }
        return searchResult;
    }

    /**
     * 返回检索式的预处理结果
     *
     * @param termarray            检索式
     * @param operatorarray        操作符
     * @return 检索式的预处理结果
     */
    @PostMapping
    @RequestMapping("/queryProcess")
    public JSONObject BoolQueryProcess(@RequestParam(name = "termarray") String[] termarray,
                                       @RequestParam(name = "operatorarray") String[] operatorarray,
                                       HttpServletRequest request) {
        boolRetriever.initBoolRetriever(termarray,operatorarray,request);
        boolRetriever.search();
        JSONObject ppq = new JSONObject();
        ppq.put("easyquery", boolRetriever.geteasyquary());
        ppq.put("result",boolRetriever.getFinalquery() );
        return ppq;
    }

    /**
     * 返回各词对应的布尔向量
     *
     * @param termarray            检索式
     * @param operatorarray        操作符
     * @return 检索式的预处理结果
     */
    @PostMapping
    @RequestMapping("/boolvector")
    public List<JSONObject> BoolVector(@RequestParam(name = "termarray") String[] termarray,
                                       @RequestParam(name = "operatorarray") String[] operatorarray,
                                       HttpServletRequest request) {
        boolRetriever.initBoolRetriever(termarray,operatorarray,request);
        boolRetriever.search();

        List<TermsForBool> terms_ids =boolRetriever.getterm_id();
        List<JSONObject> boolVectors = new ArrayList<>();
        for(int i=0;i<terms_ids.size();i++){
            JSONObject ppq = new JSONObject();
            ppq.put("term", terms_ids.get(i).getTerm());
            ppq.put("vector",terms_ids.get(i).getTerm_id() );
            boolVectors.add(ppq);
        }
        return boolVectors;
    }

    /**
     * 返回计算完毕的布尔向量
     *
     * @param termarray            检索式
     * @param operatorarray        操作符
     * @return 检索式的预处理结果
     */
    @PostMapping
    @RequestMapping("/boolcalculate")
    public JSONObject BoolCalculate(@RequestParam(name = "termarray") String[] termarray,
                                    @RequestParam(name = "operatorarray") String[] operatorarray,
                                    HttpServletRequest request) {
        boolRetriever.initBoolRetriever(termarray,operatorarray,request);
        boolRetriever.search();
        JSONObject ppq = new JSONObject();
        ppq.put("quary", boolRetriever.getFinalquery());
        ppq.put("calculateresult",boolRetriever.getIdResult());
        return ppq;
    }


    /**
     * 向result表中插入结果数据
     * 如果数据不存在则插入，如果数据存在则不插入
     */
    @PostMapping("/insertResult")
    public ModelMap insertResultController(@RequestParam(name = "termarray") String[] termarray,
                                           @RequestParam(name = "operatorarray") String[] operatorarray,
                                            @RequestParam(name = "analyzerName") String analyzerName,
                                           @RequestParam(name = "isRemoveStopWord") boolean isRemoveStopWord,
                                           HttpServletRequest request) {

        String standardQuery = ReadDoc.readDoc("resources/results/standardQuery.json");

        //初始化retriever
        Retriever retriever = new Retriever();
        retriever.setIsChinese(true);
        retriever.setAnalyzer(analyzerName);
        retriever.setIsRemoveStopWord(isRemoveStopWord);
        String retrieverId = Constructor.retrieverIdConstructor(retriever);
        retriever.setRetrieverId(retrieverId);

        //检查数据库中是否已经存在标准查询的检索结果数据，如果没有则生成并插入
        Retriever retriever1 = retrieverService.selectByPrimaryKey(retrieverId);
        if (retriever1 == null) {
            //插入retriever
            retrieverService.insert(retriever);
            JSONArray queryList = JSONArray.parseArray(standardQuery);

            for (int i = 0; i < queryList.size(); i++) {
                String queryContent = queryList.getJSONObject(i).getString("query");
                int queryId = queryList.getJSONObject(i).getIntValue("queryId");
                boolRetriever.initBoolRetriever(termarray, operatorarray, request);
                boolRetriever.search();
                List<ResultForBool> resultAfterSort = boolRetriever.getDocResult();
                for (int j = 0; j < resultAfterSort.size(); j++) {
                    Result result = new Result();
                    result.setDocId(resultAfterSort.get(j).getDocID());
                    result.setDocRank(j);
                    result.setQuery(queryContent);
                    result.setQueryId(queryId);
                    result.setTitle(resultAfterSort.get(j).getTitle());
                    result.setRetrieverId(retrieverId);
                    resultService.insertSelective(result);
                }
            }
        }

        //保存检索器
        ModelMap modelMap = new ModelMap();
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            int userId = user.getId();
            int retrieverNum = 0;
            int state = 0;
            UserRetriever userRetriever = userRetrieverService.selectByPrimaryKey(userId);
            if (userRetriever == null) {
                userRetriever = new UserRetriever();
                userRetriever.setUserId(userId);
                userRetriever.setRetriever1(retrieverId);
                state = userRetrieverService.insertSelective(userRetriever);
            } else {
                switch (retrieverNum) {
                    case 1:
                        userRetriever.setRetriever1(retrieverId);
                        break;
                    case 2:
                        userRetriever.setRetriever2(retrieverId);
                        break;
                    case 3:
                        userRetriever.setRetriever3(retrieverId);
                        break;
                    default:
                        userRetriever.setRetriever1(retrieverId);
                        break;
                }
                state = userRetrieverService.updateByPrimaryKeySelective(userRetriever);
            }
            if (state == 1) {
                modelMap.put("code", 1);
                modelMap.put("message", "保存成功");
            } else {
                modelMap.put("code", 0);
                modelMap.put("message", "保存失败");
            }
        }

        return modelMap;
    }



}


