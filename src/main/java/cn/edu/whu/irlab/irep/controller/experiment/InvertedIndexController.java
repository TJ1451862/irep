package cn.edu.whu.irlab.irep.controller.experiment;

import cn.edu.whu.irlab.irep.base.entity.FullIndex;
import cn.edu.whu.irlab.irep.base.entity.InvertedIndex;
import cn.edu.whu.irlab.irep.service.experiment.IndexService;
import cn.edu.whu.irlab.irep.service.experiment.creatIndex.IndexGenerator;
import cn.edu.whu.irlab.irep.base.dao.impl.FullIndexServiceImpl;
import cn.edu.whu.irlab.irep.base.dao.impl.InvertedIndexServiceImpl;
import cn.edu.whu.irlab.irep.service.util.Constructor;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("IRforCN/invertedIndex")
public class InvertedIndexController {

    @Autowired
    private IndexService indexService;

    /**
     * 加载索引
     * @param request HttpServletRequest
     * @return "索引加载完成"
     */
    @RequestMapping("/loadIndex")
    @ResponseBody
    public String loadIndexController(HttpServletRequest request){
        return indexService.loadIndex(request);
    }

    /**
     * @return 全体倒排索引表
     */
    @ResponseBody
    @RequestMapping("/fullIndex")
    public JSONArray selectFullIndexController() {
        List<FullIndex> fullIndexList = indexService.selectFullIndex();
        JSONArray jsonArray = JSON.parseArray(JSON.toJSONString(fullIndexList));
        return jsonArray;
    }

    /**
     * @param term 词项
     * @return 倒排索引表
     */
    @ResponseBody
    @RequestMapping("/invertedIndex")
    public JSONArray selectInvertedIndexController(@RequestParam(name = "term") String term) {
        List<InvertedIndex> invertedIndexList = indexService.selectInvertedIndex(term);
        JSONArray jsonArray = JSON.parseArray(JSON.toJSONString(invertedIndexList));
        return jsonArray;
    }
}
