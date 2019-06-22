package cn.edu.whu.irlab.irep.controller;

import cn.edu.whu.irlab.irep.entity.FullIndex;
import cn.edu.whu.irlab.irep.entity.InvertedIndex;
import cn.edu.whu.irlab.irep.service.creatIndex.IndexGenerator;
import cn.edu.whu.irlab.irep.service.impl.FullIndexServiceImpl;
import cn.edu.whu.irlab.irep.service.impl.InvertedIndexServiceImpl;
import cn.edu.whu.irlab.irep.service.util.Find;
import cn.edu.whu.irlab.irep.service.util.IndexTypeConstructor;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("IRforCN/invertedIndex")
public class InvertedIndexController {

    @Autowired
    public FullIndexServiceImpl fullIndexService;

    @Autowired
    public InvertedIndexServiceImpl invertedIndexService;



    @Autowired
    public Find find;

    public String folderPath = "resources/doc_ch";

    /**
     * @param analyzerName     分词器名称
     * @param isRemoveStopWord 是否去停用词
     * @return 全体倒排索引表
     */
    @ResponseBody
    @RequestMapping("/fullIndex")
    public JSONArray selectFullIndexController(@RequestParam(name = "analyzerName") String analyzerName,
                                               @RequestParam(name = "isRemoveStopWord") boolean isRemoveStopWord) {
        String indexType = IndexTypeConstructor.indexTypeConstructor(analyzerName, isRemoveStopWord);
        List<FullIndex> fullIndexList = fullIndexService.selectFullIndexByIndexType(indexType);
        if (fullIndexList == null) {
            IndexGenerator indexGenerator = new IndexGenerator(folderPath, analyzerName, isRemoveStopWord);
            indexGenerator.generateIndex();
            fullIndexList = fullIndexService.selectFullIndexByIndexType(indexType);
        }
        JSONArray jsonArray = JSON.parseArray(JSON.toJSONString(fullIndexList));
        System.out.println(jsonArray);
        return jsonArray;
    }

    //返回invertedIndex

    /**
     * @param analyzerName     分词器名称
     * @param isRemoveStopWord 是否去停用词
     * @param term             词项
     * @return 倒排索引表
     */
    @ResponseBody
    @RequestMapping("/invertedIndex")
    public JSONArray selectInvertedIndexController(@RequestParam(name = "analyzerName") String analyzerName,
                                                   @RequestParam(name = "isRemoveStopWord") boolean isRemoveStopWord,
                                                   @RequestParam(name = "term") String term) {
        String indexType = IndexTypeConstructor.indexTypeConstructor(analyzerName, isRemoveStopWord);
        InvertedIndex invertedIndex = new InvertedIndex();
        invertedIndex.setTerm(term);
        invertedIndex.setIndexType(indexType);
        List<InvertedIndex> invertedIndexList = invertedIndexService.select(invertedIndex);
        JSONArray jsonArray = JSON.parseArray(JSON.toJSONString(invertedIndexList));
        return jsonArray;
    }
}
