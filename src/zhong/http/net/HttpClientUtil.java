package zhong.http.net;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import zhong.http.net.AppException.ExceptionType;

public class HttpClientUtil {
	public static HttpResponse execute(Request request) throws AppException {
		switch (request.method) {
		case GET:
			return get(request);
		case POST:
			return post(request);
		default:
			throw new AppException(ExceptionType.NormalException, "The method " + request.method.name() + "not supported");
		}
	}

	public static HttpResponse post(Request request) throws AppException {
		try {
			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost();
			addHeader(post, request.headers);
			if (request.entity == null) {
				throw new IllegalStateException("you forget to set the content to the httpPost");
			} else {
				post.setEntity(request.entity);
			}
			post.setEntity(new StringEntity(request.postContent));
			HttpResponse httpResponse = client.execute(post);
			return httpResponse;
		} catch (UnsupportedEncodingException e) {
			throw new AppException(ExceptionType.UnsupportedEncodingException, e.getMessage());
		} catch (ClientProtocolException e) {
			throw new AppException(ExceptionType.ClientProtocolException, e.getMessage());
		} catch (IOException e) {
			throw new AppException(ExceptionType.IOException, e.getMessage());

		}

	}

	public static HttpResponse get(Request request) throws AppException {
		try {
			HttpClient client = new DefaultHttpClient();
			HttpGet get = new HttpGet(request.urlString);
			addHeader(get, request.headers);
			HttpResponse httpResponse = client.execute(get);
			return httpResponse;
		} catch (ClientProtocolException e) {
			throw new AppException(ExceptionType.ClientProtocolException, e.getMessage());
		} catch (IOException e) {
			throw new AppException(ExceptionType.IOException, e.getMessage());
		}
	}

	public static void addHeader(HttpUriRequest httpUriRequest, Map<String, String> headers) {
		if (headers != null && headers.size() > 0) {
			for (Entry<String, String> entry : headers.entrySet()) {
				httpUriRequest.addHeader(entry.getKey(), entry.getValue());
			}
		}
	}

}
