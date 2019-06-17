package cn.edu.whu.irlab.irep.service;

import cn.edu.whu.irlab.irep.IrepApplication;
import cn.edu.whu.irlab.irep.entity.Record;
import cn.edu.whu.irlab.irep.service.impl.RecordServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = IrepApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RecordServiceTest {

    @Autowired
    public RecordServiceImpl recordService;

    @Test
    public void addTest(){

        Record record = new Record();
        record.setTerm("aa");
        record.setDocId(1);
        record.setLocation(0);
        record.setIndexType("bb");
        System.out.println(record);
        recordService.insert(record);
//        try{
//            int row= recordService.insertRecord("aa",1,1,"ddd");
//        }catch (Exception e){
//
//            e.printStackTrace();
//        }




    }

}
