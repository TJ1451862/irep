package cn.edu.whu.irlab.irep.controller;

import cn.edu.whu.irlab.irep.service.preProcess.PreProcessor;
import cn.edu.whu.irlab.irep.service.util.Find;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

/**
 * @author gcr
 * @version 1.0
 * @date 2019-06-19 20:34
 * @desc 中文文档预处理交互层
 **/
@RestController
@RequestMapping("IRforCN/preProcessing")
public class PreprocessorController {


    /**
     * 中文预处理控制层
     *
     * @param token            待处理字符串
     * @param analyzerName     分词器名称
     * @param removeStopWord 是否去停用词
     * @return 预处理结果
     */
    @RequestMapping("/preProcess")
    public String preProcessController(@RequestParam(name = "token") String token,
                                       @RequestParam(name = "analyzerName") String analyzerName,
                                       @RequestParam(name = "isRemoveStopWord") boolean removeStopWord,
                                       HttpServletRequest request) {
        ArrayList<String> termList = PreProcessor.preProcess(token, analyzerName, removeStopWord);
        String string = termList.toString();
        request.getSession().setAttribute("analyzer", analyzerName);
        request.getSession().setAttribute("removeStopWord", removeStopWord);
        return string;
    }

    /**
     * 返回文章
     *
     * @param docId 文章id
     * @return
     */
    @RequestMapping("/getDoc")
    public String selectDoc(@RequestParam(name = "docId") int docId) {
        return Find.findDoc(docId, true);
    }


}
