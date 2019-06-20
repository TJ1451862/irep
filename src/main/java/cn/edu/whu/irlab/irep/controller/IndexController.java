package cn.edu.whu.irlab.irep.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author fangrf
 * @version 1.0
 * @date 2018-10-30 9:50
 * @desc 页面跳转控制层
 **/
@Controller

public class IndexController {

    @RequestMapping(value = "/index.html")
    public String index(){
        return "index";
    }

    @RequestMapping(value = "IRforCN.html")
    public String irForCnController(){
        return "IRforCN";
    }

    @RequestMapping(value = "IRforEN.html")
    public String irForEnController(){
        return "IRforEN";
    }

    @RequestMapping(value = "NLP.html")
    public String nlpController(){
        return "NLP";
    }

    @RequestMapping(value = "explanation.html")
    public String explanationController(){
        return "explanation";
    }

    @RequestMapping(value = "IRforCN/invertedindex.html")
    public String iicnController(){
        return "IRforCN/invertedindex";
    }

    @RequestMapping(value = "IRforCN/performance.html")
    public String performanceCnController(){
        return "IRforCN/performance";
    }

    @RequestMapping(value = "IRforCN/preprocessing.html")
    public String preprocessingCnController(){
        return "IRforCN/preprocessing";
    }

    @RequestMapping(value = "IRforCN/informationretrieval/boolean.html")
    public String booleanCnController(){
        return "IRforCN/informationretrieval/boolean";
    }


    @RequestMapping(value = "IRforCN/informationretrieval/languageModel.html")
    public String languageModelCnController(){
        return "IRforCN/informationretrieval/languageModel";
    }

    @RequestMapping(value = "IRforCN/informationretrieval/probabilityModel.html")
    public String probabilityModelCnController(){
        return "IRforCN/informationretrieval/probabilityModel";
    }

    @RequestMapping(value = "IRforCN/informationretrieval/vectorSpaceModel.html")
    public String vectorSpaceModelModelCnController(){
        return "IRforCN/informationretrieval/vectorSpaceModel";
    }

    @RequestMapping(value = "IRforEN/invertedindex.html")
    public String iienController(){
        return "IRforEN/invertedindex";
    }

    @RequestMapping(value = "IRforEN/performance.html")
    public String performanceEnController(){
        return "IRforEN/performance";
    }

    @RequestMapping(value = "IRforEN/preprocessing.html")
    public String preprocessingEnController(){
        return "IRforEN/preprocessing";
    }

    @RequestMapping(value = "IRforEN/informationretrieval/boolean.html")
    public String booleanEnController(){
        return "IRforEN/informationretrieval/boolean";
    }


    @RequestMapping(value = "IRforEN/informationretrieval/languageModel.html")
    public String languageModelEnController(){
        return "IRforEN/informationretrieval/languageModel";
    }

    @RequestMapping(value = "IRforEN/informationretrieval/probabilityModel.html")
    public String probabilityModelEnController(){
        return "IRforEN/informationretrieval/probabilityModel";
    }

    @RequestMapping(value = "IRforEN/informationretrieval/vectorSpaceModel.html")
    public String vectorSpaceModelModelEnController(){
        return "IRforEN/informationretrieval/vectorSpaceModel";
    }

    @RequestMapping(value = "NLP/LexicalAnalysis.html")
    public String LexicalAnalysisController(){
        return "NLP/LexicalAnalysis";
    }

    @RequestMapping(value = "NLP/NamedEntityRecognition.html")
    public String NamedEntityRecognitionController(){
        return "NLP/NamedEntityRecognition";
    }

    @RequestMapping(value = "NLP/POSTagging.html")
    public String POSTaggingController(){
        return "NLP/POSTagging";
    }


}
