package cn.edu.whu.irlab.irep.service.experiment.retrieval.boolmodel;


import cn.edu.whu.irlab.irep.base.entity.InvertedIndex;
import cn.edu.whu.irlab.irep.service.experiment.creatIndex.IndexServiceImpl;
import cn.edu.whu.irlab.irep.service.util.Constructor;
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
public class BoolRetriever {

    @Autowired
    public IndexServiceImpl invertedIndexService;


    private String indexType;//索引类型

    private String analyzerName;//分词器名称

    private boolean isRemoveStopWord;//是否去停用词

    private List<String> anslist1;//初步存储term

    private List<String> anslist2;//初步存储操作符

    private List<String> operators ;//逻辑操作符

    private List<String> terms;//分词后的检索词

    private List<Integer> idresults;//文档id的结果

    private List<TermsForBool> termsFormap;//term对应的文档id

    private List<ResultForBool> docresults;//文档的结果

    private  String finalquery;


    public void initBoolRetriever(String[] termarray, String[] operatorarray,HttpServletRequest request){
        this.indexType = Constructor.indexTypeConstructor(request);
        this.analyzerName = (String) request.getSession().getAttribute("analyzer");
        this.isRemoveStopWord = (boolean)request.getSession().getAttribute("removeStopWord");
        this.anslist1=Arrays.asList(termarray);
        this.anslist2=Arrays.asList(operatorarray);
    }

    public void search() {
        //初始化
        finalquery=null;
        operators.clear();
        terms.clear();
        termsFormap.clear();
        idresults.clear();
        docresults.clear();



        getfinallists();
        getboolpreproess();
        getboolvector();
        getboolcalculate();
        getresult();

    }

    /**
     * 得到简单的检索式
     */
    public String geteasyquary(){
        String easyquary = null;
        for(int i=0;i<anslist1.size();i++){
            easyquary=easyquary+anslist1.get(i);
            if(i<anslist2.size())
                easyquary=easyquary+anslist2.get(i);
        }
        return easyquary;
    }


    /**
     * 将query分解,将term存储在terms里，操作符存储在operators里
     */
    public void getfinallists(){
        for(int i=0;i<anslist1.size();i++){
            QueryForBool query1=new QueryForBool(anslist1.get(0), analyzerName, isRemoveStopWord);
            List<String> fcquery = new ArrayList<>();
            fcquery=query1.getPreProcessResult();
            for(int j=0;j<fcquery.size();j++){
                terms.add(fcquery.get(j));
                    if(j!=fcquery.size()-1)
                    operators.add("and");
                }
            if(i<anslist2.size())
                operators.add(anslist2.get(i));
            }
    }



    /**
     * 文档预处理的结果
     */
    public void getboolpreproess(){
        finalquery=terms.get(0);
        if(!operators.isEmpty()){
            for(int i=0;i<operators.size();i++){
                finalquery=finalquery+operators.get(i);
                finalquery=finalquery+terms.get(i+1);
            }
        }
    }


    /**
     * 计算布尔向量的结果,得到terms对应的文档id
     */
    public void getboolvector(){
        Map<String, List<Integer>> theMap = new HashMap<>();
        for(int i=0;i<terms.size();i++){
            List<InvertedIndex> iindex = invertedIndexService.selectInvertedIndex(terms.get(i));
            List<Integer> ids = new ArrayList<Integer>();
            for(int j=0;j<iindex.size();j++){
                int idnum = iindex.get(j).getDocId();
                if(!ids.contains(idnum)){
                    ids.add(idnum);
                }
            }
            termsFormap.add(new TermsForBool(terms.get(i),ids));
        }

    }

    /**
     * 进行布尔运算
     */
    public void getboolcalculate(){
        idresults.addAll(termsFormap.get(0).getTerm_id());
        if(!operators.isEmpty()){
            for(int i=0;i<operators.size();i++){
                if(operators.get(i).equals("and")){
                    idresults.retainAll(termsFormap.get(i+1).getTerm_id());
                }
                else if(operators.get(i).equals("or")){
                    idresults.addAll(termsFormap.get(i+1).getTerm_id());
                    idresults = removeDuplicate(idresults);
                }
                else {
                    idresults.removeAll(termsFormap.get(i+1).getTerm_id());
                }
            }
        }
    }


    /**
     * 输出最终结果
     */
    public void getresult(){
        for(int i=0;i<idresults.size();i++){
            ResultForBool resultforbool = new ResultForBool(idresults.get(i));
            docresults.add(resultforbool);
        }

    }

    /**
     * 去重
     */
    private List<Integer> removeDuplicate ( List<Integer> list ) {
        List<Integer> unique = new ArrayList<Integer>();
        for ( int i=0; i<list.size(); i++ ) {
            if ( !unique.contains(list.get(i)) ) unique.add(list.get(i));
        }

        return unique;
    }

    /**
     * get方法
     */


    public String getFinalquery() {
        return finalquery;
    }

    public List<String> getTerms() {
        return terms;
    }

    public List<TermsForBool> getterm_id(){
        return termsFormap;
    }

    public List<Integer> getIdResult(){
        return idresults;
    }

    public List<ResultForBool> getDocResult(){
        return docresults;
    }



}
