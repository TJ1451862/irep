package cn.edu.whu.irlab.irep.controller.system;

import cn.edu.whu.irlab.irep.base.dao.system.UserService;
import cn.edu.whu.irlab.irep.base.entity.system.User;
import cn.edu.whu.irlab.irep.service.enums.ResponseEnum;
import cn.edu.whu.irlab.irep.service.util.ResponseVoUtil;
import cn.edu.whu.irlab.irep.service.vo.ResponseVo;
import cn.edu.whu.irlab.irep.utils.JwtUtil;
import cn.edu.whu.irlab.irep.utils.SendData;
import cn.edu.whu.irlab.irep.utils.Type;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * @author fangrf
 * @version 1.0
 * @date 2019-07-17 14:29
 * @desc 后台管理的页面跳转
 **/
@Controller
@RequestMapping(value = "/platform")
public class PlatformController {

    @Autowired
    private UserService userService;

    @RequestMapping("/decode")
    @ResponseBody
    public ResponseVo decode(@RequestParam String token, HttpServletRequest request) throws UnsupportedEncodingException {
        System.out.println(token);
        String jsonStr = JwtUtil.dencrty(token);
        JSONObject jo = JSONObject.parseObject(jsonStr);
        String username = jo.getString("un");
        User user1 = userService.selectUserService(username);
        if (user1 == null) {
            User nu = new User();
            nu.setUsername(username);
            nu.setPassword("123456");
            nu.setSalt("111");
            nu.setEmail(UUID.randomUUID().toString());
            nu.setPhone(UUID.randomUUID().toString());
            nu.setJobNumber(UUID.randomUUID().toString());
            nu.setCategory(2);  ////用户类别1校内用户，2校外用户，3是后台管理员
            userService.insertUserService(nu);
            user1 = nu;
        }
        request.getSession().setAttribute("user",user1);
        return ResponseVoUtil.success(ResponseEnum.USER_LOGIN_SUCCESS,user1);
    }

    @RequestMapping("/sendData")
    @ResponseBody
    public String sendData(@RequestBody Map<String, Object> params) {
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
            String statusUrl = Type.serverURI +"/third/api/test/result/upload?xjwt=" +  xjwt ;
            System.out.println(url );

            try {
                /**
                 * 发送实验结果。
                 */
                strJson = SendData.sendPost(url,"");
                //更新实验状态
                String strJson2 = SendData.sendPost(statusUrl,"");
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
