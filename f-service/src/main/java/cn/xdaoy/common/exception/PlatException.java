package cn.xdaoy.common.exception;

public class PlatException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	private String errorCode;
	
	private String errorMessage;

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public PlatException(String errorCode, String errorMessage) {
		super(errorMessage);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
	
	public PlatException(String errorCode, String errorMessage,Throwable cause) {
		super(errorMessage,cause);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

}
