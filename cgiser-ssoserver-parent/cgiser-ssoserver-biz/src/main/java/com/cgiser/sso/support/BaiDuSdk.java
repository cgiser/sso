package com.cgiser.sso.support;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import net.sf.json.JSONObject;

public class BaiDuSdk {
	private static String appKey = "Y6jKiQEUrjGu5xhNs426kKT2j2L9DtwC";

	public static String getUserInfo(String appId,String code) {
		try {
			// 构造请求参数
			String transdata = "{\"appid\":\""+appId+"\",\"code\":\""+code+"\"}";
			String param = "r=FromIapppayToUserAction&m=domethod2&transdata="
					+ transdata + "&sign="
					+ getMD5((transdata + appKey).getBytes(), false);
			URL url;

			url = new URL("http://gameopen.baidu.com/index.php");
			HttpURLConnection urlConn = (HttpURLConnection) url
					.openConnection();
			// 设置是否向httpUrlConnection输出，因为这个是post请求，参数要放在
			// http正文内，因此需要设为true, 默认情况下是false;
			urlConn.setDoOutput(true);

			// 设置是否从httpUrlConnection读入，默认情况下是true;
			urlConn.setDoInput(true);

			// Post 请求不能使用缓存
			urlConn.setUseCaches(false);

			// 设定传送的内容类型是可序列化的java对象
			// (如果不设此项,在传送序列化对象时,当WEB服务默认的不是这种类型时可能抛java.io.EOFException)
			urlConn.setRequestProperty("Content-type",
					"application/x-www-form-urlencoded;charset=utf-8");
			urlConn.setRequestProperty("Connection", "Keep-Alive");
			// 设定请求的方法为"POST"，默认是GET
			urlConn.setRequestMethod("POST");
			// 连接，上面对urlConn的所有配置必须要在connect之前完成，
			urlConn.connect();
			// 此处getOutputStream会隐含的进行connect (即：如同调用上面的connect()方法，
			// 所以在开发中不调用上述的connect()也可以)。
			OutputStream outStrm = urlConn.getOutputStream();
			// 向对象输出流写出数据，这些数据将存到内存缓冲区中
			outStrm.write(param.getBytes());
			// 调用HttpURLConnection连接对象的getInputStream()函数,
			// 将内存缓冲区中封装好的完整的HTTP请求电文发送到服务端。
			InputStream inStrm;
			int respCode = urlConn.getResponseCode();
			if (200 == respCode) {
				inStrm = urlConn.getInputStream();
			} else {
				inStrm = urlConn.getErrorStream();
			}

			StringBuilder sb = new StringBuilder();
			BufferedReader bf = new BufferedReader(
					new InputStreamReader(inStrm), 1000);
			String str = null;
			while ((str = bf.readLine()) != null) {
				sb.append(str);
			}
			bf.close();
			if(sb.toString().equals("FAILURE")){
	        	return "{result:0;value:\"FAILURE\"}";
	        }else{
	            sb.delete(0, 10);
	            return "{result:1;value:\""+sb.substring(0, sb.indexOf("}")+1)+"\"}";
	        }
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	public static String getMD5(byte[] source, boolean isUpperCase) {
		String s = null;
		char hexDigits[] = { // 用来将字节转换成 16 进制表示的字符
		'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
				'e', 'f' };
		try {
			java.security.MessageDigest md = java.security.MessageDigest
					.getInstance("MD5");
			md.update(source);
			byte tmp[] = md.digest(); // MD5 的计算结果是一个 128 位的长整数，
			// 用字节表示就是 16 个字节
			char str[] = new char[16 * 2]; // 每个字节用 16 进制表示的话，使用两个字符，
			// 所以表示成 16 进制需要 32 个字符
			int k = 0; // 表示转换结果中对应的字符位置
			for (int i = 0; i < 16; i++) { // 从第一个字节开始，对 MD5 的每一个字节
				// 转换成 16 进制字符的转换
				byte byte0 = tmp[i]; // 取第 i 个字节
				str[k++] = hexDigits[byte0 >>> 4 & 0xf]; // 取字节中高 4 位的数字转换,
				// >>> 为逻辑右移，将符号位一起右移
				str[k++] = hexDigits[byte0 & 0xf]; // 取字节中低 4 位的数字转换
			}
			s = new String(str); // 换后的结果转换为字符串

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (isUpperCase)
			return s.toUpperCase();
		else
			return s;
	}
	public static void main(String[] args) {
		String result = getUserInfo("2234101", "1401311736362662-2935-116699" );
		JSONObject obj = JSONObject.fromObject(result);
		System.out.println(obj);
	}
}
