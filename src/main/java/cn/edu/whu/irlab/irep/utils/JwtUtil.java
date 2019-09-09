package cn.edu.whu.irlab.irep.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Date;

public class JwtUtil
{

	public static void main(String[] args)
	{
		String token="AAABbRUnXPEBAAAAAAABiDA%3D.Sj0DRty4ojE%2B7lZPvmK3qZgiKwMZ0Y2XkowkiS069lOHN0Sf%2F3lDoOsEaMtEofwVO0W2PgOFVoUcr%2FOEu2yhQ5jtgOgfdCfa8s2f4suZWf0Csnh9xvGSP8nLn1f%2BWMZyhDcxJZanyldptZB%2Fo8jA4Rc4ct55bxrPROvL%2ByhkYJxP%2BeDzA0%2Bw2WoQ774UP2JdZPH6Y60kWhCcBBUK4mW4yQmLukmcKVuw9PIhfb4Qo6tNvvVytS7MjiqE0UTjpjmXniYN5zrLeyzkNddEp1htiL8%2FrO7xTJ7hKpQlkC5omRuv%2BB3DgCxICa1gAcnO5m%2Fl.OakvUC6w5JQJwbIA9NokxVWL%2B5yi467RCrZZz1LkBDI%3D";
		System.out.println("token=="+token);
		try
		{
			String resultJson = dencrty(token);
			System.out.println("resultJson=="+resultJson);
			encrty(resultJson);
		} catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
	}
	
	
	//解密token
	public static String dencrty(String token) throws UnsupportedEncodingException {
		//获取当前时间
		long now=new Date().getTime();
		//创建JWT实例
		JWT jwt = new JWT(Type.secret,Type.aeskey,System.nanoTime(),Type.issueId);
		//token = URLDecoder.decode(token,"UTF-8");
		//调用解密方法解密token
		String resultJson = jwt.verifyAndDecrypt(token,0);
		return resultJson;
	}
	
	//加密数据
	public static String  encrty(String json) throws UnsupportedEncodingException {
		//获取当前时间
		long now=System.currentTimeMillis();
		//创建JWT实例
		JWT jwt=new JWT(Type.secret,Type.aeskey,System.nanoTime(),Type.issueId);
		//创建payload用来装参数
		ByteBuffer payload = ByteBuffer.allocate(1024).order(ByteOrder.BIG_ENDIAN);
        payload.put(json.getBytes("UTF-8")).flip();
		//创建out对象
        ByteBuffer out = ByteBuffer.allocate(1024);
        //调用加密方法，加密参数
        jwt.encryptAndSign(JWT.Type.SYS,payload,out,now+60*60*1000);
        String xjwt = new String(out.array(),out.arrayOffset(),out.remaining());
		return URLEncoder.encode(xjwt,"UTF-8");
	}

}
