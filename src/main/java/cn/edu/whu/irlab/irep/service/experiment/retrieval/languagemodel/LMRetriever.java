package cn.edu.whu.irlab.irep.service.experiment.retrieval.languagemodel;

import cn.edu.whu.irlab.irep.base.entity.experiment.FullIndex;
import cn.edu.whu.irlab.irep.base.entity.experiment.InvertedIndex;
import cn.edu.whu.irlab.irep.base.dao.experiment.impl.FullIndexServiceImpl;
import cn.edu.whu.irlab.irep.base.dao.experiment.impl.InvertedIndexServiceImpl;
import cn.edu.whu.irlab.irep.base.entity.experiment.Retriever;
import cn.edu.whu.irlab.irep.base.entity.system.User;
import cn.edu.whu.irlab.irep.base.entity.system.UserRetrieverScore;
import cn.edu.whu.irlab.irep.service.experiment.IndexService;
import cn.edu.whu.irlab.irep.service.experiment.retrieval.LMRetrieverService;
import cn.edu.whu.irlab.irep.service.experiment.retrieval.RetrievalService;
import cn.edu.whu.irlab.irep.service.util.Constructor;
import cn.edu.whu.irlab.irep.service.util.Find;
import cn.edu.whu.irlab.irep.service.vo.IdfVo;
import cn.edu.whu.irlab.irep.service.vo.ResultVo;
import cn.edu.whu.irlab.irep.service.vo.SearchResultVo;
import cn.edu.whu.irlab.irep.service.vo.TfVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author Jane
 * @version 1.0
 * @date 2019-07-13 15:57
 * @desc 语言模型检索器
 */
@Service
public class LMRetriever extends RetrievalService implements LMRetrieverService {

    @Autowired
    public FullIndexServiceImpl fullIndexService;

    @Autowired
    public InvertedIndexServiceImpl invertedIndexService;

    private double smoothParam;//平滑系数

    private int totalArticles = 166;//总共文本数量;

    //idf值 词典中各词项在所有文档中出现的频率
    private List<IdfVo> idfVoList;


    @Autowired
    private IndexService indexService;


    public void initLMRetriever(String query, double smoothParam, HttpServletRequest request) {
        this.smoothParam = smoothParam;
        super.initRetriever(query,request);
        super.retriever=new Retriever(true,analyzerName,isRemoveStopWord,"languageModel",
                0,"smoothParam",(int)(smoothParam*100));
        this.idfVoList = calculateDf();
    }

    public List<IdfVo> calculateDf() {
        List<FullIndex> fullIndexList = indexService.selectFullIndex();
        List<IdfVo> idfVoList = new ArrayList<>();
        int num = 0;
        for (FullIndex i :
                fullIndexList) {
            IdfVo idfVo = new IdfVo(i.getTerm(), num, i.getDf() / (double) totalArticles);
            idfVoList.add(idfVo);
            num++;
        }
        return idfVoList;
    }

    @Override
    public List<TfVo> calculateLM(int docId) {
        int docLength = indexService.selectDocLength(docId);
        List<TfVo> tfVos = indexService.selectDocTf(docId);
        for (TfVo t :
                tfVos) {
            t.setTf(t.getTf() / docLength);
        }
        return tfVos;
    }

    @Override
    public List<ResultVo> calculateSimilarity() {
        List<ResultVo> resultVos=new ArrayList<>();
        for (int i = 0; i < totalArticles; i++) {
            String title = Find.findTitle(i, true);
            Double GP = 1.0;
            for (String term :
                    query.getPreProcessResult()) {
                List<TfVo> lmVos = calculateLM(i);
                double a=0;
                double b=0;
                for (IdfVo idf :
                        idfVoList) {
                    if (term.equals(idf.getTerm())) {
                        a = idf.getIdf();
                    }
                }
                for (TfVo lm :
                        lmVos) {
                    if (term.equals(lm.getTerm())) {
                        b = lm.getTf();
                    }
                }
                GP *= (1 - smoothParam) * a + (smoothParam) * b;
            }
            ResultVo resultVo=new ResultVo(i,title,GP);
            resultVos.add(resultVo);
        }
        return resultVos;
    }

    @Override
    public int quit() {
        UserRetrieverScore userRetrieverScore=new UserRetrieverScore();
        User user=(User) session.getAttribute("user");
        userRetrieverScore.setUserId(user.getId());
        userRetrieverScore.setLanugaeRetriever(retriever.getRetrieverId());
        return userRetrieverScoreService.updateByUserId(userRetrieverScore);
    }
}
