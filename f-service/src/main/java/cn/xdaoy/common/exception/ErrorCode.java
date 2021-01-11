package cn.xdaoy.common.exception;

public class ErrorCode {

	public static class ChnlError{
		public static final String requestParamError = "A0001";
		public static final String requestNoPermission = "A0002";
		public static final String orderNotExists = "A0003";
		public static final String orderExists = "A0004";
		public static final String userNotExists = "A0005";
		public static final String userExists = "A0006";
		public static final String userNotLogined = "A0007";
		public static final String authFailure = "A0008";
		public static final String userNotAuthed = "A0009";
		public static final String fileNotSupport = "A9001";
		
	}
	
	public static class PlatError{
		public static final String serviceFail = "B0000";
		public static final String unknowSysError = "B0001";
		public static final String serviceNotExists = "B0002";
		public static final String serviceBusing = "B0003";
		public static final String methodParamInvalid = "B1001";
		public static final String recordNotExists = "B1001";
	}
	
	public static class CorpError{
		public static final String corpResponseFail  = "C0000";
		public static final String corpResponseError = "C0002";
		public static final String corpRequestTimeout = "C0003";
	}
}
