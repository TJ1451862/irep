package cn.edu.whu.irlab.irep.controller.experiment;

import cn.edu.whu.irlab.irep.service.experiment.preProcess.Impl.PreProcessorServiceImpl;
import cn.edu.whu.irlab.irep.service.experiment.preProcess.PreProcessorService;
import cn.edu.whu.irlab.irep.service.util.Find;
import cn.edu.whu.irlab.irep.service.vo.TfVo2;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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


    @Autowired
    private PreProcessorService preProcessorService;

    /**
     * 中文预处理控制层
     * @return 预处理结果
     */
    @PostMapping("/preProcess")
    public List<String> preProcessController(@RequestBody JSONObject object,
                                             HttpServletRequest request) {
        String token=object.getString("token");
        String analyzerName=object.getString("analyzerName");
        boolean removeStopWord=object.getBoolean("isRemoveStopWord");
        List<String> termList = PreProcessorServiceImpl.preProcess(token, analyzerName, removeStopWord);

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

    /**
     * 生成词云
     * @param docId 文档Id
     * @param analyzerName 分词器名称
     * @param removeStopWord 是否去停用词
     * @return 词云数据
     */
    @PostMapping("/createTermCloud")
    public JSONObject createTermCloudController(@RequestParam(name = "docId")int docId,
                                                @RequestParam(name = "analyzerName") String analyzerName,
                                                @RequestParam(name = "isRemoveStopWord") boolean removeStopWord){
        JSONObject output=new JSONObject();
        List<TfVo2> termCloud=preProcessorService.createTermCloud(docId,analyzerName,removeStopWord);
        output.put("docId",docId);
        output.put("data",termCloud);
        return output;
    }

}
