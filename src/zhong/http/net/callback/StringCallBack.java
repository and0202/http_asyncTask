package zhong.http.net.callback;

import zhong.http.net.utils.IOUtil;
import zhong.http.net.utils.TextUtil;

public abstract class StringCallBack extends AbstractCallback {
	@Override
	protected Object bindData(String content) {
		if (TextUtil.isUseable(path)) {
			//读取文件，返回内容
			return IOUtil.readFromFile(path);
		}else {
			return content;
		}
	}

}
