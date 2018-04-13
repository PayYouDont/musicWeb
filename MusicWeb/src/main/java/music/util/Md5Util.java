package music.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.crypto.codec.Base64;

public class Md5Util {
	public static String MD5(String s) {
		char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			byte[] strTemp = s.getBytes();
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char[] str = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[(k++)] = hexDigits[(byte0 >>> 4 & 0xF)];
				str[(k++)] = hexDigits[(byte0 & 0xF)];
			}
			return new String(str);
		} catch (Exception e) {
		}
		return null;
	}

	public static String encrypt(String str) {
		String output = "";
		char rnd = (char) (int) Math.round(Math.random() * 122.0D);

		int size = str.length();
		char[] array = new char[size + 1];
		for (int i = 0; i < size; i++) {
			array[i] = ((char) (str.charAt(i) + rnd));
		}
		array[size] = rnd;
		String hex = "";
		for (int i = 0; i < array.length; i++) {
			hex = Integer.toHexString(array[i]);

			output = output + "\\\\u" + hex;
		}
		return output;
	}

	public static String unEncrypt(String str) {
		String[] strArr = str.replaceFirst("\\\\u", "").split("\\\\u");
		int size = strArr.length;
		char[] charArr = new char[size];
		for (int i = 0; i < size; i++) {
			charArr[i] = ((char) Integer.parseInt(strArr[i], 16));
		}
		String output = "";

		char rnd = charArr[(size - 1)];
		for (int i = 0; i < size - 1; i++) {
			output = output + (char) (charArr[i] - rnd);
		}
		return output;
	}

	public static String getEncryptPassword(String password) {
		MessageDigest messageDigest = null;

		StringBuffer result = new StringBuffer("");
		byte[] args = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");

			messageDigest.update(password.getBytes("GBK"));

			args = messageDigest.digest();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException ee) {
			ee.printStackTrace();
		} finally {
			messageDigest.reset();
		}
		for (int i = 0; i < args.length; i++) {
			int v = args[i] & 0xFF;
			if (v < 16) {
				result.append(0);
			}
			result.append(Integer.toHexString(v));
		}
		return result.toString().toUpperCase();
	}

	public static String base64Encode(String pwd) throws Exception {
		byte[] bytes = pwd.getBytes("utf8");
		byte[] encodeBase64 = Base64.encode(bytes);
		return new String(encodeBase64);
	}

	public static String base64Decode(String str) throws Exception {
		byte[] bytes2 = str.getBytes("utf8");
		byte[] decodeBase64 = Base64.encode(bytes2);
		return new String(decodeBase64);
	}

	public static String encodeCode(String code, Object salt) {
		Md5PasswordEncoder md5 = new Md5PasswordEncoder();
		md5.setEncodeHashAsBase64(false);
		return md5.encodePassword(code, salt);
	}

	public static boolean checkEncode(String source, String md5Code, Object salt) {
		String encode = encodeCode(source, salt);
		return md5Code.equals(encode);
	}
}
