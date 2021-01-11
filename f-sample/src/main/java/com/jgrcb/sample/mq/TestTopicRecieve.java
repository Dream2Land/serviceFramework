package com.jgrcb.sample.mq;

import org.springframework.stereotype.Component;

import cn.xdaoy.module.mq.MQTopicReciever;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TestTopicRecieve extends MQTopicReciever{

	@Override
	public String tag() {
		return "test";
	}

	@Override
	public void recieve(String obj) {
		log.info("topic recieve:{}",obj);
	}

}
