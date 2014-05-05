package zhong.http.net;

import org.apache.http.HttpResponse;

import zhong.http.net.interfaces.IProgressListener;

import android.R.bool;
import android.os.AsyncTask;

public class RequestTask extends AsyncTask<Object, Integer, Object> {
	Request request;

	public RequestTask(Request request) {
		super();
		this.request = request;
	}

	@Override
	protected Object doInBackground(Object... params) {
		try {
			Object object = null;
			HttpResponse httpResponse = HttpClientUtil.execute(request);
			if (request.iProgressListener != null) {
				object =  request.callBack.handle(httpResponse, new IProgressListener() {
					
					@Override
					public void onProgressUpdate(int currentPos, int contentLenght) {
						publishProgress(currentPos,contentLenght);
					}
				});
			} else {
				object =  request.callBack.handle(httpResponse, null);
			}
			return request.callBack.onPreHandle(object);
		} catch (Exception e) {
			return e;
		}
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected void onPostExecute(Object result) {
		super.onPostExecute(result);
		if (result instanceof Exception) {
			request.callBack.onFailed((Exception) result);
		} else {
			request.callBack.onSuccess(result);
		}
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
		if (request.iProgressListener != null) {
			request.iProgressListener.onProgressUpdate(values[0],values[1]);
		}
	}

	@Override
	protected void onCancelled() {
		super.onCancelled();
		if (request.callBack != null) {
			request.callBack.cancel();
		}
	}
	
	
	
	

}
