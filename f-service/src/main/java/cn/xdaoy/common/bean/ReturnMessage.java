package cn.xdaoy.common.bean;

import java.io.Serializable;

import com.google.gson.JsonObject;

import cn.xdaoy.utils.StringUtils;

public class ReturnMessage<T> implements Serializable{
	
	private static final long serialVersionUID = 1L;

	public static final String SUCCESS = "0000";
	
	private String code;
	
	private String message;
	
	private T data;

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	private ReturnMessage(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	private ReturnMessage(String code, String message, T data) {
		super();
		this.code = code;
		this.message = message;
		this.data = data;
	}
	
	
	public static <T> ReturnMessage<T> successMsg() {
		return new ReturnMessage<>(SUCCESS,"成功");
	}
	
	public static <T> ReturnMessage<T> successMsg(T data) {
		return new ReturnMessage<>(SUCCESS,"成功",data);
	}
	
	public static <T> ReturnMessage<T> successMsg(String message) {
		return new ReturnMessage<>(SUCCESS,message);
	}
	
	public static <T> ReturnMessage<T> successMsg(String message,T data) {
		return new ReturnMessage<>(SUCCESS,message,data);
	}
	
	public static <T> ReturnMessage<T> failMsg(String code,String message) {
		return new ReturnMessage<>(code,message);
	}
	
	public static <T> ReturnMessage<T> failMsg(String code,String message,T data) {
		return new ReturnMessage<>(code,message,data);
	}
	
	public boolean isSuccess() {
		if(StringUtils.isEmpty(this.code)) {
			return false;
		}
		return SUCCESS.equals(this.code);
	}
	
	public static boolean isSuccess(JsonObject json) {
		if(StringUtils.isEmpty(json.get("code").getAsString())) {
			return false;
		}
		return SUCCESS.equals(json.get("code").getAsString());
	}
	
}
