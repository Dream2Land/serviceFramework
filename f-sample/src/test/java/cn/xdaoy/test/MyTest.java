package cn.xdaoy.test;

import java.io.IOException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.assertj.core.util.Arrays;

import com.alibaba.fastjson.JSONObject;

import cn.xdaoy.common.task.TaskFactory;
import cn.xdaoy.utils.AESUtils;
import cn.xdaoy.utils.http.HttpUtils;
import cn.xdaoy.utils.http.TrustAllFactory;
import cn.xdaoy.utils.http.TrustAllFactory.TrustAllCerts;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MyTest extends HttpUtils{
	
	public static void main(String[] args) throws Exception {
		System.setProperty("projectName", "sample");
		test4();
	}
	
	private static void test() throws IOException {
		client = new OkHttpClient.Builder()
		        .sslSocketFactory(TrustAllFactory.createSSLSocketFactory(), new TrustAllCerts())
		        .connectTimeout(10000, TimeUnit.MILLISECONDS)
	            .writeTimeout(10000,TimeUnit.MILLISECONDS)
	            .readTimeout(10000, TimeUnit.MILLISECONDS)
	            .build();
		JSONObject jsono = new JSONObject();
		jsono.put("openid", "ofC5P5S4H7HMkRiTp6YmfuMdB5RQ");
		jsono.put("mobile", "15754803758");
		JSONObject jsono2 = new JSONObject();
		jsono2.put("data", Base64.encodeBase64String(jsono.toJSONString().getBytes()));
		RequestBody body = RequestBody.create(jsono2.toJSONString(),JSON);
		Request request = new Request.Builder().addHeader("source","0001")
				.addHeader("target", "iboc-info")
				.addHeader("sign", "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIwMDAxIiwic3ViIjoie1widmlwX2x2bFwiOlwiU1ZJUDJcIixcInVzZXJsZXZlbFwiOlwiMDJcIixcIm9wZW5JZFwiOlwib2ZDNVA1UzRIN0hNa1JpVHA2WW1mdU1kQjVSUVwiLFwib3BlcmF0ZXJJZFwiOlwiMDM5OTMyODU5YzEwNDYxODgzOWZiNDBmNzVjNmJmOTNcIixcInVzZXJpZFwiOlwiMDM5OTMyODU5YzEwNDYxODgzOWZiNDBmNzVjNmJmOTNcIixcInRhZ3NcIjpbXCIyXCIsXCIzXCJdfSIsImlzcyI6ImdhdGV3YXkiLCJpYXQiOjE1OTQ3Nzk2NTQsImV4cCI6MTU5NDc4MzI1NH0.M0CyvUH5e1VDJyRk-HTr1tGxmXWrdZ2TqBdVubO41WU")
				.url("https://ibobt.jgrcb.com/gateway/v/api/iboc/info/sms/send")
				.post(body).build();
		Response response = client.newCall(request).execute();
		System.out.println(Base64.decodeBase64(response.body().string().getBytes()));
	}
	
	private static void test2() {
		//String s = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIwMDAxIiwic3ViIjoie1widmlwX2x2bFwiOlwiU1ZJUDJcIixcInVzZXJsZXZlbFwiOlwiMDJcIixcIm9wZW5JZFwiOlwib2ZDNVA1UzRIN0hNa1JpVHA2WW1mdU1kQjVSUVwiLFwib3BlcmF0ZXJJZFwiOlwiMDM5OTMyODU5YzEwNDYxODgzOWZiNDBmNzVjNmJmOTNcIixcInVzZXJpZFwiOlwiMDM5OTMyODU5YzEwNDYxODgzOWZiNDBmNzVjNmJmOTNcIixcInRhZ3NcIjpbXCIyXCIsXCIzXCJdfSIsImlzcyI6ImdhdGV3YXkiLCJpYXQiOjE1OTQ3Nzk2NTQsImV4cCI6MTU5NDc4MzI1NH0.M0CyvUH5e1VDJyRk-HTr1tGxmXWrdZ2TqBdVubO41WU";
		String s = "aoGSgHQDHLw8QBRPuJQJVo7DYqNlZjGvEY_swyZOuhA";
		System.out.println(Arrays.asList(Base64.decodeBase64(s)));
	}
	public static char[] getChars(byte[] bytes) {
        Charset cs = Charset.forName("UTF-8");
        ByteBuffer bb = ByteBuffer.allocate(bytes.length);
        bb.put(bytes);
        bb.flip();
        CharBuffer cb = cs.decode(bb);
        return cb.array();
    }
	
	public static void test3() {
		CloseableHttpClient client = HttpClientBuilder.create().build();
		for(int i=0;i<15;i++) {
		TaskFactory.addAsyncTask(new Runnable() {
			@Override
			public void run() {
				try {
					HttpGet get = new HttpGet();
					get.setURI(new URI("https://www.xdaoy.cn"));
					CloseableHttpResponse resp = client.execute(get);
					System.out.println(resp.getStatusLine().getStatusCode());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}});
		}
	}
	
	private static void test4() {
		try {
			//Arrays.asList(SecureRandom.getInstance("SHA1PRNG").generateSeed(16)).forEach(System.out::println);
			//String e = AESUtils.encrypt("123456", "ghj2jjo673K32bs");
			//System.out.println(e);
			String e = "ZUIQ9RBzzdPTE8R2PlF1kg==";;
			System.out.println(AESUtils.decrypt(e, "ghj2jjo673K32bs"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
