package cn.xdaoy.utils;

import java.security.MessageDigest;


public class StringUtils extends org.apache.commons.lang3.StringUtils{
	
	public static final String SHA1 = "SHA-1";
	public static final String SHA256 = "SHA-256";
	
	/**
	 * String is empty check
	 * 
	 * <p>1.str is null</p>
	 * <p>2.str is blank+</p>
	 * <p>3.str is 'null' or 'undefined'</p>
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str){
		if(str==null){
			return true;
		}
		String t = str.replace(" ", "").trim();
		if("".equals(t) || "null".equalsIgnoreCase(t) || "undefined".equalsIgnoreCase(t)){
			return true;
		}
		return false;
	}
	
	
	/**
	 * get String sha1 value
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static String getSHA1(String str) throws Exception{
		return digest(str,"SHA-1","UTF-8");
    }
	
	/**
	 * get String sha256 value
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static String getSHA256(String str) throws Exception{
		return digest(str,"SHA-256","UTF-8");
    }
	
	/**
	 * 
	 * @param str: digest target String
	 * @param type: digest type
	 * @param charset: str charset
	 * @return
	 * @throws Exception
	 */
	public static String digest(String str,String type,String charset) throws Exception{
		MessageDigest messageDigest= MessageDigest.getInstance(type);
		messageDigest.update(str.getBytes(charset));
		return byte2Hex(messageDigest.digest());
    }
	
	/**
	 * byte to hex
	 * 
	 * @param bytes
	 * @return
	 */
	public static String byte2Hex(byte[] bytes) {
		if (bytes == null || bytes.length <= 0) {
			return "";
		}
    	StringBuffer stringBuffer = new StringBuffer();
    	String temp = null;
    	for(int i=0; i<bytes.length; i++) {
    		temp = Integer.toHexString(bytes[i] & 0xFF);
    		if (temp.length() == 1) {
    			stringBuffer.append("0");
    		}
    		stringBuffer.append(temp);
    	}
    	return stringBuffer.toString();
    }
	
}
