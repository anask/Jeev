package edu.umkc.sce_med.fh504;

import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import android.util.Base64;

public class CopyOfAESCrypto {

	Cipher ecipher;
	Cipher dcipher;
	// 8-byte Salt
	private static byte[] salt = {(byte) 0xC8, (byte) 0x14A, (byte) 0xD5, (byte) 0x230,(byte) 0x6A7, (byte) 0x7DE, (byte) 0x6FF, (byte) 0xC3 };
	int iterationCount = 200;

	 public CopyOfAESCrypto (String passPhrase) {
	    try {

	        // Create the key
	        KeySpec keySpec = new PBEKeySpec(passPhrase.toCharArray(), salt, iterationCount);
	        SecretKey key = SecretKeyFactory.getInstance(
	                "PBEWithSHA256And256BitAES-CBC-BC").generateSecret(keySpec);
	        ecipher = Cipher.getInstance(key.getAlgorithm());
	        dcipher = Cipher.getInstance(key.getAlgorithm());

	        // Prepare the parameter to the ciphers
	        AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt, iterationCount);

	        // Create the ciphers
	        ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
	        dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
	    } catch (Exception e) { e.printStackTrace(); }
	}

	public String encrypt(String str) {
	    try {
	        // Encode the string into bytes using utf-8
	        byte[] utf8 = str.getBytes("UTF8");

	        // Encrypt
	        byte[] enc = ecipher.doFinal(utf8);

	        // Encode bytes to base64 to get a string
	        return Base64.encodeToString(enc, Base64.DEFAULT);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
	}

	public String decrypt(String str) {
	    try {
	        // Decode base64 to get bytes
	        byte[] dec = Base64.decode(str, Base64.DEFAULT);

	        // Decrypt
	        byte[] utf8 = dcipher.doFinal(dec);

	        // Decode using utf-8
	        return new String(utf8, "UTF8");
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
	}

	public int getIterationCount() {
	    return iterationCount;
	}

	public String getSalt() {
	    return Base64.encodeToString(salt, Base64.DEFAULT);
	}
	

}