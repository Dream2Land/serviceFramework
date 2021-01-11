package cn.xdaoy.common.aop;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import cn.xdaoy.utils.StringUtils;



@Aspect
@Component
@EnableAspectJAutoProxy(proxyTargetClass = false, exposeProxy = true)
public class CommAopPoint {
	
	private static final Logger logger = LoggerFactory.getLogger(CommAopPoint.class);
	
	private static Map<String,Set<AopReciever>> map = new HashMap<>();
	
	public static void regReciever(final AopReciever reciever) {
		logger.info("==>registe aop reciever："+reciever.pointer());
		if(StringUtils.isEmpty(reciever.pointer())) {
			return;
		}
		Set<AopReciever> set = map.get(reciever.pointer());
		if(null == set || set.isEmpty()) {
			addReciever(reciever);
		}else {
			set.add(reciever);
		}
	}
	
	private static synchronized void addReciever(final AopReciever reciever) {
		Set<AopReciever> s = map.get(reciever.pointer());
		if(null != s && !s.isEmpty()) {
			s.add(reciever);
			return;
		}
		Set<AopReciever> set = new HashSet<>();
		set.add(reciever);
		map.put(reciever.pointer(), set);
	}
	

	@Pointcut("@annotation(cn.xdaoy.common.aop.AopPointer)")
	private void aopPointer() {};
	
	@Before("aopPointer() && @annotation(pointer)")
	public void before(JoinPoint point,AopPointer pointer) {
		logger.info("==>aop before："+pointer.value());
		if(StringUtils.isEmpty(pointer.value())) {
			return;
		}
		Set<AopReciever> set = map.get(pointer.value());
		if(null == set || set.isEmpty()) {
			return;
		}
		for(AopReciever r : set) {
			r.before(point.getArgs());
		}
	}
	
	@After("aopPointer() && @annotation(pointer)")
	public void after(JoinPoint point,AopPointer pointer) {
		logger.info("==>aop after："+pointer.value());
		if(StringUtils.isEmpty(pointer.value())) {
			return;
		}
		Set<AopReciever> set = map.get(pointer.value());
		if(null == set || set.isEmpty()) {
			return;
		}
		for(AopReciever r : set) {
			r.after(point.getArgs());
		}
	}
	
	@AfterReturning(returning="rvt",pointcut="aopPointer() && @annotation(pointer)")
	public void afterReturning(Object rvt,AopPointer pointer) {
		logger.info("==>aop afterReturning："+pointer.value());
		if(StringUtils.isEmpty(pointer.value())) {
			return;
		}
		Set<AopReciever> set = map.get(pointer.value());
		if(null == set || set.isEmpty()) {
			return;
		}
		for(AopReciever r : set) {
			r.afterReturning(rvt);
		}
	}
	
	@AfterThrowing(throwing="ex",pointcut="aopPointer() && @annotation(pointer)")
	public void afterThrowing(Throwable ex,AopPointer pointer) {
		logger.info("==>aop afterThrowing："+pointer.value());
		logger.error("==>aop afterThrowing error ：{}", ex);
		if(StringUtils.isEmpty(pointer.value())) {
			return;
		}
		Set<AopReciever> set = map.get(pointer.value());
		if(null == set || set.isEmpty()) {
			return;
		}
		for(AopReciever r : set) {
			r.afterThrowing(ex);
		}
	}
	
	@Around("aopPointer() && @annotation(pointer)")
	public Object around(ProceedingJoinPoint point,AopPointer pointer) {
		logger.info("==>aop around："+pointer.value());
		if(StringUtils.isEmpty(pointer.value())) {
			
		}
		Set<AopReciever> set = map.get(pointer.value());
		if(null == set || set.isEmpty()) {
			try {
				return point.proceed(point.getArgs());
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
		for(AopReciever r : set) {
			r.aroundBefore(point.getArgs());
		}
		Object rvt = null;
		try {
			rvt= point.proceed(point.getArgs());
		} catch (Throwable e) {
			e.printStackTrace();
		}
		for(AopReciever r : set) {
			r.aroundAfter(point.getArgs());
		}
		return rvt;
	}
	
}
