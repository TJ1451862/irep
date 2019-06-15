package cn.edu.whu.irlab.irep;

import cn.edu.whu.irlab.irep.service.creatIndex.CreateIndex;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class IndexTest {

    @Test
    public void indexTest(){
        String analyzerName="smartChinese";
        boolean isRemoveStopWord=false;

        CreateIndex createIndex=new CreateIndex();
        createIndex.createIndex(analyzerName,isRemoveStopWord);

    }


}
