package cn.edu.whu.irlab.irep.controller;


import cn.edu.whu.irlab.irep.entity.Result;
import cn.edu.whu.irlab.irep.entity.Retriever;
import cn.edu.whu.irlab.irep.entity.User;
import cn.edu.whu.irlab.irep.entity.UserRetriever;
import cn.edu.whu.irlab.irep.service.impl.ResultServiceImpl;
import cn.edu.whu.irlab.irep.service.retrievalModel.languagemodel.LMRetriever;
import cn.edu.whu.irlab.irep.service.retrievalModel.languagemodel.QueryForLM;
import cn.edu.whu.irlab.irep.service.retrievalModel.languagemodel.DocForLM;
import cn.edu.whu.irlab.irep.service.retrievalModel.languagemodel.ResultForLM;
import cn.edu.whu.irlab.irep.service.retrievalModel.vsmmodel.DocForVSM;
import cn.edu.whu.irlab.irep.service.retrievalModel.vsmmodel.ResultI;
import cn.edu.whu.irlab.irep.service.util.Constructor;
import cn.edu.whu.irlab.irep.service.util.Find;
import cn.edu.whu.irlab.irep.service.util.ReadDoc;
import cn.edu.whu.irlab.irep.service.impl.RetrieverServiceImpl;
import cn.edu.whu.irlab.irep.service.impl.UserRetrieverServiceImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @author Jane
 * @version 1.0
 * @date 2010-07-13 15:32
 * @desc 语言模型交互层
 **/
@Controller
@RequestMapping(value="IRforCN/Retrieval/languageModel")
public class LMController {

    @Autowired
    public LMRetriever languageRetriever;

    @Autowired
    public RetrieverServiceImpl retrieverService;

    @Autowired
    public UserRetrieverServiceImpl userRetrieverService;

    @Autowired
    public ResultServiceImpl resultService;

