package cn.itcast.shop.utils;

import java.util.UUID;

/**
 * 生成随机字符串的工具类
 * @author admin
 *
 */
public class UUIDUtils {
	/**
	 * 生成随机字符串(32位)
	 * @return
	 */
	public static String getUUID(){
		/* toString()生成字符中存在  - 字符
		 * 使用replace("-", "")去除  - 字符
		 * 去除 - 字符，整体长度为32位		*/ 
		return UUID.randomUUID().toString().replace("-", "");
	}
}
