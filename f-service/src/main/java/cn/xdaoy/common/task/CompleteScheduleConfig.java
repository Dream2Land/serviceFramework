package cn.xdaoy.common.task;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import com.google.gson.Gson;

import cn.xdaoy.utils.ClassUtils;
import cn.xdaoy.utils.StringUtils;

@Configuration
@EnableScheduling
public class CompleteScheduleConfig implements SchedulingConfigurer {
	
	private final transient Logger logger = LoggerFactory.getLogger(CompleteScheduleConfig.class);

	@Value("${cron.task.enable:false}")
	String enable;
	
	@Value("${cron.task.appid}")
	String appid;
	
	@Autowired
	CronMapper cronMapper;
	
	/**
	 * config task
	 */
	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		taskRegistrar.setScheduler(Executors.newScheduledThreadPool(10, new ThreadFactory() {
			@Override
			public Thread newThread(Runnable r) {
				return new Thread(r,"Schedule");
			}
		}));
		
		//taskRegistrar.getCronTaskList().clear();
		
		if("false".equals(enable)) {
			return;
		}
		
		List<TaskEntity> cronList = cronMapper.getCron(appid);
		logger.info("==>cron task size：{}",cronList.size());
		logger.info("==>cron task list:{}", new Gson().toJson(cronList));
		for (TaskEntity cron : cronList) {
			if (StringUtils.isEmpty(cron.getCron())
					|| StringUtils.isEmpty(cron.getClassName())) {
				continue;
			}
			try {
				Runnable task = null;
				try {
					task = (Runnable) ClassUtils.getClass(cron.getClassName()).newInstance();
				}catch (Exception e) {
					logger.error("==>load task error:",e);
				}
				taskRegistrar.addCronTask(task, cron.getCron());
			} catch (Exception e) {
				logger.error("==>load task error:",e);
			}
		}
		logger.info("==>load task finish");
	}

}
