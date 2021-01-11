package cn.xdaoy.utils.http;

import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import cn.xdaoy.utils.http.TrustAllFactory.TrustAllCerts;
import okhttp3.OkHttpClient;

@Component
public class HttpsUtils extends HttpUtils{
	
	@Override
	protected void initf() {
		client = new OkHttpClient.Builder()
		        .sslSocketFactory(TrustAllFactory.createSSLSocketFactory(), new TrustAllCerts())
		        .connectTimeout(timeout, TimeUnit.MILLISECONDS)
	            .writeTimeout(timeout,TimeUnit.MILLISECONDS)
	            .readTimeout(timeout, TimeUnit.MILLISECONDS)
	            .build();
	}

}