    /**
     * 返回检索结果
     * @param query 检索式
     * @param smoothParam  平滑系数
     * @param analyzerName  分词器
     * @param isRemoveStopWord 是否去除停用词
     * @return 检索结果
     */
    @ResponseBody
    @RequestMapping("/lmSearch")
    public JSONArray LMSearchController(@RequestParam(name="query") String query,
                                        @RequestParam(name="smoothParam") double smoothParam,
                                        @RequestParam(name="analyzerName") String analyzerName,
                                        @RequestParam(name = "isRemoveStopWord") boolean isRemoveStopWord){

        isNeedSearch(query, smoothParam, analyzerName, isRemoveStopWord);
        JSONArray searchResult=new JSONArray();
        List<ResultForLM> resultList = languageRetriever.getResultAfterSort();
        for (int i = 0; i < resultList.size(); i++) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("title", resultList.get(i).getTitle());
            jsonObject.put("content", Find.findDoc(resultList.get(i).getDocID(), true));
            searchResult.add(jsonObject);
        }
        return searchResult;
    }


    /**
     * 判断是否需要检索
     * @param query 检索式
     * @param smoothParam 平滑系数
     * @param analyzerName 分词器
     * @param isRemoveStopWord 是否去除停用词
     */
    public void isNeedSearch(@RequestParam(name="query") String query,
                             @RequestParam(name="smoothParam") double smoothParam,
                             @RequestParam(name="analyzerName") String analyzerName,
                             @RequestParam(name = "isRemoveStopWord") boolean isRemoveStopWord){
        languageRetriever.initLMRetriever(query, smoothParam, analyzerName, isRemoveStopWord);
        languageRetriever.search();
    }


    /**
     * 返回检索式的预处理结果
     * @param query 检索式
     * @param analyzerName 分词器
     * @param isRemoveStopWord 是否去除停用词
     * @return 检索式的预处理结果
     */
    @ResponseBody
    @RequestMapping("/queryProcess")
    public JSONObject LMQueryProcess(@RequestParam(name="query") String query,
                                     @RequestParam(name="smoothParam") double smoothParam,
                                     @RequestParam(name="analyzerName") String analyzerName,
                                     @RequestParam(name = "isRemoveStopWord") boolean isRemoveStopWord){
        JSONObject ppq = new JSONObject();
        isNeedSearch(query, smoothParam, analyzerName, isRemoveStopWord);
        ppq.put("query", query);
        ppq.put("result", languageRetriever.getQuery().getPreProcessResult());
        return ppq;
    }

    /**
     *为每篇文档建立LM，计算其各个词的频率
     * @param
     * @return 给出各文档中各词词项的频率（存在tfs中)，即各文档的LM
     */
    @ResponseBody
    @RequestMapping("/lmOfDocs")
    public List<JSONObject> getTfsOfDocsController(@RequestParam(name="query") String query,
                                                   @RequestParam(name="smoothParam") double smoothParam,
                                                   @RequestParam(name="analyzerName") String analyzerName,
                                                   @RequestParam(name = "isRemoveStopWord") boolean isRemoveStopWord) {
        isNeedSearch(query, smoothParam, analyzerName, isRemoveStopWord);
        List<DocForLM> docForLMList = languageRetriever.getDocForLMList();
        List<JSONObject> lmsOfDocs = new ArrayList<>();
        for (int i = 0; i < docForLMList.size(); i++) {
            JSONObject jsonObject = new JSONObject();
            JSONObject lmsJson;
            jsonObject.put("title", Find.findTitle(docForLMList.get(i).getId(), true));
            jsonObject.put("docId", docForLMList.get(i).getId());
            lmsJson = JSON.parseObject(JSON.toJSONString(docForLMList.get(i).getLMap()));
            jsonObject.put("lms", lmsJson);
            lmsOfDocs.add(jsonObject);
        }
        return lmsOfDocs;
    }

    //返回各文档的生成概率
    @ResponseBody
    @RequestMapping("/getResult")
    public List<ResultForLM> getResult(@RequestParam(name="query") String query,
                                       @RequestParam(name="smoothParam") double smoothParam,
                                       @RequestParam(name="analyzerName") String analyzerName,
                                       @RequestParam(name = "isRemoveStopWord") boolean isRemoveStopWord){
        isNeedSearch(query, smoothParam, analyzerName, isRemoveStopWord);
        return languageRetriever.getResult();
    }

    //返回降序的各文档的生成概率
    @ResponseBody
    @RequestMapping("/getResultAfterSort")
    public List<ResultForLM> getResultAfterSort(@RequestParam(name="query") String query,
                                       @RequestParam(name="smoothParam") double smoothParam,
                                       @RequestParam(name="analyzerName") String analyzerName,
                                       @RequestParam(name = "isRemoveStopWord") boolean isRemoveStopWord){
        isNeedSearch(query, smoothParam, analyzerName, isRemoveStopWord);
        return languageRetriever.getResultAfterSort();
    }

    /**
     * 向result表中插入结果数据
     * 如果数据不存在则插入，如果数据存在则不插入
     */
    @RequestMapping("/insertResult")
    @ResponseBody
    public ModelMap insertResultController(@RequestParam(name = "smoothParam") double smoothParam,
                                           @RequestParam(name = "analyzerName") String analyzerName,
                                           @RequestParam(name = "isRemoveStopWord") boolean isRemoveStopWord,
                                           HttpServletRequest request) {

        String standardQuery = ReadDoc.readDoc("resources/results/standardQuery.json");

        //初始化retriever
        Retriever retriever = new Retriever();
        retriever.setIsChinese(true);
        retriever.setAnalyzer(analyzerName);
        retriever.setIsRemoveStopWord(isRemoveStopWord);
        retriever.setModel("lm");
        retriever.setParamName1("平滑系数");
        retriever.setParam1((int) (smoothParam * 100));
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
                languageRetriever.initLMRetriever(queryContent, smoothParam, analyzerName, isRemoveStopWord);
                languageRetriever.search();
                List<ResultForLM> resultAfterSort =languageRetriever.getResultAfterSort();
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
                state=userRetrieverService.updateByPrimaryKeySelective(userRetriever);
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
