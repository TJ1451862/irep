package cn.edu.whu.irlab.irep.controller.system;

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

    /**
     * 首页设置
     * @return 首页
     */
    @RequestMapping("/")
    public String login() {
        return "login";
    }

    @RequestMapping("/signIn1.html")
    public String login1Controller() {
        return "signIn";
    }

    @RequestMapping("/login.html")
    public String loginController() {
        return "login";
    }

    @RequestMapping(value = "/signIn.html")
    public String signIn(){
        return "signIn";
    }

    @RequestMapping(value = "/index.html")
    public String index() {
        return "index";
    }

    @RequestMapping(value = "IRforCN.html")
    public String irForCnController() {
        return "IRforCN";
    }

    @RequestMapping(value = "IRforEN.html")
    public String irForEnController() {
        return "IRforEN";
    }

    @RequestMapping(value = "NLP.html")
    public String nlpController() {
        return "NLP";
    }

    @RequestMapping(value = "explanation.html")
    public String explanationController() {
        return "explanation";
    }

    @RequestMapping(value = "IRforCN/invertedIndex.html")
    public String iicnController() {
        return "IRforCN/invertedIndex";
    }

    @RequestMapping(value = "IRforCN/performance.html")
    public String performanceCnController() {
        return "IRforCN/performance";
    }

    @RequestMapping(value = "IRforCN/preProcessing.html")
    public String preProcessingCnController() {
        return "IRforCN/preProcessing";
    }

    @RequestMapping(value = "IRforCN/Retrieval/boolean.html")
    public String booleanCnController() {
        return "IRforCN/Retrieval/boolean";
    }


    @RequestMapping(value = "IRforCN/Retrieval/languageModel.html")
    public String languageModelCnController() {
        return "IRforCN/Retrieval/languageModel";
    }

    @RequestMapping(value = "IRforCN/Retrieval/probabilityModel.html")
    public String probabilityModelCnController() {
        return "IRforCN/Retrieval/probabilityModel";
    }

    @RequestMapping(value = "IRforCN/Retrieval/vectorSpaceModel.html")
    public String vectorSpaceModelModelCnController() {
        return "IRforCN/Retrieval/vectorSpaceModel";
    }

    @RequestMapping(value = "IRforEN/invertedIndex.html")
    public String iienController() {
        return "IRforEN/invertedIndex";
    }

    @RequestMapping(value = "IRforEN/performance.html")
    public String performanceEnController() {
        return "IRforEN/performance";
    }

    @RequestMapping(value = "IRforEN/preProcessing.html")
    public String preProcessingEnController() {
        return "IRforEN/preProcessing";
    }

    @RequestMapping(value = "IRforEN/Retrieval/boolean.html")
    public String booleanEnController() {
        return "IRforEN/Retrieval/boolean";
    }


    @RequestMapping(value = "IRforEN/Retrieval/languageModel.html")
    public String languageModelEnController() {
        return "IRforEN/Retrieval/languageModel";
    }

    @RequestMapping(value = "IRforEN/Retrieval/probabilityModel.html")
    public String probabilityModelEnController() {
        return "IRforEN/Retrieval/probabilityModel";
    }

    @RequestMapping(value = "IRforEN/Retrieval/vectorSpaceModel.html")
    public String vectorSpaceModelModelEnController() {
        return "IRforEN/Retrieval/vectorSpaceModel";
    }

    @RequestMapping(value = "NLP/LexicalAnalysis.html")
    public String LexicalAnalysisController() {
        return "NLP/LexicalAnalysis";
    }

    @RequestMapping(value = "NLP/NamedEntityRecognition.html")
    public String NamedEntityRecognitionController() {
        return "NLP/NamedEntityRecognition";
    }

    @RequestMapping(value = "NLP/POSTagging.html")
    public String POSTaggingController() {
        return "NLP/POSTagging";
    }


}
