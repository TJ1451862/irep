package cn.edu.whu.irlab.irep.service.experiment.perfomance.Impl;

import cn.edu.whu.irlab.irep.base.dao.experiment.PerformanceService;
import cn.edu.whu.irlab.irep.base.dao.experiment.ResultService;
import cn.edu.whu.irlab.irep.base.dao.experiment.RetrieverService;
import cn.edu.whu.irlab.irep.base.dao.experiment.StandardQueryService;
import cn.edu.whu.irlab.irep.base.dao.system.UserRetrieverScoreService;
import cn.edu.whu.irlab.irep.base.entity.experiment.Performance;
import cn.edu.whu.irlab.irep.base.entity.experiment.Result;
import cn.edu.whu.irlab.irep.base.entity.experiment.Retriever;
import cn.edu.whu.irlab.irep.base.entity.experiment.StandardQuery;
import cn.edu.whu.irlab.irep.base.entity.system.User;
import cn.edu.whu.irlab.irep.base.entity.system.UserRetrieverScore;
import cn.edu.whu.irlab.irep.service.experiment.perfomance.EvaluateService;
import cn.edu.whu.irlab.irep.service.experiment.retrieval.BoolRetrieverService;
import cn.edu.whu.irlab.irep.service.experiment.retrieval.LMRetrieverService;
import cn.edu.whu.irlab.irep.service.experiment.retrieval.ProbabilityRetrievalService;
import cn.edu.whu.irlab.irep.service.experiment.retrieval.VsmRetrievalService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author gcr19
 * @date 2019-07-28 08:17
 * @desc
 **/
@Service
public class EvaluateServiceImpl implements EvaluateService {

    @Autowired
    private RetrieverService retrieverService;

    @Autowired
    private BoolRetrieverService boolRetrieverService;

    @Autowired
    public VsmRetrievalService vsmRetriever;

    @Autowired
    private ProbabilityRetrievalService probabilityRetrievalService;

    @Autowired
    private LMRetrieverService lmRetrieverService;

    @Autowired
    private UserRetrieverScoreService userRetrieverScoreService;

    @Autowired
    private ResultService resultService;

    @Autowired
    private StandardQueryService standardQueryService;

    @Autowired
    private Evaluator evaluator;

    @Autowired
    private PerformanceService performanceService;

    @Override
    public int testRetriever(Retriever retriever) {
        int state;
        Retriever tempRetriever = retrieverService.selectByPrimaryKey(retriever.getRetrieverId());
        if (tempRetriever == null) {
            retrieverService.insert(retriever);
            state = 0;
        } else {
            state = 1;
        }
        return state;
    }

    @Override
    public Map<String, List<Result>> testRetriever(String query, String modelName, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int userId = user.getId();
        UserRetrieverScore userRetrieverScore = userRetrieverScoreService.selectByUserId(userId);

        switch (modelName) {
            case "boolModel":
                boolRetrieverService.initBoolRetriever(query, request);
                return boolRetrieverService.testRetriever();
            case "vsm": {
                String retrieverId = userRetrieverScore.getVsmRetriever();
                int formulaId = Integer.valueOf(retrieverId.substring(6));
                double param = 0;
                if (formulaId == 3) {
                    param = Double.valueOf(retrieverId.substring(8, 9)) / 100;
                }
                vsmRetriever.initVSMRetriever(query, formulaId, param, request);
                return vsmRetriever.testRetriever();
            }
            case "probabilityModel": {
                String retrieverId = userRetrieverScore.getProbabilityRetriever();
                List<String> list = Arrays.asList(retrieverId.split("_"));
                double k = Double.valueOf(list.get(2)) / 100;
                double b = Double.valueOf(list.get(3)) / 100;
                probabilityRetrievalService.initRetriever(query, k, b, request);
                return probabilityRetrievalService.testRetriever();
            }
            case "languageModel": {
                String retrieverId = userRetrieverScore.getLanugaeRetriever();
                List<String> list = Arrays.asList(retrieverId.split("_"));
                double smoothParam = Double.valueOf(list.get(2)) / 100;
                lmRetrieverService.initLMRetriever(query, smoothParam, request);
                return lmRetrieverService.testRetriever();
            }
            default:
                return null;
        }
    }

