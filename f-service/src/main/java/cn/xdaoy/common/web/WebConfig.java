package cn.xdaoy.common.web;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		//converters.clear();
		// -------------------1 json --------------------
		/*
		 * // 1.message converter FastJsonHttpMessageConverter
		 * fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter(); //
		 * 2.fastjson config; FastJsonConfig fastJsonConfig = new FastJsonConfig();
		 * fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
		 * fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
		 * fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig); // json
		 * format List<MediaType> fastMediaTypes = new ArrayList<>();
		 * fastMediaTypes.add(MediaType.APPLICATION_JSON);
		 * fastJsonHttpMessageConverter.setSupportedMediaTypes(fastMediaTypes);
		 * fastJsonHttpMessageConverter.setDefaultCharset(Charset.forName("UTF-8"));
		 * converters.add(fastJsonHttpMessageConverter);
		 */		
		Gson gson = new GsonBuilder()
		        .setDateFormat("yyyy-MM-dd HH:mm:ss")
		        .registerTypeAdapter(String.class, STRING)
		        .excludeFieldsWithModifiers(Modifier.STATIC, Modifier.TRANSIENT, Modifier.VOLATILE)
		        .create();
		GsonHttpMessageConverter gsonHttpMessageConverter = new GsonHttpMessageConverter(gson);
		gsonHttpMessageConverter.setDefaultCharset(StandardCharsets.UTF_8);
		converters.add(gsonHttpMessageConverter);

		// ------------------2 string----------------
		converters.add(responseBodyConverter());
	}

	public HttpMessageConverter<String> responseBodyConverter() {
		StringHttpMessageConverter converter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
		return converter;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// registry.addInterceptor(new TokenAuthInterceptor());
	}
	
	private static final TypeAdapter<String> STRING = new TypeAdapter<String>()
	{
		public String read(JsonReader reader) throws IOException
		{
			if (reader.peek() == JsonToken.NULL)
			{
				reader.nextNull();
				return "";
			}
			return reader.nextString();
		}
		
		public void write(JsonWriter writer, String value) throws IOException
		{
			if (value == null)
			{
				writer.value("");
				return;
			}
			writer.value(value);
		}

	};

}
