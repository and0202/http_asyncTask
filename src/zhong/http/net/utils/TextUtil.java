package zhong.http.net.utils;

public class TextUtil {
	/**
	 * 判断字符串是否可用
	 * 
	 * @param content
	 * @return
	 */
	public static boolean isUseable(String content) {
		return content != null && !content.equals("") && !content.equals("null");
	}
}
