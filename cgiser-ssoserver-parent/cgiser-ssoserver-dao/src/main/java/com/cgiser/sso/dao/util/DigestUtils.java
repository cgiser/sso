package com.cgiser.sso.dao.util;

import java.security.MessageDigest;

/**
 * 
 * @author yangh
 *
 */
public class DigestUtils {

	  
	  /**
	   * Get digest string of input string.
	   * @param input input string
	   * @return digest string
	   * @throws GenericException
	   */
	  public static String digest(String input){
	    try {
	      MessageDigest md = MessageDigest.getInstance("MD5");
	      
	      byte[] bDigests = md.digest(input.getBytes("UTF-8"));
	      
	      return byte2hex(bDigests);  
	    }
	    catch(Exception e) {
	      return "";
	    }
	  }
	  
	  private static String byte2hex(byte[] b) {

	    String hs = "";
	    String stmp = "";
	    for (int n = 0; n < b.length; n++) {
	      stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
	      if (stmp.length() == 1)
	        hs = hs + "0" + stmp;
	      else
	        hs = hs + stmp;
	    }
	    return hs.toUpperCase();
	  }
	  
	  /**
	   * To check if it is equal between two digest sting.
	   * @param digesta
	   * @param digestb
	   * @return compare result
	   * @throws GenericException
	   */
	  public static boolean isEqual(String digesta, String digestb) throws Exception {
	    try {
	      
	      return MessageDigest.isEqual(digesta.toUpperCase().getBytes(), digestb.toUpperCase().getBytes());
	      
	    }
	    catch(Exception e) {
	      throw e;
	    }
	  }
	  
	  public static void main(String[] ar) throws Exception {
	    String s = DigestUtils.digest("100000000");
	    System.out.println(s);
	    System.out.println(s.length());
	  }
	  
	  


}
