package cn.edu.whu.irlab.irep.controller.experiment;

import cn.edu.whu.irlab.irep.service.experiment.preProcess.PreProcessor;
import cn.edu.whu.irlab.irep.service.util.Find;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @PostMapping("/preProcess")
    public List<String> preProcessController(@RequestParam(name = "token") String token,
                                             @RequestParam(name = "analyzerName") String analyzerName,
                                             @RequestParam(name = "isRemoveStopWord") boolean removeStopWord,
                                             HttpServletRequest request) {
        List<String> termList = PreProcessor.preProcess(token, analyzerName, removeStopWord);
        request.getSession().setAttribute("analyzer", analyzerName);
        request.getSession().setAttribute("removeStopWord", removeStopWord);

        return termList;
    }

    /**
     * 返回文章
     *
     * @param docId 文章id
     * @return
     */
    @PostMapping("/getDoc")
    public Map<String,String> selectDoc(@RequestParam(name = "docId") int docId) {
        Map<String,String> map=new HashMap<>();
        map.put("docId",String.valueOf(docId));
        map.put("content",Find.findDoc(docId, true));
        return map;
    }


}
