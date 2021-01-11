package com.jgrcb.sample.aop;

import org.springframework.stereotype.Service;

import cn.xdaoy.common.aop.AopPointer;

@Service
public class AopTarget {

	@AopPointer("sample")
	public String test() {
		int a = 0;
		a++;
		System.out.println(a);
		return String.valueOf(a);
	}
}
