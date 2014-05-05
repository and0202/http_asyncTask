package zhong.http.net.bean;

import java.io.Serializable;
import java.util.ArrayList;

import zhong.http.net.parser.json.WeatherInfoParser;

public class WeatherInfoBean implements Serializable {
	private String city;
	private String date;
	private String temp1;
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTemp1() {
		return temp1;
	}
	public void setTemp1(String temp1) {
		this.temp1 = temp1;
	}
	@Override
	public String toString() {
		return "WeatherInfoBean [city=" + city + ", date=" + date + ", temp1=" + temp1 + "]";
	}
	
	
	public ArrayList<WeatherInfoBean> getList(String content){
		return WeatherInfoParser.getWeatherInfoList(content);
	}
}
