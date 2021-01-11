package cn.xdaoy.common.aop;

import javax.annotation.PostConstruct;

/**
 * aop event reciever
 * 
 * @author xdtand
 *
 */
public interface AopReciever {
	
	@PostConstruct
	default void init() {
		CommAopPoint.regReciever(this);
	};

	/**
	 * pointer
	 * @return
	 */
	String pointer();
	
	/**
	 * method before
	 * @param args
	 */
	void before(Object[] args);
	
	/**
	 * method after
	 * @param args
	 */
	void after(Object[] args);
	
	/**
	 * method returning
	 * @param obj
	 */
	void afterReturning(Object obj);
	
	/**
	 * method throwing
	 * @param ex
	 */
	void afterThrowing(Throwable ex);
	
	/**
	 * method around before,composite with aroundAfter
	 * @param args
	 */
	void aroundBefore(Object obj);
	
	/**
	 * method around after,composite with aroundBefore
	 * @param args
	 */
	void aroundAfter(Object obj);
}
