package cn.edu.whu.irlab.irep.service.xml2txt;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XML2TXT {

    private static Document doc = new Document();
    private static Element root;

    public static void main(String[] args) {
        String filePath = "E:\\code\\Project\\irep\\resources\\demo_sogou-qcl";

        // 使用ArrayList来存储每行读取到的字符串
        ArrayList<String> arrayList = new ArrayList<>();
        JSONArray array=new JSONArray();
        boolean sign=true;
        String url="";
        String name="";
        try {
            FileReader fr = new FileReader(filePath);
            BufferedReader bf = new BufferedReader(fr);
            String str;
            // 按行读取字符串
            while ((str = bf.readLine()) != null) {
                if (str.contains("<url>")){
                    str= str.replace("<url>","").replace("</url>","").replace("\t","");
                    arrayList.add(str);
                }else if (str.contains("<doc_id>")){
                    str= str.replace("<doc_id>","").replace("</doc_id>","").replace("\t","");
                    arrayList.add(str);
                }
            }
            bf.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i <arrayList.size() ; i++) {
            if (sign){
                sign=!sign;
                url=arrayList.get(i);
            }else {
                sign=!sign;
                name=arrayList.get(i);
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("name",name);
                jsonObject.put("url",url);
                array.add(jsonObject);
            }
        }
        System.out.println(array);
    }
}
