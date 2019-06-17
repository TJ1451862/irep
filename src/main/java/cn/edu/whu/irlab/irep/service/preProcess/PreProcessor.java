package cn.edu.whu.irlab.irep.service.preProcess;


import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;

/**
 * @author gcr
 * @version 1.0
 * @date 2019-06-13 18:54
 * @desc 中文预处理器
 **/

@Service
public class PreProcessor {

    public ArrayList<String> preProcess(String token,Analyzer analyzer,boolean isRemoveStopWord){
        ArrayList<String> result=new ArrayList<>();
        String terms;
        if (isRemoveStopWord){
            terms=outputAnalyzer(token,analyzer);
            result=removePunctuation(terms);
            result=removeStopWords(result);
        }else {
            terms=outputAnalyzer(token,analyzer);
            result=removePunctuation(terms);
        }
        return result;
    }

    //分词
    public String outputAnalyzer(String token, Analyzer analyzer){

        StringBuilder stringBuilder =new StringBuilder();
        StringReader reader=new StringReader(token);
        try{
            TokenStream tokenStream=analyzer.tokenStream(token,reader);
            tokenStream.reset();
            while (tokenStream.incrementToken()){
                CharTermAttribute charTermAttribute =tokenStream.getAttribute(CharTermAttribute.class);
                stringBuilder.append(charTermAttribute.toString());
                stringBuilder.append(" ");
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        analyzer.close();
        String outPut=String.valueOf(stringBuilder);
        System.out.println("分词完成");
        return outPut;
    }

    //去标点
    public ArrayList<String> removePunctuation(String seg){
        String splits = seg.replaceAll("\\pP|\\pS|\\pM|\\pN|\\pC", "");// 去除标点符号

        splits = splits.replaceAll(" "," " );
        splits = splits.replaceAll(" "," " );
        splits = splits.replaceAll("  "," " );
        splits = splits.replaceAll("ą"," " );
        splits = splits.replaceAll("ȁ"," " );
        splits = splits.replaceAll("É"," " );
        splits = splits.replaceAll("Ï"," " );
        splits = splits.replaceAll("丶"," " );
        splits = splits.replaceAll("[a-zA-Z]","" );//去英文

        String[] sWords = splits.split(" ");
        ArrayList<String> termList = new ArrayList();
        for (int i = 0; i < sWords.length; i++) {
            String word=sWords[i];
            if (word==null||word.equals("")||word.equals(" ") || word.equals("  ")){}
            else {
                word = word.trim();
                word = word.replace("  ", "");
                termList.add(word);
            }
        }
        System.out.println( "去标点完成");
        return termList;
    }

    //去停用词
    public ArrayList<String> removeStopWords(ArrayList<String> termList){
        ArrayList<String> terms = termList;
        String dataDir = null;
        try{
            dataDir = "resources/StopWordTable.txt";
            BufferedReader br1 = new BufferedReader(new InputStreamReader(new FileInputStream(dataDir), "UTF-8"));//构造一个BufferedReader类来读停用词表
            String string1 = null;
            ArrayList<String> stopWord = new ArrayList();
            while ((string1 = br1.readLine()) != null) {//使用readLine方法，一次读一行 读取停用词
                stopWord.add(string1);
            }
            br1.close();
            terms.removeAll(stopWord);//去停用词
        }catch (IOException e){
            e.printStackTrace();
        }
        System.out.println("去停用词完成");
        return terms;
    }

    //筛选分词器
    public Analyzer analyzerSelector(String analyzerName){
        Analyzer analyzer=null;
        switch (analyzerName){
            case "standard":
                analyzer=new StandardAnalyzer();
                break;
            case "whitespace":
                analyzer=new WhitespaceAnalyzer();
                break;
            case "simple":
                analyzer=new SimpleAnalyzer();
                break;
            case "CJK":
                analyzer=new CJKAnalyzer();
                break;
            case "smartChinese":
                analyzer=new SmartChineseAnalyzer();
                break;
            default:break;
        }
        return analyzer;
    }
}
