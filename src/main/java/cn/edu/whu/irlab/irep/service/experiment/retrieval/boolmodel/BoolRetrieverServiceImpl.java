package cn.edu.whu.irlab.irep.service.experiment.retrieval.boolmodel;


import cn.edu.whu.irlab.irep.base.entity.experiment.InvertedIndex;
import cn.edu.whu.irlab.irep.base.entity.experiment.Retriever;
import cn.edu.whu.irlab.irep.service.experiment.IndexService;
import cn.edu.whu.irlab.irep.service.experiment.preProcess.Impl.PreProcessorServiceImpl;
import cn.edu.whu.irlab.irep.service.experiment.retrieval.BoolRetrieverService;
import cn.edu.whu.irlab.irep.service.experiment.retrieval.RetrievalService;
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

    private List<String> booleanQuery = new ArrayList<>();

    private Set<Integer> resultSet = new TreeSet<>();

    //操作记录
    private List<BoolStepVo> boolStepVoList = new ArrayList<>();

    // 运算符优先级map
    private static final Map<String, Integer> OPT_PRIORITY_MAP = new HashMap<String, Integer>() {
        private static final long serialVersionUID = 6968472606692771458L;

        {
            put("(", 0);
            put("NOT", 1);
            put("AND", 2);
            put("OR", 3);
            put(")", 4);
        }
    };

    @Autowired
    private IndexService indexService;

    @Override
    public void initBoolRetriever(String query, HttpServletRequest request) {
        super.initRetriever(query, request);
        this.booleanQuery = preProcess();
        boolStepVoList.clear();
        super.retriever = new Retriever(true, analyzerName, isRemoveStopWord, "boolModel", 0);
    }

    @Override
    public void initBoolRetriever(String query, String indexType) {
        this.booleanQuery = Arrays.asList(query.split(" "));
        this.booleanQuery = preProcess();
        boolStepVoList.clear();
    }

    @Override
    public List<String> preProcess() {
        List<String> output = new ArrayList<>();
        List<String> booleanQuery = Arrays.asList(query.getContent().split(" "));
        booleanQuery = new ArrayList<>(booleanQuery);

        Iterator iterator = booleanQuery.iterator();
        while (iterator.hasNext()) {
            String s = (String) iterator.next();
            if ("".equals(s)) {
                iterator.remove();
            }
        }

        boolean isRemoved = false;

        for (String s :
                booleanQuery) {
            switch (s) {
                case "AND":
                    if (isRemoved) {
                        isRemoved = false;
                    } else {
                        output.add(s);
                    }
                    break;
                case "OR":
                    if (isRemoved) {
                        isRemoved = false;
                    } else {
                        output.add(s);
                    }
                    break;
                case "NOT":
                    if (isRemoved) {
                        isRemoved = false;
                    } else {
                        output.add(s);
                    }
                    break;
                default: {
                    List<String> temp = PreProcessorServiceImpl.preProcess(s, analyzerName, isRemoveStopWord);
                    if (temp.size() == 1) {
                        output.add(temp.get(0));
                    } else if (temp.size() == 0) {
                        isRemoved = true;
                    } else {
                        output.add("(");
                        for (String s1 :
                                temp) {
                            output.add(s1);
                            output.add("AND");
                        }
                        output.remove(output.size() - 1);
                        output.add(")");
                    }
                }
            }
        }
        if (output.get(output.size() - 1).equals("AND")) {
            output = output.subList(0, output.size() - 1);
        }
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

        //运算符栈
        Stack<String> optStack = new Stack<>();
        //操作数栈
        Stack<BoolVectorVo> itemStack = new Stack<>();

        for (String s :
                booleanQuery) {
            if (s.equals("(") || s.equals("AND") || s.equals("OR") || s.equals("NOT") || s.equals(")")) {
                if (optStack.empty()) {
                    optStack.push(s);
                } else {
                    switch (s) {
                        case "(":
                            optStack.push(s);
                            break;
                        case ")":
                            directCalc(optStack, itemStack, true);
                            break;
                        default:
                            //运算符为AND、OR、NOT
                            compareAndCalc(optStack, itemStack, s);
                            break;
                    }
                }
            } else {
                BoolVectorVo item = new BoolVectorVo(s, termVectorMap.get(s));
                itemStack.push(item);
            }

        }
        if (!optStack.empty()) {
            directCalc(optStack, itemStack, false);
        }
        setResultSet(boolStepVoList.get(boolStepVoList.size() - 1).getResultSet());

        return boolStepVoList;
    }


    //召回文档
    @Override
    public List<ResultVo> calculateSimilarity() {
        booleanOperation();
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


    private void compareAndCalc(Stack<String> optStack, Stack<BoolVectorVo> itemStack, String curOpt) {
        //比较当前运算符和栈顶运算符的优先级
        String peekOpt = optStack.peek();
        int priority = calcPriority(peekOpt, curOpt);
        if (priority > 0) {
            //当前运算符优先级高，直接入栈
            optStack.push(curOpt);
        } else {
            //当前操作符
            String opt = optStack.pop();
            //当前操作数2
            BoolVectorVo item2 = itemStack.pop();
            //当前操纵数1
            BoolVectorVo item1 = itemStack.pop();
            //计算结果作为操作数入栈
            BoolVectorVo result = binaryOperation(opt, item1, item2);
            itemStack.push(result);
            if (optStack.empty()) {
                optStack.push(curOpt);
            } else {
                compareAndCalc(optStack, itemStack, curOpt);
            }
        }
    }

    private int calcPriority(String peekOpt, String curOpt) {
        return OPT_PRIORITY_MAP.get(curOpt) - OPT_PRIORITY_MAP.get(peekOpt);
    }

    private void directCalc(Stack<String> optStack, Stack<BoolVectorVo> itemStack, boolean bracket) {
        //当前操作符
        String opt = optStack.pop();
        //当前操作数2
        BoolVectorVo item2 = itemStack.pop();
        //当前操纵数1
        BoolVectorVo item1 = itemStack.pop();

        BoolVectorVo result = binaryOperation(opt, item1, item2);
        itemStack.push(result);

        if (bracket) {
            if ("(".equals(optStack.peek())) {
                //遇到左括号则停止运算，同时将左括号从栈中移除
                optStack.pop();
            } else {
                directCalc(optStack, itemStack, bracket);
            }
        } else {
            if (!optStack.empty()) {
                //等号类型只要栈中还有运算符就继续运算
                directCalc(optStack, itemStack, bracket);
            }
        }

    }

    //完成一次二元运算操作
    private BoolVectorVo binaryOperation(String opt, BoolVectorVo item1, BoolVectorVo item2) {

        Set<Integer> item1Set = item1.getDocIds();
        Set<Integer> item2Set = item2.getDocIds();
        Set<Integer> resultSet = new TreeSet<>();

        //进行布尔运算
        switch (opt) {
            case "AND":
                resultSet.addAll(item1Set);
                resultSet.retainAll(item2Set);
                break;
            case "OR":
                resultSet.addAll(item1Set);
                resultSet.addAll(item2Set);
                break;
            case "NOT":
                resultSet.addAll(item1Set);
                resultSet.removeAll(item2Set);
                break;
            default:
                break;
        }
        //保存操作记录
        BoolStepVo boolStepVo = new BoolStepVo(opt, item1.getTerm(), item2.getTerm(), item1Set, item2Set, resultSet);
        boolStepVoList.add(boolStepVo);
        BoolVectorVo result = new BoolVectorVo("", resultSet);

        return result;
    }


    private Map<String, Set<Integer>> calculateBoolVector() {
        Map<String, Set<Integer>> output = new HashedMap();
        for (String s :
                booleanQuery) {
            if (s.equals("AND") || s.equals("NOT") || s.equals("OR") || s.equals("(") || s.equals(")")) {
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

    private void setResultSet(Set<Integer> resultSet) {
        this.resultSet = resultSet;
    }
}
