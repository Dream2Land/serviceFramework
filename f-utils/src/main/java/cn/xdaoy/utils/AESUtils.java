package cn.xdaoy.utils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class AESUtils {

	private static final String KEY_ALGORITHM = "AES";
	private static final String DEFAULT_CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";//CBC prefered
	private static byte[] IV = {-25,58,-85,-14,59,-24,-33,98,-127,-93,-28,69,1,-24,16,-113};//CBC recommend iv for factor
	
//	static{
//		try {
//			IV = SecureRandom.getInstance("SHA1PRNG").generateSeed(16);
//		} catch (NoSuchAlgorithmException e) {
//			e.printStackTrace();
//		}
//	}
	
	/**
	 * AES encrypt
	 *
	 * @param content
	 * @param password
	 * @return 
	 * @throws Exception 
	 */
	public static String encrypt(String content, String password) throws Exception {
		return Base64.encodeBase64String(encrypt(content.getBytes("utf-8"),password));
	}
	
	/**
	 * AES encrypt
	 *  
	 * @param data
	 * @param password
	 * @return
	 */
	public static byte[] encrypt(byte[] data, String password) {
		try {
			Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(password),new IvParameterSpec(IV));
			return cipher.doFinal(data);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * AES decrypt
	 *
	 * @param content
	 * @param password
	 * @return
	 */
	public static String decrypt(String content, String password) throws Exception{
		return new String(decrypt(Base64.decodeBase64(content),password), "utf-8");
	}
	
	/**
	 * AES decrypt
	 * @param data
	 * @param password
	 * @return
	 */
	public static byte[] decrypt(byte[] data, String password) {
		try {
			Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, getSecretKey(password),new IvParameterSpec(IV));
			return cipher.doFinal(data);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * generate key
	 * 
	 * @param password
	 * @return
	 */
	private static SecretKeySpec getSecretKey(final String password) {
		KeyGenerator kg = null;
		try {
			kg = KeyGenerator.getInstance(KEY_ALGORITHM);
			// for linux random is't same issue
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			random.setSeed(password.getBytes());
			// AES recommend 128
			kg.init(128, random);
			SecretKey secretKey = kg.generateKey();
			return new SecretKeySpec(secretKey.getEncoded(), KEY_ALGORITHM);
		} catch (NoSuchAlgorithmException ex) {
			ex.printStackTrace();
		}
		return null;
	}


}
