package com.huboot.commons.utils;

import org.springframework.util.StringUtils;

/**
 * 表情符号处理
 */
public class EmojiFilterUtil {
	 
	private static boolean isNotEmojiCharacter(char codePoint) {
		return (codePoint == 0x0) ||
				(codePoint == 0x9) ||
				(codePoint == 0xA) ||
				(codePoint == 0xD) ||
				((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
				((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) ||
				((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
	}
	 
	/**
	 * 过滤emoji 或者 其他非文字类型的字符
	 * @param source
	 * @return
	 */
	public static String filterEmoji(String source) {
		if (StringUtils.isEmpty(source)) {
			return source;
		}
		//到这里铁定包含
		StringBuilder buf = null;

		int len = source.length();

		for (int i = 0; i < len; i++) {
			char codePoint = source.charAt(i);

			if (isNotEmojiCharacter(codePoint)) {
				if (buf == null) {
					buf = new StringBuilder(source.length());
				}

				buf.append(codePoint);
			}
		}

		if (buf == null) {
			return "";//如果没有找到 emoji表情，则返回源字符串
		} else {
			if (buf.length() == len) {//这里的意义在于尽可能少的toString，因为会重新生成字符串
				buf = null;
				return source;
			} else {
				return buf.toString();
			}
		}

	}

	public static void main(String[] args){
		System.out.println(filterEmoji("中文dfgdf💬😍"));
	}
}
