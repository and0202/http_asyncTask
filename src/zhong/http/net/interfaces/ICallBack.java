package zhong.http.net.interfaces;

import org.apache.http.HttpResponse;

import zhong.http.net.AppException;

public interface ICallBack<T> {
	/**
	 * 网络请求失败回调方法
	 * 
	 * @param result
	 */
	void onFailed(Exception result);

	/**
	 * 网络请求成功回调方法
	 * 
	 * @param result
	 */
	void onSuccess(T result);

	/**
	 * 处理网络请求返回的结果
	 * 
	 * @param httpResponse
	 * @param iProgressListener
	 * @return
	 * @throws AppException
	 */
	T handle(HttpResponse httpResponse, IProgressListener iProgressListener) throws AppException;

	/**
	 * 检查是否取消了网络请求
	 * 
	 * @throws AppException
	 */
	void checkIfCancelled() throws AppException;

	/**
	 * 取消网络请求
	 */
	void cancel();

	/**
	 * 对返回的结果进行处理后再返回
	 * @param t
	 * @return
	 */
	T onPreHandle(T t);
}
