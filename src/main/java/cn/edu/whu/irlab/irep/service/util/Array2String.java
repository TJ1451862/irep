package cn.edu.whu.irlab.irep.service.util;

import java.util.ArrayList;

public class Array2String {
    public static String array2String(ArrayList<String> arrayList){
        String string="";
        for (String i:
                arrayList) {
            string+=i;
        }
        return string;
    }
}
