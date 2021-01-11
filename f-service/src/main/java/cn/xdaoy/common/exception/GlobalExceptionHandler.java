package cn.xdaoy.common.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.google.gson.Gson;

import cn.xdaoy.common.bean.ReturnMessage;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	@ExceptionHandler(value = Exception.class)
	public Object defaultErrorHandler(HttpServletRequest req, HttpServletResponse resp, Exception e) throws Exception {
		log.error("request error", e);
		String msg = "unknow intenal error";
		if (e instanceof NoHandlerFoundException) {
			resp.setStatus(404);
		}
		if(404 == resp.getStatus()) {
			msg = "service not found";
		}if(403 == resp.getStatus()) {
			msg = "service not permissioned";
		}
		ReturnMessage<?> rt = ReturnMessage.failMsg(String.valueOf(resp.getStatus()),msg);
		resp.getWriter().append(new Gson().toJson(rt));
		return null;
	}

}
