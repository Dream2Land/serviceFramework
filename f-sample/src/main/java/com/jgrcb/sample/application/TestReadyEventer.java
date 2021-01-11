package com.jgrcb.sample.application;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.stereotype.Component;

import cn.xdaoy.common.application.ApplicationReadyEventListener.ReadyEventer;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TestReadyEventer implements ReadyEventer{

	@Override
	public void exec(ApplicationReadyEvent event) {
		log.info("\\n\\n\\n\\ntest application ready\\n\\n\\n\\n");
	}

}
