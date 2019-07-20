package cn.edu.whu.irlab.irep;

import cn.edu.whu.irlab.irep.service.experiment.preProcess.PreProcessor;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class analyzerTest {

    @Autowired
    public PreProcessor preProcessor;

    @Test
    public void standerAnalyzer() throws IOException {
        String string = "中华人民共和国简称中国，是一个有13亿人口的国家。";
        String analyzerName = "standard";
//        System.out.println(preProcessor.outputAnalyzer(string, analyzerName));
    }


}
