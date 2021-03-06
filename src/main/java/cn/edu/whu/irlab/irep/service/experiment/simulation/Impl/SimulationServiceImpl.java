package cn.edu.whu.irlab.irep.service.experiment.simulation.Impl;

import cn.edu.whu.irlab.irep.base.dao.system.UserRetrieverScoreService;
import cn.edu.whu.irlab.irep.base.entity.system.User;
import cn.edu.whu.irlab.irep.base.entity.system.UserRetrieverScore;
import cn.edu.whu.irlab.irep.service.experiment.retrieval.*;
import cn.edu.whu.irlab.irep.service.experiment.simulation.SimulationService;
import cn.edu.whu.irlab.irep.service.vo.SearchResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author gcr19
 * @date 2019-08-08 07:40
 * @desc
 **/
@Service
public class SimulationServiceImpl implements SimulationService {


    @Autowired
    private UserRetrieverScoreService userRetrieverScoreService;

    @Autowired
    private BoolRetrieverService boolRetrieverService;

    @Autowired
    private VsmRetrievalService vsmRetrievalService;

    @Autowired
    private ProbabilityRetrievalService probabilityRetrievalService;

    @Autowired
    private LMRetrieverService lmRetrieverService;

    @Override
    public int selectModel(String modelName, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int userId = user.getId();
        UserRetrieverScore userRetrieverScore = userRetrieverScoreService.selectByUserId(userId);
        String retrieverId;
        switch (modelName) {
            case "boolModel": {
                retrieverId = "1010_10";
                break;
            }
            case "vsm": {
                retrieverId = userRetrieverScore.getVsmRetriever();
                break;
            }
            case "probabilityModel": {
                retrieverId = userRetrieverScore.getProbabilityRetriever();
                break;
            }
            case "languageModel": {
                retrieverId = userRetrieverScore.getLanugaeRetriever();
                break;
            }
            default:
                return 0;
        }
        session.setAttribute("SimulatedId", retrieverId);
        return 1;
    }

    @Override
    public List<SearchResultVo> search(String query, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String retrieverId = (String) session.getAttribute("SimulatedId");

        List<String> retrieverParam = Arrays.asList(retrieverId.split("_"));
        retrieverParam = new ArrayList<>(retrieverParam);
        switch (retrieverParam.get(1).getBytes()[0]) {
            case '1': {
                boolRetrieverService.initBoolRetriever(query, request);
                return boolRetrieverService.search();
            }
            case '2': {
                int formulaId = Integer.valueOf(retrieverId.substring(6));
                double param = 0;
                if (formulaId == 3) {
                    param = Double.valueOf(retrieverId.substring(8, 9)) / 100;
                }
                vsmRetrievalService.initVSMRetriever(query, formulaId, param, request);
                return vsmRetrievalService.search();
            }
            case '3': {
                List<String> list = Arrays.asList(retrieverId.split("_"));
                double k = Double.valueOf(list.get(2)) / 100;
                double b = Double.valueOf(list.get(3)) / 100;
                probabilityRetrievalService.initRetriever(query, k, b, request);
                return probabilityRetrievalService.search();
            }
            case '4': {
                List<String> list = Arrays.asList(retrieverId.split("_"));
                double smoothParam = Double.valueOf(list.get(2)) / 100;
                lmRetrieverService.initLMRetriever(query, smoothParam, request);
                return lmRetrieverService.search();
            }
            default:
                return null;
        }
    }
}
