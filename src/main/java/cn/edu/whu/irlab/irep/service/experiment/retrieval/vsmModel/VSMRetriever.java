package cn.edu.whu.irlab.irep.service.experiment.retrieval.vsmModel;

import cn.edu.whu.irlab.irep.base.dao.experiment.DocumentService;
import cn.edu.whu.irlab.irep.base.entity.experiment.FullIndex;
import cn.edu.whu.irlab.irep.base.entity.experiment.Retriever;
import cn.edu.whu.irlab.irep.base.entity.system.User;
import cn.edu.whu.irlab.irep.base.entity.system.UserRetrieverScore;
import cn.edu.whu.irlab.irep.service.experiment.retrieval.RetrievalService;
import cn.edu.whu.irlab.irep.service.experiment.retrieval.VsmRetrievalService;
import cn.edu.whu.irlab.irep.service.experiment.IndexService;
import cn.edu.whu.irlab.irep.service.vo.*;
import cn.edu.whu.irlab.irep.service.util.Calculator;
import cn.edu.whu.irlab.irep.service.util.Find;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author gcr
 * @version 1.0
 * @date 2019-06-18 23:36
 * @desc 向量空间模型检索器
 */
@Service
public class VSMRetriever extends RetrievalService implements VsmRetrievalService {

    @Autowired
    private IndexService indexService;

    private int formulaID;//公式ID

    private double smoothParam;//平滑系数

    //idf值
    private List<IdfVo> idfVoList;

    @Override
    public void initVSMRetriever(String queryContent,
                                 int formulaID,
                                 Double smoothParam,
                                 HttpServletRequest request) {
        this.formulaID = formulaID;
        this.smoothParam = smoothParam;
        super.initRetriever(queryContent,request);
        if(formulaID==3&&smoothParam!=null){
            super.retriever = new Retriever(true, analyzerName, isRemoveStopWord, "vsm", formulaID, "平滑系数", (int) (smoothParam * 100), "", null);
        }else {
            super.retriever = new Retriever(true, analyzerName, isRemoveStopWord, "vsm", formulaID);
        }
        setIdfVoList(calculateIdf());
    }

    @Override
    public List<IdfVo> calculateIdf() {
        List<FullIndex> fullIndexList = indexService.selectFullIndex();
        List<IdfVo> idfVoList = new ArrayList<>();
        int N = fullIndexList.size();
        int num = 0;
        for (FullIndex i :
                fullIndexList) {
            IdfVo idfVo = new IdfVo(i.getTerm(), num, Math.log((double) N / i.getDf()));
            idfVoList.add(idfVo);
            num++;
        }
        return idfVoList;
    }

    @Override
    public List<TfVo> calculateTfOfQuery() {
        return Calculator.calculateTF1(query.getTf(), formulaID, smoothParam);
    }

    @Override
    public List<TfVo> calculateTfOfDoc(int docId) {
        return Calculator.calculateTF1(indexService.selectDocTf(docId), formulaID, smoothParam);
    }

    @Override
    public List<VectorIVo> calculateVectorOfQuery() {
        List<TfVo> tfVosOfQuery = calculateTfOfQuery();
        return calculateVector(tfVosOfQuery);
    }

    @Override
    public List<VectorIVo> calculateVectorOfDoc(int docId) {
        List<TfVo> tfVosOfDoc = indexService.selectDocTf(docId);
        return calculateVector(tfVosOfDoc);
    }

    @Override
    public List<ResultVo> calculateSimilarity() {
        List<ResultVo> results = new ArrayList<>();
        List<VectorIVo> vectorOfQuery = calculateVectorOfQuery();
        double moduleOfQueryVector = Calculator.calculateModule(vectorOfQuery);
        for (int i = 0; i < 166; i++) {
            double similarity = 0;
            List<VectorIVo> vectorOfDoc = calculateVectorOfDoc(i);
            double moduleOfDocVector = Calculator.calculateModule(vectorOfDoc);
            for (VectorIVo q :
                    vectorOfQuery) {
                for (VectorIVo d :
                        vectorOfDoc) {
                    if (q.getNum() == d.getNum()) {
                        similarity += q.getValue() * d.getValue();
                    }
                }
            }
            similarity = similarity / (moduleOfDocVector * moduleOfQueryVector);
            if (similarity != 0) {
                ResultVo resultVo = new ResultVo(i, Find.findTitle(i, true), similarity);
                results.add(resultVo);
            }
        }
        return results;
    }

    private List<VectorIVo> calculateVector(List<TfVo> tfVoList) {
        List<VectorIVo> vector = new ArrayList<>();
        for (TfVo tf :
                tfVoList) {
            VectorIVo vectorIVo = new VectorIVo(tf.getTerm(), 0, 0);
            for (IdfVo idf :
                    idfVoList) {
                if (idf.getTerm().equals(tf.getTerm())) {
                    vectorIVo.setNum(idf.getNum());
                    vectorIVo.setValue(idf.getIdf() * tf.getTf());
                }
            }
            vector.add(vectorIVo);
        }
        return vector;
    }

    @Override
    public int quit() {
        UserRetrieverScore userRetrieverScore=new UserRetrieverScore();
        User user=(User) session.getAttribute("user");
        userRetrieverScore.setUserId(user.getId());
        userRetrieverScore.setVsmRetriever(retriever.getRetrieverId());
        return userRetrieverScoreService.updateByUserId(userRetrieverScore);
    }

    /*
    以下为get和set方法
     */

    public List<IdfVo> getIdfVoList() {
        return idfVoList;
    }

    public void setIdfVoList(List<IdfVo> idfVoList) {
        this.idfVoList = idfVoList;
    }

    public int getFormulaID() {
        return formulaID;
    }

    public void setFormulaID(int formulaID) {
        this.formulaID = formulaID;
    }

    public double getSmoothParam() {
        return smoothParam;
    }

    public void setSmoothParam(double smoothParam) {
        this.smoothParam = smoothParam;
    }
}
