package cn.edu.whu.irlab.irep.service.experiment.retrieval;

import cn.edu.whu.irlab.irep.base.dao.experiment.ResultService;
import cn.edu.whu.irlab.irep.base.dao.experiment.StandardQueryService;
import cn.edu.whu.irlab.irep.base.dao.system.UserRetrieverScoreService;
import cn.edu.whu.irlab.irep.base.dao.system.impl.UserRetrieverScoreServiceImpl;
import cn.edu.whu.irlab.irep.base.entity.experiment.Result;
import cn.edu.whu.irlab.irep.base.entity.experiment.Retriever;
import cn.edu.whu.irlab.irep.base.entity.experiment.StandardQuery;
import cn.edu.whu.irlab.irep.base.entity.system.UserRetrieverScore;
import cn.edu.whu.irlab.irep.service.experiment.perfomance.EvaluateService;
import cn.edu.whu.irlab.irep.service.util.BubbleSort;
import cn.edu.whu.irlab.irep.service.vo.Query;
import cn.edu.whu.irlab.irep.service.vo.ResultVo;
import cn.edu.whu.irlab.irep.service.vo.SearchResultVo;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author gcr19
 * @date 2019-07-31 13:23
 * @desc 检索服务
 **/
public abstract class RetrievalService implements RetrieverService {

    protected Retriever retriever;

    protected String analyzerName;

    protected boolean isRemoveStopWord;

    //查询语句
    protected Query query;

    protected HttpSession session;

    @Autowired
    private EvaluateService evaluateService;

    @Autowired
    private StandardQueryService standardQueryService;

    @Autowired
    private ResultService resultService;

    @Autowired
    protected UserRetrieverScoreService userRetrieverScoreService;


    @Override
    public abstract List<SearchResultVo> search();

    @Override
    public abstract List<ResultVo> calculateSimilarity();

    @Override
    public List<ResultVo> descendOrderSimilarity() {
        return BubbleSort.bubbleSort(calculateSimilarity());
    }

    protected void initRetriever(String queryContent,
                                 HttpServletRequest request){
        this.session=request.getSession();
        //分词器名称
        this.analyzerName = (String) session.getAttribute("analyzer");
        //是否去停用词
        this.isRemoveStopWord = (boolean) session.getAttribute("removeStopWord");
        this.query = new Query(queryContent, analyzerName, isRemoveStopWord);
    }

    @Override
    public Map<String, List<Result>> testRetriever() {
        int state = evaluateService.testRetriever(retriever);
        if (state == 0) {
            saveTestResult();
        }
        StandardQuery standardQuery = standardQueryService.selectByQueryContent(query.getContent());
        List<Result> testResults = resultService.selectByQueryIdAndRetrieverId(standardQuery.getId(), retriever.getRetrieverId());
        List<Result> standardResult = resultService.selectByQueryIdAndRetrieverId(standardQuery.getId(), "1000_00");
        Map<String, List<Result>> testResultMap = new HashedMap();
        testResultMap.put("testResults", testResults);
        testResultMap.put("standardResults", standardResult);
        return testResultMap;
    }

    private void saveTestResult() {
        List<StandardQuery> standardQueries = standardQueryService.selectAll();
        List<Result> standardResult = resultService.selectByRetrieverId("1000_00");

        List<Result> forSave=new ArrayList<>();

        for (StandardQuery sq :
                standardQueries) {
            query=new Query(sq.getQueryContent(),analyzerName,isRemoveStopWord);
            List<ResultVo> resultVos = descendOrderSimilarity();
            int rank = 0;
            List<Result> subStandardResult = new ArrayList<>();

            for (Result r :
                    standardResult) {
                if (r.getQueryId().equals(sq.getId())) {
                    subStandardResult.add(r);
                }
            }

            for (ResultVo sr :
                    resultVos) {
                rank++;
                boolean existing = false;
                for (Result r :
                        subStandardResult) {
                    if (r.getDocId().equals(sr.getDocId())) {
                        existing = true;
                    }
                }
                Result result = new Result(sq.getId(), sr.getDocId(),sr.getTitle(), rank, retriever.getRetrieverId(), existing);
                forSave.add(result);
            }
        }
        resultService.insertForEach(forSave);
    }

    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }
}
