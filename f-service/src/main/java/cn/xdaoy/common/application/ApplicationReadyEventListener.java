package cn.xdaoy.common.application;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ApplicationReadyEventListener implements  ApplicationListener<ApplicationReadyEvent> {
	
	private static final List<ReadyEventer> list = new ArrayList<>();

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		log.info("==>application started, preparing ready function");
		
		for(ReadyEventer e :list) {
			e.exec(event);
		}
		
    }
	
	static void register( ReadyEventer e){
		list.add(e);
	}
	
	public interface ReadyEventer{ 
		@PostConstruct
		default void init() {
			register(this);
		};
		void exec(final ApplicationReadyEvent event);
	}
	
}
