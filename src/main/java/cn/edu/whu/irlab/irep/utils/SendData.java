package cn.edu.whu.irlab.irep.utils;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.Date;
import java.util.Random;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.binary.Hex;

public class SendData
{

	public static void main(String[] args)
	{
//			
//		checkStatus();
	    
	 //   loginUser("test","123456");
     //   String attachmentId = upload();
     //   sendData(attachmentId);
	}

	public static void loginUser(String loginId,String pwd) {          
        try { 
            String cnonce = getRandomString(16);
            String nonce = getRandomString(16);
            try {
                pwd = SHA256(nonce+SHA256(pwd).toUpperCase() + cnonce).toUpperCase();
                System.out.println(pwd); 
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            //base64(HmacSHA256(based64(raw header) + '.' + base64(raw payload), secret key))
            
            String url= Type.serverURI +"/sys/api/user/validate?" ;
            System.out.println(url +"username="+loginId +"&password="+pwd +"&nonce="+nonce +"&cnonce=" +cnonce ); 

            try {
                /**
                 * 验证用户
                 */
                String sr = sendPost(url+"username="+loginId +"&password="+pwd +"&nonce="+nonce +"&cnonce=" +cnonce,"");
                System.out.println("-------POST-------"+sr);
                
                //读取实验结果
                JSONObject retJson=new JSONObject();
                retJson = JSONObject.parseObject(sr);
                
                System.out.println("username:"+retJson.get("username"));
                System.out.println("code:"+retJson.get("code"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


	/**
     *  利用Apache的工具类实现SHA-256加密
     *  所需jar包下載 http://pan.baidu.com/s/1nuKxYGh
     * @param str 加密后的报文
     * @return
     */
    public static String SHA256(String str){
        MessageDigest messageDigest;
        String encdeStr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hash = messageDigest.digest(str.getBytes("UTF-8"));
            encdeStr = Hex.encodeHexString(hash);
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return encdeStr;
    }


    public static String upload() {
        String json="SYS";
        String id="";
        try
        {

            File file = new File("D:\\批发告知承诺书（网上办证版本2）.pdf");
            String xjwt=JwtUtil.encrty(json);
            //System.out.println(xjwt);
             
            String url = Type.serverURI +"/project/log/attachment/upload?"  ;

            try {
                String totalChunks="1";
                String current="0";
                String filename = "测试文件.pdf";
                String chunkSize="5048576";
                
                filename = URLEncoder.encode(filename,"UTF-8");
                /**
                 * 上传实验文件。
                 */
                System.out.println(url + "&totalChunks="+totalChunks +"&current="+current +"&filename=" +filename+ "&chunkSize=" +chunkSize +"&xjwt="+xjwt); 
                String sr = uploadFile(url + "&totalChunks="+totalChunks +"&current="+current +"&filename=" +filename+ "&chunkSize=" +chunkSize +"&xjwt="+xjwt
                        ,file);
                System.out.println("-------POST-------"+sr);
                
                //读取实验结果
                JSONObject retJson=new JSONObject();
                retJson = JSONObject.parseObject(sr);
                
                System.out.println("id:"+retJson.get("id"));
                System.out.println("code:"+retJson.get("code"));
                id = (String) retJson.get("id");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        return id;
    }



    public static void checkStatus() {
        JSONObject param=new JSONObject();
        param.put("username","test2");
        param.put("issuerId","100400"); 
        
        String json = param.toString();
        try {
            String xjwt = JwtUtil.encrty(json);
            System.out.println(xjwt);
            
            String url = Type.serverURI +"/third/api/test/result/upload?xjwt=" +  xjwt ;
            System.out.println(url ); 

            try {
                /**
                 * 检查状态。
                 */
                String sr = sendPost(url,"");
                System.out.println("-------POST-------"+sr);
                
                //读取实验结果
                JSONObject retJson=new JSONObject();
                retJson = JSONObject.parseObject(sr);

                System.out.println("code:"+retJson.get("code"));
                System.out.println("id:"+retJson.get("id"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }



    public static void sendData(String attachmentId) {
        long now = new Date().getTime();
//		long now=System.currentTimeMillis();
		System.out.println("now=="+now);
		JSONObject param = new JSONObject();
		param.put("username","test");
		param.put("projectTitle","网络大数据搜索引擎虚拟仿真实验");
		param.put("status",1);
		param.put("score",94);
		param.put("startDate",now-4000000);
		param.put("endDate",now-1000000);
		param.put("timeUsed",15);
		param.put("issuerId","100400");
		param.put("attachmentId",attachmentId);
		String json=param.toString();
		try
		{
			String xjwt=JwtUtil.encrty(json);
			//System.out.println(xjwt );  
			
			String url = Type.serverURI +"/project/log/upload?xjwt=" +  xjwt ;
            System.out.println(url );
            

            try {
                /**
                 * 发送实验结果。
                 */
                String sr = sendPost(url,"");
                System.out.println("-------POST-------"+sr);
                
                //读取实验结果
                JSONObject retJson=new JSONObject();
                retJson = JSONObject.parseObject(sr);

                System.out.println("code:"+retJson.get("code"));
                System.out.println("id:"+retJson.get("id"));
            } catch (Exception e) {
                e.printStackTrace();
            }
			
			String strJson=JwtUtil.dencrty(xjwt);
			System.out.println(strJson);
		} catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
    }
	
	

    /**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            //conn.setRequestProperty("method", "POST");
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true); 
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String lines;
            StringBuffer sb = new StringBuffer("");
            while ((lines = in.readLine()) != null) {
                lines = new String(lines.getBytes(), "utf-8");
                sb.append(lines);
            }
            result = sb.toString();
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }   
    
    public static String getRandomString(int length){
        String str="ABCDEF0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
          int number=random.nextInt(16);
          sb.append(str.charAt(number));
        }
        return sb.toString();
    }
    
    public static String uploadFile(String serverURL,File file){
        String result ="";
        try { 
            URL url = new URL(serverURL);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            // 发送POST请求必须设置如下两行
  
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type","text/html");
            conn.setRequestProperty("Cache-Control","no-cache");
            conn.setRequestProperty("Charsert", "UTF-8"); 
            conn.connect();
            conn.setConnectTimeout(10000);
            OutputStream out =conn.getOutputStream(); 
  
            DataInputStream in = new DataInputStream(new FileInputStream(file));
  
            int bytes = 0;
            byte[] buffer = new byte[1024];
            while ((bytes = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytes);
            }
            in.close();
            out.flush();
            out.close(); 
      
            
         // 得到响应码
            int res = conn.getResponseCode();
            StringBuilder sb2;
            if (res == 200){
             // 定义BufferedReader输入流来读取URL的响应
                BufferedReader in2 = new BufferedReader( new InputStreamReader(conn.getInputStream()));
                String lines;
                StringBuffer sb = new StringBuffer("");
                while ((lines = in2.readLine()) != null) {
                    lines = new String(lines.getBytes(), "utf-8");
                    sb.append(lines);
                }
                result = sb.toString();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;

    }
}