    @Override
    public Performance individualPerformance(String query, String modelName, HttpServletRequest request) {
        StandardQuery standardQuery = standardQueryService.selectByQueryContent(query);
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int userId = user.getId();
        UserRetrieverScore userRetrieverScore = userRetrieverScoreService.selectByUserId(userId);
        Performance performance;
        String retrieverId;
        List<Result> standardList = initStandardList(standardQuery.getId());
        List<Result> resultList;
        switch (modelName) {
            case "boolModel": {
                retrieverId = userRetrieverScore.getBoolRetriever();
                performance = performanceService.selectByQueryAndRetrieverId(query, retrieverId);
                if (performance != null) {
                    return performance;
                } else {
                    resultList = initResultList(standardQuery.getId(), retrieverId);
                }
                break;
            }
            case "vsm": {
                retrieverId = userRetrieverScore.getVsmRetriever();
                performance = performanceService.selectByQueryAndRetrieverId(query, retrieverId);
                if (performance != null) {
                    return performance;
                } else {
                    resultList = initResultList(standardQuery.getId(), retrieverId);
                }

                break;
            }
            case "probabilityModel": {
                retrieverId = userRetrieverScore.getProbabilityRetriever();
                performance = performanceService.selectByQueryAndRetrieverId(query, retrieverId);
                if (performance != null) {
                    return performance;
                } else {
                    resultList = initResultList(standardQuery.getId(), retrieverId);
                }
                break;
            }
            case "languageModel": {
                retrieverId = userRetrieverScore.getLanugaeRetriever();
                performance = performanceService.selectByQueryAndRetrieverId(query, retrieverId);
                if (performance != null) {
                    return performance;
                } else {
                    resultList = initResultList(standardQuery.getId(), retrieverId);
                }
                break;
            }
            default:
                return null;
        }
        performance = calculatePerformance(standardList, resultList);
        performance.setRetrieverId(resultList.get(0).getRetrieverId());
        performance.setQuery(query);
        performanceService.insertSelective(performance);
        return performance;
    }

    @Override
    public JSONObject calculateAvgPerformances(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int userId = user.getId();
        UserRetrieverScore userRetrieverScore = userRetrieverScoreService.selectByUserId(userId);

        Performance boolModelPerformance = performanceService.selectByQueryAndRetrieverId("avgPerformance", userRetrieverScore.getBoolRetriever());
        if (boolModelPerformance == null) {
            boolModelPerformance = avgPerformance("boolModel", userRetrieverScore.getBoolRetriever(), request);
        }
        Performance vsmPerformance = performanceService.selectByQueryAndRetrieverId("avgPerformance", userRetrieverScore.getVsmRetriever());
        if (vsmPerformance == null) {
            vsmPerformance = avgPerformance("vsm", userRetrieverScore.getVsmRetriever(), request);
        }
        Performance probabilityModelPerformance = performanceService.selectByQueryAndRetrieverId("avgPerformance", userRetrieverScore.getProbabilityRetriever());
        if (probabilityModelPerformance == null) {
            probabilityModelPerformance = avgPerformance("probabilityModel", userRetrieverScore.getProbabilityRetriever(), request);
        }
        Performance languageModelPerformance = performanceService.selectByQueryAndRetrieverId("avgPerformance", userRetrieverScore.getLanugaeRetriever());
        if (languageModelPerformance == null) {
            languageModelPerformance = avgPerformance("languageModel", userRetrieverScore.getLanugaeRetriever(), request);
        }

        JSONObject object =new JSONObject();
        object.put("boolModelPerformance",boolModelPerformance);
        object.put("vsmPerformance",vsmPerformance);
        object.put("probabilityModelPerformance",probabilityModelPerformance);
        object.put("languageModelPerformance",languageModelPerformance);
        return object;
    }

