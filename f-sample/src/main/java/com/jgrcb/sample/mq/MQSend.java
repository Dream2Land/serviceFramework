package com.jgrcb.sample.mq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.xdaoy.module.mq.MQSender;
import cn.xdaoy.module.mq.MQType;

@Component
public class MQSend {

	@Autowired
	MQSender mqSender;
	
	public void send() {
		mqSender.sendMessage(MQType.TOPIC, "test", "test");
	}
	
}
