package com.jgrcb.sample.controller;

import java.util.Arrays;
import java.util.Enumeration;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.jgrcb.sample.aop.AopTarget;
import com.jgrcb.sample.corp.CorpRequestService;
import com.jgrcb.sample.mapper.SampleMapper;
import com.jgrcb.sample.mq.MQSend;
import com.jgrcb.sample.redis.RedisService;
import com.jgrcb.sample.request.RequestTest;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;

@RestController
@RefreshScope
@Slf4j
public class SampleController {
	
	@Resource
	SampleMapper sampleMapper;
	
	@Resource
	AopTarget aopTarget;
	
	@Resource
	RedisService redisService;
	
	@Resource
	MQSend mqSend;
	
	@Resource
	CorpRequestService corpRequestService;
	
	private final RestTemplate restTemplate;

    @Autowired
    public SampleController(RestTemplate restTemplate) {this.restTemplate = restTemplate;}

	@RequestMapping("test")
	public String test(HttpServletRequest request) {
		log.info("req header:");
		Enumeration<String> headerNames = request.getHeaderNames();
		while(headerNames.hasMoreElements()) {
			String h = headerNames.nextElement();
			log.info("{}:{}",h,request.getHeader(h));
		}
		return "hello"+sampleMapper.count();
	}
	
	@RequestMapping("test/aop")
	public String testAop() {
		return aopTarget.test();
	}
	
	@RequestMapping("test/redis")
	public String testRedis() {
		return redisService.test();
	}
	
	@RequestMapping("test/tcp")
	public String testTcp() { 
		RequestTest.testTcp();
		return "tcp send";
	}
	
	@RequestMapping("test/http")
	public String testHttp() { 
		RequestTest.testHttp();
		return "http send";
	}
	
	@RequestMapping("test/service/find")
	public String testServiceFind() { 
		return restTemplate.getForObject("http://sample/test/", String.class);
	}
	
	@Value("${test:111}")
	String test;
	
	@RequestMapping("test/config")
	public String testConfig() { 
		return test;
	}
	
	@RequestMapping("test/mq")
	public String testmq() { 
		mqSend.send();
		return "sended";
	}
	
	@RequestMapping("test/corp")
	public String testcorp() { 
		corpRequestService.testReqest();
		return "sended";
	}
	
	@Autowired
	private WxMpService mpService;

	@GetMapping({ "test/mp/msg" })
	public String test(HttpServletRequest request, Model model) {
		WxMpTemplateData data = new WxMpTemplateData();
		data.setName("keyword1");
		data.setValue("test");
		WxMpTemplateMessage msg = new WxMpTemplateMessage();
		msg.setTemplateId("VuUGmmN844zoQcUyYfv7a7DUmIiLfNHgsZB40VRlav0");
		msg.setToUser("o4DKG6PTKnKt5G8B9hh5UL6pTV2U");
		msg.setData(Arrays.asList(data));
		try {
			return mpService.getTemplateMsgService().sendTemplateMsg(msg);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
}
