package cn.edu.whu.irlab.irep.controller;

import cn.edu.whu.irlab.irep.mybatis.entity.FullIndex;
import cn.edu.whu.irlab.irep.mybatis.entity.InvertedIndex;
import cn.edu.whu.irlab.irep.service.creatIndex.IndexGenerator;
import cn.edu.whu.irlab.irep.mybatis.service.impl.FullIndexServiceImpl;
import cn.edu.whu.irlab.irep.mybatis.service.impl.InvertedIndexServiceImpl;
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
    public FullIndexServiceImpl fullIndexService;

    @Autowired
    public InvertedIndexServiceImpl invertedIndexService;

    @Autowired
    public IndexGenerator indexGenerator;

    public String folderPath = "resources/doc_ch";

    /**
     * @return 全体倒排索引表
     */
    @ResponseBody
    @RequestMapping("/fullIndex")
    public JSONArray selectFullIndexController(HttpServletRequest request) {
        String indexType = Constructor.indexTypeConstructor(request);
        List<FullIndex> fullIndexList = fullIndexService.selectByIndexType(indexType);
        if (fullIndexList.size() == 0) {
            indexGenerator.initIndexGenerator(folderPath,request);
            indexGenerator.generateIndex();
            fullIndexList = fullIndexService.selectByIndexType(indexType);
        }
        JSONArray jsonArray = JSON.parseArray(JSON.toJSONString(fullIndexList));
//        System.out.println(jsonArray);
        return jsonArray;
    }

    /**
     * @param term 词项
     * @return 倒排索引表
     */
    @ResponseBody
    @RequestMapping("/invertedIndex")
    public JSONArray selectInvertedIndexController(@RequestParam(name = "term") String term,HttpServletRequest request) {
        String indexType = Constructor.indexTypeConstructor(request);
        InvertedIndex invertedIndex = new InvertedIndex();
        invertedIndex.setTerm(term);
        invertedIndex.setIndexType(indexType);
        List<InvertedIndex> invertedIndexList = invertedIndexService.select(invertedIndex);
        JSONArray jsonArray = JSON.parseArray(JSON.toJSONString(invertedIndexList));
        return jsonArray;
    }

    //返回invertedIndex
}
