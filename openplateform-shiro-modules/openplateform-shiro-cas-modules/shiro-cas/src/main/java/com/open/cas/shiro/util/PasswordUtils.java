package com.open.cas.shiro.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.lang3.StringUtils;

public class PasswordUtils {

	private static final String SHA1 = "SHA-1";
	private static final String MD5 = "MD5";

	public static String encode(String plainPassword, String saltStr) {
		byte[] salt = null;
		if (StringUtils.isNotEmpty(saltStr)) {
			salt = EncodeUtils.decodeHex(saltStr);
		}
		String algorithm = MD5;
		int interations = 1;

		byte[] hashPassword = null;
		if (SHA1.equalsIgnoreCase(algorithm)) {
			hashPassword = DigestUtils.sha1(plainPassword.getBytes(), salt, interations);
		} else if (MD5.equalsIgnoreCase(algorithm)) {
			hashPassword = DigestUtils.md5(plainPassword.getBytes(), salt, interations);
		}

		return EncodeUtils.encodeHex(hashPassword);
	}

	public static String encode(String plainPassword) {
		return encode(plainPassword, null);
	}

	/**
	 * MD5 加密
	 * 
	 * @param String info 需要MD5加密的字符串
	 * @return String result MD5加密后返回的结果
	 */
	public static String encryptToMD5(byte[] info) {
		// MessageDegist计算摘要后 得到的是Byte数组
		byte[] digesta = null;
		try {
			// 获取消息摘要MessageDigest抽象类的实例
			MessageDigest mDigest = MessageDigest.getInstance("MD5");
			// 添加需要进行计算摘要的对象（字节数组）
			mDigest.update(info);
			// 计算摘要
			digesta = mDigest.digest();
		} catch (NoSuchAlgorithmException e) {
			// TODO: handle exception
			//if (Consts.DEBUG) e.printStackTrace();
		}

		// 将字节数组转换为String并返回

		return bytes2Hex(digesta);
	}

	/**
	 * 将2进制字节数组转换为16进制字符串
	 * 
	 * @param byte[] bytes 字节数组
	 * @return String hex 返回16进制内容的字符串，比较类似UDB的密钥
	 */
	public static String bytes2Hex(byte[] bytes) {
		// 16进制结果
		String hex = "";
		// 存放byte字节对象的临时变量
		String temp = "";

		// 对字节数组元素进行处理
		for (int i = 0; i < bytes.length; i++) {
			// byte的取值范围是从-127-128，需要& 0xFF (11111111) 使得byte原来的负值变成正的
			temp = Integer.toHexString(bytes[i] & 0xFF);
			// 长度为1，那么需要补充一位 0
			if (temp.length() == 1) {
				hex = hex + "0" + temp;
			} else {
				// 长度为2，直接拼接即可
				hex = hex + temp;
			}
		}
		// 返回大写的字符串
		return hex.toUpperCase();
	}

	/**
	 * 后台用户密码加密
	 * @param loginName
	 * @param password
	 * @return
	 * @author liuheng
	 * @date 2015年8月26日 下午3:20:30
	 */
	public static String encryptAdminUserPass(String loginName, String password) {
		String salt = EncodeUtils.encodeHex(loginName.getBytes());
		return encode(password, salt);
	}

	public static void main(String[] args) {
		String plainPassword = "123456";
		String salt = EncodeUtils.encodeHex("martin".getBytes());
		System.out.println(salt);//e10adc3949ba59abbe56e057f20f883e, a66abb5684c45962d887564f08346e8d

		System.out.println(encode(plainPassword, salt));
		//passwordMD5 = {java.lang.String@830046769144}"E10ADC3949BA59ABBE56E057F20F883E"
	}
}
