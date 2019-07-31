package cn.edu.whu.irlab.irep.controller.experiment;

import cn.edu.whu.irlab.irep.base.entity.experiment.FullIndex;
import cn.edu.whu.irlab.irep.base.entity.experiment.InvertedIndex;
import cn.edu.whu.irlab.irep.service.experiment.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("IRforCN/invertedIndex")
public class InvertedIndexController {

    @Autowired
    private IndexService indexService;

    /**
     * 加载索引
     * @param request HttpServletRequest
     * @return "索引加载完成"
     */
    @PostMapping("/loadIndex")
    public Map<String,String> loadIndexController(HttpServletRequest request){
        Map<String,String> state=new HashMap<>();
        int code=indexService.loadIndex(request);
        state.put("code",String.valueOf(code));
        if (code==1){
            state.put("message","索引加载完成！");
        }else {
            state.put("message","索引加载失败");
        }
        return state;
    }

    /**
     * @return 全体倒排索引表
     */
    @PostMapping("/fullIndex")
    public List<FullIndex> selectFullIndexController() {
        return indexService.selectFullIndex();
//        List<FullIndex> fullIndexList = indexService.selectFullIndex();
//        JSONArray jsonArray = JSON.parseArray(JSON.toJSONString(fullIndexList));
//        return jsonArray;
    }

    /**
     * @param term 词项
     * @return 倒排索引表
     */
    @PostMapping("/invertedIndex")
    public List<InvertedIndex> selectInvertedIndexController(@RequestParam(name = "term") String term) {
        return indexService.selectInvertedIndex(term);
    }
}
