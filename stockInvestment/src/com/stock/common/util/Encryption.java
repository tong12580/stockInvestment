package com.stock.common.util;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

/**
 * @ClassName: 加密类
 * @Description: 加密函数  32位
 * @author yutong
 * @date 2016/03/06 4:54:15 PM
 * 
 */
public class Encryption {

	public static String md5s(String plainText) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte b[] = md.digest();

			int i;

			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			// System.out.println("result: " + buf.toString());// 32位加密
			// System.out.println("result: " + buf.toString().substring(8,
			// 24));// 16位加密
			return buf.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * MD5 45位加密	
	 * @param fields
	 * @return
	 */
	  public static String getMD5Encode(String ... fields)
	  {
	    if (ArrayUtils.isEmpty(fields)) {
	      return null;
	    }

	    StringBuilder builder = new StringBuilder(60);
	    String[] arrayOfString = fields; int j = fields.length; for (int i = 0; i < j; i++) { String field = arrayOfString[i];
	      if (!StringUtils.isEmpty(field))
	      {
	    	  builder.append(field);
	      } }
	    return DigestUtils.md5Hex(builder.toString());
	  }

	  public static String getSHAEncode(String ... fields) {
	    if (ArrayUtils.isEmpty(fields)) {
	      return null;
	    }

	    StringBuilder builder = new StringBuilder(60);
	    String[] arrayOfString = fields; int j = fields.length; for (int i = 0; i < j; i++) { String field = arrayOfString[i];
	      if (!StringUtils.isEmpty(field))
	      {
	        builder.append(field);
	      } }
	    return DigestUtils.sha1Hex(builder.toString());
	  }

	  public static String decodeBase64(String base64String, String encoding) throws UnsupportedEncodingException {
	    if (StringUtils.isEmpty(base64String)) {
	      return null;
	    }
	    byte[] decodeArray = Base64.decodeBase64(base64String);
	    return new String(decodeArray, encoding);
	  }

	  public static String encodeBase64(String stringValue, String encoding) throws UnsupportedEncodingException
	  {
	    if (StringUtils.isEmpty(stringValue)) {
	      return null;
	    }
	    String encoded = Base64.encodeBase64String(stringValue.getBytes(Charset.forName(encoding)));
	    return encoded;
	  }

	
	public static void main(String args[]){
		
		System.out.println(md5s("123456" + "20140416123"));
		String encode = getSHAEncode(new String[] { "深圳大诚软件" });
		String encode2 = getMD5Encode(new String[] { "12345620140416123" });
		System.out.print(encode2);
		System.out.println(new Date().getTime());
		System.out.print(encode);
	}
}
