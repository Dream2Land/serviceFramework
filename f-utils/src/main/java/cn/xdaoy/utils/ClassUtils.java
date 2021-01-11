package cn.xdaoy.utils;

import java.net.URL;
import java.net.URLClassLoader;

import org.springframework.core.io.ClassPathResource;

public class ClassUtils {

	private static ClassLoader classLoader;
	
	private static URLClassLoader urlClassLoader;
	
	static {
		classLoader = org.springframework.util.ClassUtils.getDefaultClassLoader();
		try {
			URL path = new ClassPathResource("").getURL();
			URL[] url = new URL[] {path};
			urlClassLoader = URLClassLoader.newInstance(url,classLoader);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param className :full package class name
	 * @throws ClassNotFoundException 
	 */
	public static Class<?> getClass(String className) throws ClassNotFoundException {
		//默认从lib加载
		Class<?> cls = null;
		try {
			cls = Class.forName(className,false,classLoader);
		}catch (Exception e) {
			e.printStackTrace();
		}
		if(null == cls) {
			cls = org.springframework.util.ClassUtils.forName(className,urlClassLoader);
		}
		return cls;
	}
}
