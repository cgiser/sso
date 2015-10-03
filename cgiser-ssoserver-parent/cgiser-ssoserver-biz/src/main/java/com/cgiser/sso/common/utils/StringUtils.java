/**
 * $Id$
 *
 *
 */
package com.cgiser.sso.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * @Title: StringUtil.java
 * @Copyright: Copyright (c) 2009
 * @Description: <br>
 *               工具类 <br>
 * @Company: 互动
 * @Created on 2010-10-27 下午02:33:22
 * @author liaoxiandong
 * @version $Revision: 1.0 $
 * @see　HISTORY
 * @since 1.0
 */
public class StringUtils {
	/**
	 * 时间转换成字符串：带format formatStr:yyyyMMddHHmmss、yyyyMMddHHmmssSSS、yyyyMMdd等等
	 * 
	 * @param format
	 * @param date
	 * @return
	 */
	public static String date2Str(String format, Date date) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	/**
	 * 转换成时间戳
	 * 
	 * @param date
	 * @return
	 */
	public static String date2TimeStream(Date date) {
		if (date == null) {
			return "";
		}
		long time = date.getTime();
		String timeStr = (time + "").trim();
		return timeStr;
	}

	/**
	 * 把时间戳转换成时间格式的串
	 * 
	 * @param datelong
	 * @param format
	 * @return
	 */
	public static String long2DateStr(long datelong, String format) {
		Date date = new Date(datelong);
		return date2Str(format, date);
	}

	/**
	 * 验证邮箱是否有效
	 */
	public static boolean checkUserEmailValid(String email) {
		return Pattern.matches(
				"^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$", email);
	}
	/**
	 * 验证用户名是否有效(由数字和26个英文字母或者下划线组成的字符串)
	 */
	public static boolean checkUserNameValid(String name) {
		return Pattern.matches(
				"^[\\w+$]{2,10}", name);
	}
	/**
	 * 验证密码是否有效(由数字和26个英文字母或者下划线组成的字符串)
	 */
	public static boolean checkPasswordValid(String name) {
		return Pattern.matches(
				"^[\\w+$]{4,8}", name);
	}
    /**
     * 检查字符串是否为空或者''
     * 
     * @param data
     * @return 为空返回ture，否则返回false
     */
    public static boolean checkNullWithTrim(String data) {
        return org.apache.commons.lang.StringUtils.isEmpty(data);
    }
	/**
	 * test
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(StringUtils.checkPasswordValid("dsd2sa"));
	}
}
