package com.example.zhong_http;

import java.io.File;
import java.util.ArrayList;

import zhong.http.net.Request;
import zhong.http.net.Request.RequestMethod;
import zhong.http.net.bean.WeatherInfoBean;
import zhong.http.net.callback.AbstractCallback;
import zhong.http.net.callback.FileCallBack;
import zhong.http.net.callback.JsonCallBack;
import zhong.http.net.callback.StringCallBack;
import zhong.http.net.interfaces.IProgressListener;
import zhong.http.net.utils.Logger;
import android.R.color;
import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {
	private TextView textView;
	private ProgressBar progressBar;
	private Button stringBtn,jsonBtn,imgBtn;
	private final String string_url = "http://www.baidu.com";
	private final String string_json_url = "http://m.weather.com.cn/data/101250101.html";
	private final String file_img_url = "http://www.sinaimg.cn/qc/photo_auto/photo/78/91/40067891/40067891_src.jpg";
	private final String file_name_json = Environment.getExternalStorageDirectory() + File.separator + "file_json.txt";
	private final String file_name_string = Environment.getExternalStorageDirectory() + File.separator + "file_string.txt";
	private final String file_name_img = Environment.getExternalStorageDirectory() + File.separator + "file_string.txt";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initview();
	}
	
	private void initview() {
		textView = (TextView) findViewById(R.id.textView);
		progressBar = (ProgressBar) findViewById(R.id.progressBar1);
		stringBtn = (Button)findViewById(R.id.getString);
		jsonBtn = (Button)findViewById(R.id.getJson);
		imgBtn = (Button)findViewById(R.id.getImg);
		
		stringBtn.setOnClickListener(this);
		jsonBtn.setOnClickListener(this);
		imgBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.getString:
			getString();
			break;
		case R.id.getJson:
			getJson();
			break;
		case R.id.getImg:
			getImg();
			break;
		}
	}

	/**
	 * 获得字符串
	 */
	public void getString() {
		final Request request = new Request(string_url, RequestMethod.GET);
		AbstractCallback abstractCallback = new StringCallBack() {

			@Override
			public void onSuccess(Object result) {
				Log.d("lincoln", "onSuccess:" + result);
				if (result instanceof String) {
					textView.setText((String) result);
				}
			}

			@Override
			public void onFailed(Exception result) {
				result.printStackTrace();
			}

			@Override
			public Object onPreHandle(Object t) {
				return t;
			}
		};
		abstractCallback.setPath(file_name_string);
		request.setICallBack(abstractCallback);
		request.execute();
	}

	/**
	 * 获得json数据
	 */
	public void getJson() {
		Request request = new Request(string_json_url, RequestMethod.GET);
		AbstractCallback abstractCallback = new JsonCallBack<ArrayList<WeatherInfoBean>>() {

			@Override
			public void onSuccess(ArrayList<WeatherInfoBean> result) {
				Log.d("lincoln", "onSuccess:" + result);
				if (result == null) {
					return;
				}
				textView.setText(result.toString());
			}

			@Override
			public void onFailed(Exception result) {
				result.printStackTrace();
			}

			@Override
			public ArrayList<WeatherInfoBean> onPreHandle(ArrayList<WeatherInfoBean> t) {
				// insert data to database
				return t;
			}
		};
		Logger.d("file_name_string:" + file_name_string);
		abstractCallback.setPath(file_name_json);
		request.setICallBack(abstractCallback);
		request.execute();
	}

	/**
	 *获得图片
	 */
	public void getImg() {
		Request request = new Request(file_img_url, RequestMethod.GET);
		AbstractCallback abstractCallback = new FileCallBack() {

			@Override
			public void onSuccess(Object result) {
			}

			@Override
			public void onFailed(Exception result) {
			}
		};
		request.setProgressListener(new IProgressListener() {

			@Override
			public void onProgressUpdate(int currentPos, int contentLenght) {
				final int precent = currentPos * 100 / contentLenght;
				Logger.d("precent:" + precent + " currentPos:" + currentPos + " contentLenght:" + contentLenght);

				runOnUiThread(new Runnable() {
					public void run() {
						progressBar.setProgress(precent);
					}
				});
			}
		});
		abstractCallback.setPath(file_name_img);
		request.setICallBack(abstractCallback);
		request.execute();

	}
}
