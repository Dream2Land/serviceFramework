package cn.xdaoy.utils.http;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import okhttp3.FormBody;
import okhttp3.FormBody.Builder;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Component
public class HttpUtils {

	protected static OkHttpClient client;

	public static final MediaType JSON = MediaType.parse("application/json");
	public static final MediaType XML = MediaType.parse("application/xml");

	@Value("${http.timeout:10000}")
	protected Long timeout;

	protected HttpUtils() {

	}

	@PostConstruct
	void init() {
		initf();
	}

	protected void initf() {
		client = new OkHttpClient.Builder().connectTimeout(timeout, TimeUnit.MILLISECONDS)
				.writeTimeout(timeout, TimeUnit.MILLISECONDS).readTimeout(timeout, TimeUnit.MILLISECONDS).build();
	}

	/**
	 * get
	 * 
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public static byte[] request(final String url) throws Exception {
		Request request = new Request.Builder().url(url).build();
		Response response = client.newCall(request).execute();
		return response.body().bytes();
	}

	/**
	 * get
	 * 
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public static String get(final String url, final String charset) throws Exception {
		return new String(request(url), charset);
	}

	/**
	 * get
	 * 
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public static String get(final String url) throws Exception {
		return get(url, "utf-8");
	}

	/**
	 * get with params
	 * 
	 * @param url
	 * @param Map<String,String> params
	 * @return
	 * @throws IOException
	 */
	public static String get(final String url, final Map<String, String> params) throws Exception {
		return get(formatUrl(url, params));
	}

	/**
	 * get with params
	 * 
	 * @param String             url
	 * @param Map<String,String> params
	 * @param String             params
	 * @return
	 * @throws IOException
	 */
	public static String get(final String url, final Map<String, String> params, final String charset)
			throws Exception {
		return get(formatUrl(url, params), charset);
	}

	/**
	 * format url with params
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static String formatUrl(final String url, final Map<String, String> params) throws Exception {
		String nurl = url;
		Set<String> pSet = new HashSet<>();
		for (Entry<String, String> entry : params.entrySet()) {
			pSet.add(entry.getKey() + "=" + URLEncoder.encode(entry.getValue(), "UTF-8"));
		}
		String paramStr = String.join("&", pSet);
		if (url.indexOf("?") > 0) {
			nurl = url + "&" + paramStr;
		} else {
			nurl = url + "?" + paramStr;
		}
		return nurl;
	}

	/**
	 * post
	 * 
	 * @param url
	 * @param body
	 * @param mediaType
	 * @return
	 * @throws IOException
	 */
	public static byte[] post(final String url, final byte[] bytes, final MediaType mediaType) throws IOException {
		RequestBody body = RequestBody.create(bytes, mediaType);
		Request request = new Request.Builder().url(url).post(body).build();
		Response response = client.newCall(request).execute();
		return response.body().bytes();
	}

	/**
	 * post with form
	 * 
	 * @param url
	 * @param json
	 * @return
	 * @throws IOException
	 */
	public static String postForm(final String url, final Map<String, String> params) throws IOException {
		return postForm(url,params,"utf-8");
	}
	
	/**
	 * post with form
	 * 
	 * @param url
	 * @param json
	 * @param charset
	 * @return
	 * @throws IOException
	 */
	public static String postForm(final String url, final Map<String, String> params,final String charset) throws IOException {
		Builder builder = new FormBody.Builder();
		for (Entry<String, String> entry : params.entrySet()) {
			builder.add(entry.getKey(), entry.getValue());
		}
		FormBody formBody = builder.build();
		Request request = new Request.Builder().url(url).post(formBody).build();
		Response response = client.newCall(request).execute();
		return new String(response.body().bytes(),charset);
	}

	/**
	 * post with json
	 * 
	 * @param url
	 * @param json
	 * @return
	 * @throws IOException
	 */
	public static String postJson(final String url, final String json) throws IOException {
		return new String(post(url, json.getBytes("utf-8"), JSON), "utf-8");
	}

	/**
	 * post with json
	 * 
	 * @param url
	 * @param json
	 * @param charset
	 * @return
	 * @throws IOException
	 */
	public static String postJson(final String url, final String json, final String charset) throws IOException {
		return new String(post(url, json.getBytes(charset), JSON), charset);
	}

	/**
	 * post with xml
	 * 
	 * @param url
	 * @param xml
	 * @return
	 * @throws IOException
	 */
	public static String postXml(final String url, final String xml) throws IOException {
		return new String(post(url, xml.getBytes("utf-8"), XML), "utf-8");
	}

	/**
	 * post with xml
	 * 
	 * @param url
	 * @param xml
	 * @param charset
	 * @return
	 * @throws IOException
	 */
	public static String postXml(final String url, final String xml, final String charset) throws IOException {
		return new String(post(url, xml.getBytes(charset), XML), charset);
	}
}
