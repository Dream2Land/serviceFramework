package cn.xdaoy.test;

import java.util.HashMap;

import cn.xdaoy.utils.PropertiesUtils;
import cn.xdaoy.utils.http.HttpUtils;

public class MyTest{
	
	public static void main(String[] args) throws Exception {
//		HttpUtils.get("https://www.baidu.com");
//		HttpUtils.get("https://www.baidu.com","GBK");
//		HttpUtils.get("https://www.baidu.com",new HashMap<>());
//		HttpUtils.postForm("https://www.baidu.com",new HashMap<>());
//		HttpUtils.postJson("https://www.baidu.com","123");
//		HttpUtils.postJson("https://www.baidu.com","123","GBK");
//		HttpUtils.postXml("https://www.baidu.com","123");
//		HttpUtils.postJson("https://www.baidu.com","123","GBK");
		System.out.println(PropertiesUtils.getProperty("http.timeout"));
	}
}
