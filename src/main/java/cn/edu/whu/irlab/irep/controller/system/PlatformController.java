package cn.edu.whu.irlab.irep.controller.system;

import cn.edu.whu.irlab.irep.utils.JwtUtil;
import cn.edu.whu.irlab.irep.utils.SendData;
import cn.edu.whu.irlab.irep.utils.Type;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.Map;

/**
 * @author fangrf
 * @version 1.0
 * @date 2019-07-17 14:29
 * @desc 后台管理的页面跳转
 **/
@Controller
@RequestMapping(value = "/platform")
public class PlatformController {

    @RequestMapping("/decode")
    @ResponseBody
    public String decode(@RequestParam(name="token") String token) throws UnsupportedEncodingException {
        System.out.println(token);
        String jsonStr = JwtUtil.dencrty(token);
        //获取平台信息
        return jsonStr;
    }

    @RequestMapping("/sendData")
    @ResponseBody
    public String sendData(@RequestParam Map<String, Object> params) {
        String strJson = "";
        JSONObject param = new JSONObject();
        param.put("username",params.get("username"));
        param.put("projectTitle","网络大数据搜索引擎虚拟仿真实验");
        param.put("status",params.get("status"));
        param.put("score",params.get("score"));
        param.put("startDate",params.get("startDate"));
        param.put("endDate",params.get("endDate"));
        param.put("timeUsed",params.get("timeUsed"));
        param.put("issuerId",Type.issueId);
        String json = param.toString();
        try
        {
            String xjwt=JwtUtil.encrty(json);

            String url = Type.serverURI +"/project/log/upload?xjwt=" +  xjwt ;
            System.out.println(url );

            try {
                /**
                 * 发送实验结果。
                 */
                strJson = SendData.sendPost(url,"");
                System.out.println("-------POST-------"+strJson);

            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        return strJson;
    }

}
