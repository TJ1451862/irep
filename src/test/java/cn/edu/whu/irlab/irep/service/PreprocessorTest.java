package cn.edu.whu.irlab.irep.service;

import cn.edu.whu.irlab.irep.IrepApplication;
import cn.edu.whu.irlab.irep.service.experiment.preProcess.PreProcessor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = IrepApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PreprocessorTest {



    @Test
    public void preProcessorTest() {
        String string = "中华人民共和国简称中国，是一个有13亿人口的国家。";
        String analyzerName = "standard";
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList = PreProcessor.preProcess(string, analyzerName, true);
//        System.out.println(arrayList);
    }

}
