package cn.xdaoy.sample.request;

import java.io.IOException;

import cn.xdaoy.utils.http.HttpUtils;
import cn.xdaoy.utils.tcp.TcpUtils;

public class RequestTest {

	public static void testHttp() {
		try {
			String r = HttpUtils.get("http://localhost:8088/test");
			System.out.println(r);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void testTcp() {
		try {
			String r = TcpUtils.getString("127.0.0.1", 12345, "utf-8", "测试");
			System.out.println(r);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
