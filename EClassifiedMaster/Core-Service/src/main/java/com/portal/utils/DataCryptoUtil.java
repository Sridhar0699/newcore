package com.portal.utils;

import java.security.Key;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Service;

@Service(value = "DataCryptoUtil")
public class DataCryptoUtil {

	private static final String ALGO = "AES";

	public static String encodeBase64String(byte[] bytes) {
		return new String(java.util.Base64.getEncoder().encode(bytes));
	}

	public static byte[] decodeBase64StringTOByte(String stringData) throws Exception {
		return java.util.Base64.getDecoder().decode(stringData.getBytes("UTF-8"));
	}

	public static String encrypt(String Data, String hash) throws Exception {
		Key key = generateKey(hash);
		Cipher c = Cipher.getInstance(ALGO);
		c.init(Cipher.ENCRYPT_MODE, key);
		byte[] encVal = c.doFinal(Data.getBytes());
		String encryptedValue = encodeBase64String(encVal);
		return encryptedValue;
	}

	public static String decrypt(String encryptedData, String hash) throws Exception {
		Key key = generateKey(hash);
		Cipher c = Cipher.getInstance(ALGO);
		c.init(Cipher.DECRYPT_MODE, key);
		byte[] decordedValue = decodeBase64StringTOByte(encryptedData);
		byte[] decValue = c.doFinal(decordedValue);
		String decryptedValue = new String(decValue);
		return decryptedValue;
	}

	private static Key generateKey(String hash) throws Exception {
		if (hash.length() > 16)
			hash = hash.substring(0, 16);
		Key key = new SecretKeySpec(hash.getBytes(), ALGO);
		return key;
	}

	public Map<String, String> encrypt(Map<String, String> map, String hash) {
		Map<String, String> tempMap = new HashMap<String, String>();
		for (Map.Entry<String, String> obj : map.entrySet()) {
			try {
				tempMap.put(obj.getKey(), encrypt(obj.getValue(), hash));
			} catch (Exception e) {
			}
		}
		return tempMap;
	}

	public Map<String, Object> decrypt(Map<String, Object> map, String hash) {
		Map<String, Object> tempMap = new HashMap<String, Object>();
		for (Map.Entry<String, Object> obj : map.entrySet()) {
			try {
				if (obj.getValue() != null)
					tempMap.put(obj.getKey(), decrypt(obj.getValue() + "", hash));
			} catch (Exception e) {
				e.getMessage();
				tempMap.put(obj.getKey(), obj.getValue() + "");
			}
		}
		return tempMap;
	}
	
}
