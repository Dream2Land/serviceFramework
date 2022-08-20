package cn.xdaoy.sample.aop;

import org.springframework.stereotype.Component;

import cn.xdaoy.common.aop.AopReciever;

@Component
public class AopEventer implements AopReciever {

	public String pointer() {
		return "sample";
	}

	public void before(Object[] args) {
		System.out.println("before");
		
	}

	public void after(Object[] args) {
		System.out.println("after");
	}

	public void afterReturning(Object obj) {
		System.out.println("after returning ,result:"+obj);
		
	}

	public void afterThrowing(Throwable ex) {
		System.out.println("after throw");
		
	}

	public void aroundBefore(Object obj) {
		System.out.println("around before");
		
	}

	public void aroundAfter(Object obj) {
		System.out.println("around after");
		
	}

}
