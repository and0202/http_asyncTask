package zhong.http.net.callback;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;

import zhong.http.net.AppException;
import zhong.http.net.AppException.ExceptionType;
import zhong.http.net.interfaces.ICallBack;
import zhong.http.net.interfaces.IProgressListener;
import zhong.http.net.utils.TextUtil;

public abstract class AbstractCallback<T> implements ICallBack<T> {
	private static final int IO_BUFFER_SIZE = 4 * 1024;
	public String path;
	public Class mReturnClass;
	public Type mReturnType;
	private boolean isCancelled;

	@Override
	public void checkIfCancelled() throws AppException{
		if (isCancelled) {
			throw new AppException(ExceptionType.NormalException,"request has been cancelled");
		}
	}

	@Override
	public T onPreHandle(T t) {
		return t;
	}


	
	public T handle(HttpResponse httpResponse, IProgressListener iProgressListener) throws AppException {
		checkIfCancelled();
		// File, Bitmap,String,json,xml
		try {
			HttpEntity entity = httpResponse.getEntity();
			switch (httpResponse.getStatusLine().getStatusCode()) {
			case HttpStatus.SC_OK:
				if (TextUtil.isUseable(path)) {
					FileOutputStream foStream = new FileOutputStream(path);
					InputStream inputStream = null;
					if (entity.getContentEncoding() != null) {
						String encodingString = entity.getContentEncoding().getValue();
						if (TextUtil.isUseable(encodingString) && "gzip".equalsIgnoreCase(encodingString)) {
							inputStream = new GZIPInputStream(entity.getContent());
						} else if (TextUtil.isUseable(encodingString) && "deflate".equals(encodingString)) {
							inputStream = new InflaterInputStream(entity.getContent());
						}
					} else {
						inputStream = entity.getContent();
					}
					byte[] bytes = new byte[IO_BUFFER_SIZE];
					int read;
					long currentPos = 0;
					long length = entity.getContentLength();
					while ((read = inputStream.read(bytes)) != -1) {
						checkIfCancelled();
						if (iProgressListener != null) {
							currentPos += read;
							iProgressListener.onProgressUpdate((int) currentPos/1024, (int) length/1024);
						}
						foStream.write(bytes, 0, read);
					}
					foStream.flush();
					foStream.close();
					inputStream.close();
					return bindData(path);
				} else {
					return bindData(EntityUtils.toString(entity));
				}
			default:
				break;
			}
		} catch (ParseException e) {
			throw new AppException(ExceptionType.ParseException, e.getMessage());
		} catch (IOException e) {
			throw new AppException(ExceptionType.IOException, e.getMessage());
		}
		return null;
	}

	/**
	 * 解析服务器返回得数据
	 * 
	 * @param content
	 * @return
	 */
	protected T bindData(String content)  throws AppException{
		checkIfCancelled();
		return null;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public AbstractCallback<T> setReturnClass(Class<T> clz) {
		this.mReturnClass = clz;
		return this;
	}

	public AbstractCallback<T> setReturnType(Type type) {
		this.mReturnType = type;
		return this;
	}

	@Override
	public void cancel() {
		isCancelled = true;
	}
}
