package zhong.http.net;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;

import zhong.http.net.callback.AbstractCallback;
import zhong.http.net.interfaces.ICallBack;
import zhong.http.net.interfaces.IProgressListener;

public class Request {
	public enum RequestMethod{
		GET,POST,DELETE,PUT
	}
	public static final String ENCODING = "UTF-8";
	public RequestMethod method;
	public String urlString;
	public String postContent;
	public Map<String, String> headers;
	public HttpEntity entity;
	public ICallBack callBack;
	public IProgressListener iProgressListener;
	
	
	public Request(String url,RequestMethod method){
		this.urlString = url;
		this.method = method;
	}
	
	public void setEntity(List<NameValuePair> list){
		try {
			entity = new UrlEncodedFormEntity(list,ENCODING);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	public void setEntity(String contentString){
		try {
			entity = new StringEntity(contentString, ENCODING);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	public void setEntity(byte[] bytes){
		entity = new ByteArrayEntity(bytes);
	}
	
	public void setICallBack(ICallBack iCallBack){
		this.callBack = iCallBack;
	}
	
	public void execute(){
		RequestTask task = new RequestTask(this);
		task.execute();
	}

	public void setProgressListener(IProgressListener iProgressListener2) {
		this.iProgressListener = iProgressListener2;
	}

	public void cancel() {
		if (callBack != null) {
			callBack.cancel();
		}
	}
}
