package zhong.http.net;

public class AppException extends Exception{
	
	/**
	 *异常类型
	 */
	public enum ExceptionType{
		NormalException, ParseException, IOException, ClientProtocolException, UnsupportedEncodingException,
	}

	private ExceptionType type;
	private String detailMessageString;
	public AppException(ExceptionType ExceptionType,String detailMessage) {
		super(detailMessage);
		this.type = ExceptionType;
		this.detailMessageString = detailMessage;
	}
	
}
