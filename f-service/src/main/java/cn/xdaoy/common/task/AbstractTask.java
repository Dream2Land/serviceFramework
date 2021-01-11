package cn.xdaoy.common.task;

import org.slf4j.Marker;
import org.slf4j.MarkerFactory;


public abstract class AbstractTask implements Runnable{

	protected static final Marker TASK_MARKER = MarkerFactory.getMarker("TASK");
	
	protected final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());
	
	protected void debug(String msg) {
		log.debug(TASK_MARKER, msg);
	}
	
	protected void debug(String format,Object...arguments) {
		log.debug(TASK_MARKER, format, arguments);	
	}
	
	protected void info(String msg) {
		log.info(TASK_MARKER, msg);
	}
	
	protected void info(String format,Object...arguments) {
		log.info(TASK_MARKER, format, arguments);	
	}
	
	protected void warn(String msg) {
		log.warn(TASK_MARKER, msg);
	}
	
	protected void warn(String format,Object...arguments) {
		log.warn(TASK_MARKER, format, arguments);	
	}
	
	protected void error(String msg) {
		log.error(TASK_MARKER, msg);
	}
	
	protected void error(String format,Object...arguments) {
		log.error(TASK_MARKER, format, arguments);	
	}
	
	protected void error(String msg,Throwable e) {
		log.error(TASK_MARKER, msg,e);
	}
}
