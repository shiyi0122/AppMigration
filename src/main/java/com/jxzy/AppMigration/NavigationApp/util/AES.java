package com.jxzy.AppMigration.NavigationApp.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

public class AES {

	static final String algorithmStr = "AES/ECB/PKCS5Padding";

	private static final Object TAG = "AES";

	static private KeyGenerator keyGen;

	static private Cipher cipher;

	static boolean isInited = false;

	static private String KEY  = "041a7d164c694c49";
	static private String IV   = "893D322B038CDZXA";

      
      private static  void init() {
		try { 
		        /**为指定算法生成一个 KeyGenerator 对象。
		        *此类提供（对称）密钥生成器的功能。
		        *密钥生成器是使用此类的某个 getInstance 类方法构造的。
		        *KeyGenerator 对象可重复使用，也就是说，在生成密钥后，
		        *可以重复使用同一 KeyGenerator 对象来生成进一步的密钥。
		        *生成密钥的方式有两种：与算法无关的方式，以及特定于算法的方式。
		        *两者之间的惟一不同是对象的初始化：
		        *与算法无关的初始化
		        *所有密钥生成器都具有密钥长度 和随机源 的概念。
		        *此 KeyGenerator 类中有一个 init 方法，它可采用这两个通用概念的参数。
		        *还有一个只带 keysize 参数的 init 方法，
		        *它使用具有最高优先级的提供程序的 SecureRandom 实现作为随机源
		        *（如果安装的提供程序都不提供 SecureRandom 实现，则使用系统提供的随机源）。
		        *此 KeyGenerator 类还提供一个只带随机源参数的 inti 方法。
		        *因为调用上述与算法无关的 init 方法时未指定其他参数，
		        *所以由提供程序决定如何处理将与每个密钥相关的特定于算法的参数（如果有）。
		        *特定于算法的初始化
		        *在已经存在特定于算法的参数集的情况下，
		        *有两个具有 AlgorithmParameterSpec 参数的 init 方法。
		        *其中一个方法还有一个 SecureRandom 参数，
		        *而另一个方法将已安装的高优先级提供程序的 SecureRandom 实现用作随机源
		        *（或者作为系统提供的随机源，如果安装的提供程序都不提供 SecureRandom 实现）。
		        *如果客户端没有显式地初始化 KeyGenerator（通过调用 init 方法），
		        *每个提供程序必须提供（和记录）默认初始化。
		        */
			keyGen = KeyGenerator.getInstance("AES");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		// 初始化此密钥生成器，使其具有确定的密钥长度。
		keyGen.init(128); //128位的AES加密
		try {    
		        // 生成一个实现指定转换的 Cipher 对象。
			cipher = Cipher.getInstance(algorithmStr);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		}
		//标识已经初始化过了的字段
		isInited = true;
	}

	private static byte[] genKey() {
		if (!isInited) {
			init();  
		}
		//首先 生成一个密钥(SecretKey),
		//然后,通过这个秘钥,返回基本编码格式的密钥，如果此密钥不支持编码，则返回 null。 
		return keyGen.generateKey().getEncoded();
	}

	private static byte[] encrypt(byte[] content, byte[] keyBytes) {
		byte[] encryptedText = null;
		if (!isInited) { 
			init();
		}
		/**
		*类 SecretKeySpec
		*可以使用此类来根据一个字节数组构造一个 SecretKey，
		*而无须通过一个（基于 provider 的）SecretKeyFactory。
		*此类仅对能表示为一个字节数组并且没有任何与之相关联的钥参数的原始密钥有用 
		*构造方法根据给定的字节数组构造一个密钥。
		*此构造方法不检查给定的字节数组是否指定了一个算法的密钥。
		*/
		Key key = new SecretKeySpec(keyBytes, "AES");
		try {
		        // 用密钥初始化此 cipher。
			cipher.init(Cipher.ENCRYPT_MODE, key);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}
		try {
		        //按单部分操作加密或解密数据，或者结束一个多部分操作。(不知道神马意思)
			encryptedText = cipher.doFinal(content);
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return encryptedText;
	}

	private static byte[] encrypt(String content, String password) {
		try {
			byte[] keyStr = getKey(password);
			SecretKeySpec key = new SecretKeySpec(keyStr, "AES");
			Cipher cipher = Cipher.getInstance(algorithmStr);//algorithmStr
			byte[] byteContent = content.getBytes("utf-8");
			cipher.init(Cipher.ENCRYPT_MODE, key);//   ʼ
			byte[] result = cipher.doFinal(byteContent);
			return result; //     
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}
 
	private static byte[] decrypt(byte[] content, String password) {
		try {
			byte[] keyStr = getKey(password);
			SecretKeySpec key = new SecretKeySpec(keyStr, "AES");
			Cipher cipher = Cipher.getInstance(algorithmStr);//algorithmStr
			cipher.init(Cipher.DECRYPT_MODE, key);//   ʼ
			byte[] result = cipher.doFinal(content);
			return result; //     
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static byte[] getKey(String password) {
		byte[] rByte = null;
		if (password!=null) {
			rByte = password.getBytes();
		}else{
			rByte = new byte[24];
		}
		return rByte;
	}

	/**
	 * 将二进制转换成16进制
	 * @param buf
	 * @return
	 */
	public static String parseByte2HexStr(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

	/**
	 * 将16进制转换为二进制
	 * @param hexStr
	 * @return
	 */
	public static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1)
			return null; 
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2),
					16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}
	
        //注意: 这里的password(秘钥必须是16位的)
	private static final String keyBytes = "abcdefgabcdefg12";
	
	/**
	*加密
	*/
	public static String encode(String content){
	        //加密之后的字节数组,转成16进制的字符串形式输出
		return parseByte2HexStr(encrypt(content, keyBytes));
	}
	
	/**
	*解密
	*/
	public static String decode(String content){
	        //解密之前,先将输入的字符串按照16进制转成二进制的字节数组,作为待解密的内容输入
		byte[] b = decrypt(parseHexStr2Byte(content), keyBytes);
		return new String(b);
	}
	
	//测试用例
	public static void test1(){
		
		String postStr = decode("893D322B038CDDDACD0CD57BE0ACAA38673477FE2578ACC11444C6ECA437E4D1920415A9E1E84AAD61FCD00753D4B5FA1598ACFA4D16E199EC9FF752EDBD470EE0F29A7567155A3D28DC21268C518F27");
		System.out.println("解密后："+ postStr );
		String jsonStr = "{\"longinTokenId\":\"041a7d164c694c498b89df1801683522\",\"loginName\":\"gk\"}";
		String encode = encode(jsonStr);
		System.out.println(encode);
		
	}

	public static String decryptToPay(String sSrc) throws Exception {
		byte[] raw = KEY.getBytes("ASCII");
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		IvParameterSpec iv = new IvParameterSpec(IV.getBytes());
		cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
		byte[] encrypted1 = Base64.decodeBase64(sSrc);//先用bAES64解密
		try {
			byte[] original = cipher.doFinal(encrypted1);
			String originalString = new String(original);
			return originalString;
		} catch (Exception e) {
			return null;
		}
	}
	public static void main(String[] args) throws Exception {
		String stra = "7B5BE1C1BF2E1C2C4C3E01402701C7992F7A47769151132D400A7174F2432575B7E24E7EBFD9215127923CA795A54615EB96EB5245955C3CC894816D2A424140";
		String str = decode(stra);
		System.out.println(str);
	}
}