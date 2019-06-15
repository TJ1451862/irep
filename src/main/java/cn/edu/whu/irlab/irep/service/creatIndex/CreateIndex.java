package cn.edu.whu.irlab.irep.service.creatIndex;

import cn.edu.whu.irlab.irep.service.entity.Doc;
import org.apache.lucene.analysis.Analyzer;

import java.io.File;
import java.util.ArrayList;

public class CreateIndex {

    public String folderPath="";//待处理文档的文件夹路径

    public void createIndex(Analyzer analyzer,boolean isRemoveStopWord){

        File fileFolder=new File(folderPath);
        if (fileFolder.exists()){
            File[] files=fileFolder.listFiles();
            for (File file:
                 files) {
                Doc doc=new Doc(file.getPath());
                ArrayList<String> termList=doc.preProcess(analyzer,isRemoveStopWord);
                int num=0;
                for (String s:
                     termList) {

                    num++;

                }

            }
        }

    }
}
