package cn.edu.whu.irlab.irep.service.experiment.retrieval.boolmodel;


import cn.edu.whu.irlab.irep.base.entity.experiment.InvertedIndex;
import cn.edu.whu.irlab.irep.service.experiment.IndexService;
import cn.edu.whu.irlab.irep.service.experiment.preProcess.Impl.PreProcessorServiceImpl;
import cn.edu.whu.irlab.irep.service.experiment.retrieval.BoolRetrieverService;
import cn.edu.whu.irlab.irep.service.experiment.retrieval.RetrievalService;
import cn.edu.whu.irlab.irep.service.util.Constructor;
import cn.edu.whu.irlab.irep.service.util.Find;
import cn.edu.whu.irlab.irep.service.vo.BoolStepVo;
import cn.edu.whu.irlab.irep.service.vo.BoolVectorVo;
import cn.edu.whu.irlab.irep.service.vo.ResultVo;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author chr
 * @version 1.0
 * @date 2019-07-26 13：56
 * @desc 布尔模型检索器
 */

@Service
public class BoolRetrieverServiceImpl extends RetrievalService implements BoolRetrieverService {

    private String indexType;//索引类型

    private String analyzerName;//分词器名称

    private boolean isRemoveStopWord;//是否去停用词

    private List<String> booleanQuery = new ArrayList<>();

    private Set<Integer> resultSet = new TreeSet<>();

    @Autowired
    private IndexService indexService;

    @Override
    public void initBoolRetriever(List<String> booleanQuery, HttpServletRequest request) {
        this.indexType = Constructor.indexTypeConstructor(request);
        this.analyzerName = (String) request.getSession().getAttribute("analyzer");
        this.isRemoveStopWord = (boolean) request.getSession().getAttribute("removeStopWord");
        this.booleanQuery = preProcess(booleanQuery);
    }

    @Override
    public List<String> preProcess(List<String> booleanQuery) {
        List<String> output = new ArrayList<>();
        for (String s :
                booleanQuery) {
            switch (s) {
                case "AND":
                    output.add(s);
                    break;
                case "OR":
                    output.add(s);
                    break;
                case "NOT":
                    output.add(s);
                    break;
                default:
                    List<String> temp = PreProcessorServiceImpl.preProcess(s, analyzerName, isRemoveStopWord);
                    for (String s1 :
                            temp) {
                        output.add(s1);
                        output.add("AND");
                    }
            }
        }
        if (output.get(-1).equals("AND")) {
            output = output.subList(0, output.size() - 2);
        }
        this.booleanQuery = booleanQuery;
        return output;
    }

    @Override
    public List<BoolVectorVo> outputBoolVector() {
        List<BoolVectorVo> output = new ArrayList<>();
        Map<String, Set<Integer>> termVectorMap = calculateBoolVector();
        for (Map.Entry<String, Set<Integer>> e :
                termVectorMap.entrySet()) {
            BoolVectorVo boolVectorVo = new BoolVectorVo(e.getKey(), e.getValue());
            output.add(boolVectorVo);
        }
        return output;
    }

    @Override
    public List<BoolStepVo> booleanOperation() {
        Map<String, Set<Integer>> termVectorMap = calculateBoolVector();
        String operator = "";
        Set<Integer> preStep = null;
        Set<Integer> temp = null;
        List<BoolStepVo> boolStepVoList = new ArrayList<>();
        for (String s :
                booleanQuery) {
            if (s.equals("AND") || s.equals("NOT") || s.equals("OR")) {
                operator = s;
            } else {
                Set<Integer> cur = termVectorMap.get(s);

                switch (operator) {
                    case "AND":
                        temp.clear();
                        temp.addAll(preStep);
                        temp.retainAll(cur);
                        break;
                    case "OR":
                        temp.clear();
                        temp.addAll(preStep);
                        temp.addAll(cur);
                        break;
                    case "NOT":
                        temp.clear();
                        temp.addAll(preStep);
                        temp.removeAll(cur);
                        break;
                    default:
                        preStep = termVectorMap.get(s);
                }
                BoolStepVo boolStepVo = new BoolStepVo();
                boolStepVo.setOperator(operator);
                boolStepVo.setPerStep(preStep);
                boolStepVo.setTermSet(cur);
                boolStepVo.setTerm(s);
                boolStepVoList.add(boolStepVo);

                preStep = temp;
            }
        }
        resultSet = preStep;
        return boolStepVoList;
    }

    //召回文档
    @Override
    public List<ResultVo> calculateSimilarity() {
        List<ResultVo> output = new ArrayList<>();
        for (Integer i :
                resultSet) {
            ResultVo resultVo = new ResultVo(i, Find.findTitle(i, true), 0);
            output.add(resultVo);
        }
        return output;
    }

    @Override
    public int quit() {
        return 0;
    }

    private Map<String, Set<Integer>> calculateBoolVector() {
        Map<String, Set<Integer>> output = new HashedMap();
        for (String s :
                booleanQuery) {
            if (s.equals("AND") || s.equals("NOT") || s.equals("OR")) {
            } else {
                if (!output.containsKey(s)) {
                    List<InvertedIndex> invertedIndices = indexService.selectInvertedIndex(s);
                    Set<Integer> vector = new TreeSet<>();
                    for (InvertedIndex i :
                            invertedIndices) {
                        vector.add(i.getDocId());
                    }
                    output.put(s, vector);
                }
            }
        }
        return output;
    }
}
