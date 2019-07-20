package cn.edu.whu.irlab.irep.service.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

public class Find {

    private static String webId_title = null;
    private static String webId_title_eng = null;
    private static String webId_FileName = null;
    private static String doc_list = null;
    private static String webId_Url = null;

    public static int findId(String fileName, boolean isChinese) {

        int web_Id = 0;
        String dataDir = null;
        if (webId_FileName == null) {
            dataDir = "resources/map/Id_Name.json";
            webId_FileName = ReadDoc.readDoc(dataDir);
        }
        JSONObject json = JSON.parseObject(webId_FileName);
        if (isChinese) {
            for (Map.Entry<String, Object> entry : json.entrySet()) {
                String s = entry.getValue() + ".txt";
                if (fileName.equals(s)) {
                    web_Id = Integer.valueOf(entry.getKey());
                }
            }
        } else {
            for (Map.Entry<String, Object> entry : json.entrySet()) {
                String s = entry.getValue() + "_eng.txt";
                if (fileName.equals(s)) {
                    web_Id = Integer.valueOf(entry.getKey());
                }
            }
        }
        return web_Id;
    }

    public static String findTitle(int webID, boolean isChinese) {

        String title = null;
        String dataDir = null;
        String ID = Integer.toString(webID);

        if (isChinese) {
            if (webId_title == null) {
                dataDir = "resources/map/webid_title.json";
                webId_title = ReadDoc.readDoc(dataDir);
            }
            JSONObject jsonObject = JSON.parseObject(webId_title);
            title = jsonObject.getString(ID);
        } else {
            if (webId_title_eng == null) {
                dataDir = "resources/map/en_webid_title.json";
                webId_title_eng = ReadDoc.readDoc(dataDir);
            }
            JSONObject jsonObject = JSON.parseObject(webId_title_eng);
            title = jsonObject.getString(ID);
        }
        return title;
    }

    public static String findDoc(int webID, boolean chinese) {
        String doc = null;
        String docName;
        String dataDir = null;
        String dataDir1 = null;//存文档路径
        String ID = Integer.toString(webID);

        if (webId_FileName == null) {
            dataDir = "resources/map/Id_Name.json";
            webId_FileName = ReadDoc.readDoc(dataDir);
        }

        JSONObject jsonObject = JSON.parseObject(webId_FileName);
        docName = jsonObject.getString(ID);
        if (chinese) {
            dataDir1 = "resources/doc_ch/" + docName + ".txt";
        } else {
            dataDir1 = "resources/doc_en/" + docName + "_eng.txt";
        }

        doc = ReadDoc.readDoc(dataDir1);
        return doc;
    }

    public static String findName(int docId) {
        String name = null;
        String dataDir = null;

        if (webId_FileName == null) {
            dataDir = "resources/map/Id_Name.json";
            webId_FileName = ReadDoc.readDoc(dataDir);
        }
        JSONObject json = JSON.parseObject(webId_FileName);
        return json.getString(String.valueOf(docId));
    }

    public static String findUrl(int webId) {
        String url = null;
        String dataDir = null;
        String name = findName(webId);

        if (webId_Url == null) {
            dataDir = "resources/map/Name_Url.json";
            webId_Url = ReadDoc.readDoc(dataDir);
        }
        JSONArray array = JSONArray.parseArray(webId_Url);
        for (int i = 0; i < array.size(); i++) {
            JSONObject object = array.getJSONObject(i);
            String docName = object.getString("name");
            if (docName.equals(name)) {
                url = object.getString("url");
            }
        }

        return url;

    }
}

