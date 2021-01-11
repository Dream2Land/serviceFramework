package cn.xdaoy.utils;

import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtils {

	private static Properties props;

	static {
		loadProps();
	}

	private static void loadProps() {
		props = new Properties();
		try (InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream("application.properties")) {
			props.load(in);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getProperty(String key) {
		if (null == props) {
			loadProps();
		}
		return props.getProperty(key);
	}

	public static String getProperty(String key, String defaultValue) {
		if (null == props) {
			loadProps();
		}
		return props.getProperty(key, defaultValue);
	}

}
