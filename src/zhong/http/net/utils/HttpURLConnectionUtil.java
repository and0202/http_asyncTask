package zhong.http.net.utils;

import java.net.HttpURLConnection;

import zhong.http.net.AppException;
import zhong.http.net.AppException.ExceptionType;
import zhong.http.net.Request;

public class HttpURLConnectionUtil {

	public static HttpURLConnection execute(Request request) throws AppException {
		switch (request.method) {
		case GET:
			return get(request);
		case POST:

		case PUT:

		default:
			throw new AppException(ExceptionType.NormalException, "the " + request.method + " doesn't support");
		}
	}

	private static HttpURLConnection get(Request request) {
		return null;
	}
}
