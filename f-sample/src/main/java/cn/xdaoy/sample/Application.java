package cn.xdaoy.sample;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

import cn.xdaoy.utils.PropertiesUtils;

@SpringBootApplication
@ComponentScan({"cn.xdaoy","com.ctm"})
@MapperScan({"cn.xdaoy","com.ctm"})
@EnableDiscoveryClient
public class Application {

	public static void main(String[] args) {
		setProperty(args);
		SpringApplication.run(Application.class, args);
	}
	private static void setProperty(String[] args) {
		System.setProperty("projectName", PropertiesUtils.getProperty("spring.application.name"));
	}
	
	@LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
