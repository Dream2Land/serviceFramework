package com.jgrcb.sample.aop;

import org.springframework.stereotype.Component;

import cn.xdaoy.common.aop.AopReciever;

@Component
public class AopEventer implements AopReciever {

	@Override
	public String pointer() {
		return "sample";
	}

	@Override
	public void before(Object[] args) {
		System.out.println("before");
		
	}

	@Override
	public void after(Object[] args) {
		System.out.println("after");
	}

	@Override
	public void afterReturning(Object obj) {
		System.out.println("after returning ,result:"+obj);
		
	}

	@Override
	public void afterThrowing(Throwable ex) {
		System.out.println("after throw");
		
	}

	@Override
	public void aroundBefore(Object obj) {
		System.out.println("around before");
		
	}

	@Override
	public void aroundAfter(Object obj) {
		System.out.println("around after");
		
	}

}
