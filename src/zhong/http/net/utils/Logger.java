package zhong.http.net.utils;

import android.util.Log;

;

/**
 * @author zhongkun
 * @version 1.0
 */
public final class Logger {
	/**
	 * 日志开关
	 */
	public static final boolean DEBUG_LOG = true;
	private final static String Tag = "lincoln";

	public static void d(String tag, String msg) {
		if (DEBUG_LOG) {
			if (msg != null && !msg.equals(""))
				Log.d(tag, msg);
		}
	}

	public static void d(String msg) {
		if (DEBUG_LOG) {
			if (msg != null && !msg.equals(""))
				Log.d(Tag, msg);
		}
	}

}
