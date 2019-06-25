package cn.edu.whu.irlab.irep.service.util;

import org.springframework.stereotype.Service;

import java.io.*;

/**
 * @author fangrf
 * @version 1.0
 * @date 2018-09-08 0:27
 * @desc 读文件的工具类
 **/
public class ReadDoc {

    /**
     * 读文件
     * @param docPath
     * @return
     */
    public static String readDoc(String docPath)//读文件
    {
//        String result = "";
//        String encoding="UTF-8";
//        try{
//        ClassPathResource resource = new ClassPathResource(docPath);
//        BufferedReader br = new BufferedReader(
//                new InputStreamReader(resource.getInputStream()));
//        String line;
//
//        while ((line = br.readLine()) != null) {
//            line=new String(line.getBytes(),encoding);
//            result += line;
//        }
//        br.close();
//        }catch (IOException e){
//          e.printStackTrace();
//        }
//        return result;

        String encoding = "UTF-8";
        File file = new File(docPath);
        Long filelength = file.length();
        byte[] filecontent = new byte[filelength.intValue()];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return new String(filecontent, encoding);
        } catch (UnsupportedEncodingException e) {
            System.err.println("The OS does not support " + encoding);
            e.printStackTrace();
            return null;
        }
    }
}
