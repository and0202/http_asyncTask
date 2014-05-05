package zhong.http.net.parser.json;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import zhong.http.net.bean.WeatherInfoBean;

public class WeatherInfoParser {
	
	
	public static ArrayList<WeatherInfoBean> getWeatherInfoList(String content){
		ArrayList<WeatherInfoBean> list = new ArrayList<WeatherInfoBean>();
		try {
			JSONObject jsonObject = new JSONObject(content);
			JSONObject jsonObjectWeather = new JSONObject(jsonObject.optString("weatherinfo"));
			WeatherInfoBean weatherInfoBean = new WeatherInfoBean();
			weatherInfoBean.setCity(jsonObjectWeather.optString("city"));
			weatherInfoBean.setDate(jsonObjectWeather.optString("date_y"));
			weatherInfoBean.setTemp1(jsonObjectWeather.optString("temp1"));
			list.add(weatherInfoBean);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return list;
	}
}
