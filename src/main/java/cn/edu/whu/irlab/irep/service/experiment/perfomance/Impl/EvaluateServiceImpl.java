package cn.edu.whu.irlab.irep.service.experiment.perfomance.Impl;

import cn.edu.whu.irlab.irep.base.dao.experiment.RetrieverService;
import cn.edu.whu.irlab.irep.base.dao.system.UserRetrieverScoreService;
import cn.edu.whu.irlab.irep.base.dao.system.impl.UserRetrieverScoreServiceImpl;
import cn.edu.whu.irlab.irep.base.entity.experiment.Result;
import cn.edu.whu.irlab.irep.base.entity.experiment.Retriever;
import cn.edu.whu.irlab.irep.base.entity.system.User;
import cn.edu.whu.irlab.irep.base.entity.system.UserRetrieverScore;
import cn.edu.whu.irlab.irep.service.experiment.perfomance.EvaluateService;
import cn.edu.whu.irlab.irep.service.experiment.retrieval.BoolRetrieverService;
import cn.edu.whu.irlab.irep.service.experiment.retrieval.ProbabilityRetrievalService;
import cn.edu.whu.irlab.irep.service.experiment.retrieval.VsmRetrievalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
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
    private UserRetrieverScoreService userRetrieverScoreService;


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
                List<String> queryList = new ArrayList<>();
                queryList.add(query);
                boolRetrieverService.initBoolRetriever(queryList, request);
                return boolRetrieverService.testRetriever();
            case "vsm": {
                String retrieverId = userRetrieverScore.getVsmRetriever();
                int formulaId = Integer.valueOf(retrieverId.substring(6));
                double param=0;
                if(formulaId==3){
                     param = Double.valueOf(retrieverId.substring(8,9)) / 100;
                }
                vsmRetriever.initVSMRetriever(query, formulaId, param, request);
                return vsmRetriever.testRetriever();
            }
            case "probabilityModel": {
                String retrieverId = userRetrieverScore.getProbabilityRetriever();
                List<String> list= Arrays.asList(retrieverId.split("_"));
                double k = Double.valueOf(list.get(2)) / 100;
                double b = Double.valueOf(list.get(3)) / 100;
                probabilityRetrievalService.initRetriever(query,k,b,request);
                return probabilityRetrievalService.testRetriever();
            }
            case "languageModel":
                return null;
            default:
                return null;
        }
    }

}
