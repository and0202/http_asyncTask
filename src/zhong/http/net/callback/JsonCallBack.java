package zhong.http.net.callback;

import zhong.http.net.AppException;
import zhong.http.net.parser.json.WeatherInfoParser;
import zhong.http.net.utils.IOUtil;
import zhong.http.net.utils.TextUtil;

public abstract class JsonCallBack<T> extends AbstractCallback<T> {

	@Override
	protected T bindData(String content) throws AppException {
		if (TextUtil.isUseable(path)) {
			String contentString= IOUtil.readFromFile(path);
			return (T) (WeatherInfoParser.getWeatherInfoList(contentString));

		}
		return null;
	}
}
