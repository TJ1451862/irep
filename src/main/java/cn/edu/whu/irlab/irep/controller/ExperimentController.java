package cn.edu.whu.irlab.irep.controller;

import cn.edu.whu.irlab.irep.service.preProcess.PreProcessor;
import com.alibaba.fastjson.JSONObject;
import org.apache.lucene.analysis.Analyzer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Controller
@RequestMapping("/experiment")
public class ExperimentController {


    //返回预处理结果
    @RequestMapping("/preProcess")
    public String preProcessController(@RequestParam(name = "token")String token,@RequestParam(name = "analyzerName")String analyzerName,@RequestParam(name = "isRemoveStopWord")boolean isRemoveStopWord){
        Analyzer analyzer=PreProcessor.analyzerSelector(analyzerName);
        ArrayList<String> termList=PreProcessor.preProcess(token,analyzer,isRemoveStopWord);
        String string=termList.toString();
        return string;
    }

    //返回索引结果
    public JSONObject createIndexController(@RequestParam(name = "analyzerName")String analyzerName,@RequestParam(name = "isRemoveStopWord")boolean isRemoveStopWord){
        JSONObject index=new JSONObject();

        return index;
    }

}
