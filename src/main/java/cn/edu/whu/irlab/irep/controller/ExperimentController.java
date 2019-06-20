package cn.edu.whu.irlab.irep.controller;

import cn.edu.whu.irlab.irep.entity.FullIndex;
import cn.edu.whu.irlab.irep.entity.InvertedIndex;
import cn.edu.whu.irlab.irep.service.creatIndex.IndexGenerator;
import cn.edu.whu.irlab.irep.service.impl.FullIndexServiceImpl;
import cn.edu.whu.irlab.irep.service.impl.InvertedIndexServiceImpl;
import cn.edu.whu.irlab.irep.service.preProcess.PreProcessor;
import cn.edu.whu.irlab.irep.service.retrievalModel.vsmmodel.DocForVSM;
import cn.edu.whu.irlab.irep.service.retrievalModel.vsmmodel.ResultI;
import cn.edu.whu.irlab.irep.service.retrievalModel.vsmmodel.VSMRetriever;
import cn.edu.whu.irlab.irep.service.retrievalModel.vsmmodel.VectorI;
import cn.edu.whu.irlab.irep.service.util.Find;
import cn.edu.whu.irlab.irep.service.util.IndexTypeConstructor;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.lucene.analysis.Analyzer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/experiment")
public class ExperimentController {

    @Autowired
    public PreProcessor preProcessor;

    @Autowired
    public FullIndexServiceImpl fullIndexService;

    @Autowired
    public InvertedIndexServiceImpl invertedIndexService;

    @Autowired
    IndexTypeConstructor indexTypeConstructor;

    @Autowired
    public Find find;

    public String folderPath = "resources/doc_ch";


    public String preProcessEnController(@RequestParam(name = "token") String token,
                                         @RequestParam(name = "isRemoveStopWord") boolean isRemoveStopWord,
                                         @RequestParam(name = "isExtractStems") boolean isExtractStems) {
        String string = "";//待完善
        return string;
    }

    /**
     * @param analyzerName     分词器名称
     * @param isRemoveStopWord 是否去停用词
     * @return 全体倒排索引表
     */
    @RequestMapping("/fullIndex")
    public List<FullIndex> selectFullIndexController(@RequestParam(name = "analyzerName") String analyzerName,
                                                     @RequestParam(name = "isRemoveStopWord") boolean isRemoveStopWord) {
        String indexType = indexTypeConstructor.indexTypeConstructor(analyzerName, isRemoveStopWord);
        List<FullIndex> fullIndexList = fullIndexService.selectFullIndexByIndexType(indexType);
        if (fullIndexList == null) {
            IndexGenerator indexGenerator = new IndexGenerator(folderPath, analyzerName, isRemoveStopWord);
            indexGenerator.generateIndex();
            fullIndexList = fullIndexService.selectFullIndexByIndexType(indexType);
        }
        return fullIndexList;
    }

    //返回invertedIndex

    /**
     * @param analyzerName     分词器名称
     * @param isRemoveStopWord 是否去停用词
     * @param term             词项
     * @return 倒排索引表
     */
    @RequestMapping("/invertedIndex")
    public List<InvertedIndex> selectInvertedIndexController(@RequestParam(name = "analyzerName") String analyzerName,
                                                             @RequestParam(name = "isRemoveStopWord") boolean isRemoveStopWord,
                                                             @RequestParam(name = "term") String term) {
        String indexType = indexTypeConstructor.indexTypeConstructor(analyzerName, isRemoveStopWord);
        List<InvertedIndex> invertedIndexList = invertedIndexService.selectByIndexTypeAndTerm(indexType, term);
        return invertedIndexList;
    }

}
