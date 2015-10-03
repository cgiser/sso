package com.cgiser.sso.support;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class WdjSdk {
	private String goUrl = "https://pay.wandoujia.com/api/uid/check?";
	private String app_key = "100001153";
	
	public int checkLogin(String uid,String token){
		String act = "1";
		StringBuilder getUrl = new StringBuilder();
		getUrl.append("uid=");
		getUrl.append(uid);
		getUrl.append("&token=");
		getUrl.append( URLEncoder.encode(token));
		getUrl.append("&appkey_id=");
		getUrl.append(app_key);
		try {
			return GetResult(HttpGetGo(getUrl.toString()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	/**
	 * 获取WDJ服务器返回的结果
	 * @param jsonStr
	 * @return
	 * @throws Exception
	 */
	private int GetResult(String jsonStr) throws Exception{
//		Pattern p = Pattern.compile("(?<=\"ErrorCode\":\")\\d{1,3}(?=\")");
//		Matcher m = p.matcher(jsonStr);
//		m.find();
//		return Integer.parseInt(m.group());
		
		//这里需要引入JSON-LIB包内的JAR
		if(jsonStr.equals("true")){
			return 1;
		}
		return 0;
	}
	/**
	 * 发送GET请求并获取结果
	 * @param getUrl
	 * @return
	 * @throws Exception
	 */
	private String HttpGetGo(String getUrl) throws Exception{   
	    StringBuffer readOneLineBuff = new StringBuffer();   
	    String content ="";   
        URL url = new URL( goUrl + getUrl);   
        URLConnection conn = url.openConnection();   
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));          
        String line = "";   
        while ((line = reader.readLine()) != null) {   
            readOneLineBuff.append(line);   
        }   
        content = readOneLineBuff.toString();   
        reader.close();   
	    return content;   
	}   
}