    private Performance avgPerformance(String model, String retrieverId, HttpServletRequest request) {
        List<StandardQuery> standardQueries = standardQueryService.selectAll();
        BigDecimal size = BigDecimal.valueOf(standardQueries.size());

        BigDecimal p = new BigDecimal(0);
        BigDecimal p5 = new BigDecimal(0);
        BigDecimal p10 = new BigDecimal(0);
        BigDecimal p20 = new BigDecimal(0);

        BigDecimal r = new BigDecimal(0);
        BigDecimal r5 = new BigDecimal(0);
        BigDecimal r10 = new BigDecimal(0);
        BigDecimal r20 = new BigDecimal(0);

        BigDecimal n = new BigDecimal(0);
        BigDecimal n5 = new BigDecimal(0);
        BigDecimal n10 = new BigDecimal(0);
        BigDecimal n20 = new BigDecimal(0);

        BigDecimal apDoule = new BigDecimal(0);
        BigDecimal f1Double = new BigDecimal(0);

        for (StandardQuery sq :
                standardQueries) {
            Performance performance = individualPerformance(sq.getQueryContent(), model, request);
            p = p.add(performance.getPrecision());
            p5 = p5.add(performance.getP5());
            p10 = p10.add(performance.getP10());
            p20 = p20.add(performance.getP20());

            r = r.add(performance.getRecall());
            r5 = r5.add(performance.getR5());
            r10 = r10.add(performance.getR10());
            r20 = r20.add(performance.getR20());

            n = n.add(performance.getNdcg());
            n5 = n5.add(performance.getNdcg5());
            n10 = n10.add(performance.getNdcg10());
            n20 = n20.add(performance.getNdcg20());

            apDoule = apDoule.add(performance.getMap());
            f1Double = f1Double.add(performance.getF1());
        }

        p = p.divide(size, RoundingMode.HALF_UP);
        p5 = p5.divide(size, RoundingMode.HALF_UP);
        p10 = p10.divide(size, RoundingMode.HALF_UP);
        p20 = p20.divide(size, RoundingMode.HALF_UP);


        r = r.divide(size, RoundingMode.HALF_UP);
        r5 = r5.divide(size, RoundingMode.HALF_UP);
        r10 = r10.divide(size, RoundingMode.HALF_UP);
        r20 = r20.divide(size, RoundingMode.HALF_UP);

        n = n.divide(size, RoundingMode.HALF_UP);
        n5 = n5.divide(size, RoundingMode.HALF_UP);
        n10 = n10.divide(size, RoundingMode.HALF_UP);
        n20 = n20.divide(size, RoundingMode.HALF_UP);

        apDoule = apDoule.divide(size, RoundingMode.HALF_UP);
        f1Double = f1Double.divide(size, RoundingMode.HALF_UP);

        Performance performance = new Performance();
        performance.setPrecision(p);
        performance.setP5(p5);
        performance.setP10(p10);
        performance.setP20(p20);

        performance.setRecall(r);
        performance.setRecall(r5);
        performance.setRecall(r10);
        performance.setRecall(r20);

        performance.setNdcg(n);
        performance.setNdcg5(n5);
        performance.setNdcg10(n10);
        performance.setNdcg20(n20);

        performance.setMap(apDoule);
        performance.setF1(f1Double);

        performance.setQuery("avgPerformance");
        performance.setRetrieverId(retrieverId);
        performanceService.insertSelective(performance);
        return performance;
    }

    private Performance calculatePerformance(List<Result> standardList, List<Result> resultList) {
        Performance performance = new Performance();

        performance.setPrecision(evaluator.calculatePrecision(standardList, resultList, null));
        performance.setP5(evaluator.calculatePrecision(standardList, resultList, 5));
        performance.setP10(evaluator.calculatePrecision(standardList, resultList, 10));
        performance.setP20(evaluator.calculatePrecision(standardList, resultList, 20));

        performance.setRecall(evaluator.calculateRecall(standardList, resultList, null));
        performance.setR5(evaluator.calculateRecall(standardList, resultList, 5));
        performance.setR10(evaluator.calculateRecall(standardList, resultList, 10));
        performance.setR20(evaluator.calculateRecall(standardList, resultList, 20));

        performance.setNdcg(evaluator.calculateNDCG(standardList, resultList, null));
        performance.setNdcg5(evaluator.calculateNDCG(standardList, resultList, 5));
        performance.setNdcg10(evaluator.calculateNDCG(standardList, resultList, 10));
        performance.setNdcg20(evaluator.calculateNDCG(standardList, resultList, 20));

        performance.setF1(evaluator.calculateF1(standardList, resultList));
        performance.setMap(evaluator.calculateAP(standardList, resultList));

        return performance;
    }


    private List<Result> initStandardList(int queryId) {
        Result standard = new Result();
        standard.setQueryId(queryId);
        standard.setRetrieverId("1000_00");
        List<Result> standardList = resultService.select(standard);
        return standardList;
    }

    private List<Result> initResultList(int queryId, String retrieverId) {

        Result result = new Result();
        result.setQueryId(queryId);
        result.setRetrieverId(retrieverId);
        List<Result> resultList = resultService.select(result);

        return resultList;
    }


}
