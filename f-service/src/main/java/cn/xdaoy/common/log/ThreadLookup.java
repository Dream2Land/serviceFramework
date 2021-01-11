package cn.xdaoy.common.log;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.lookup.StrLookup;

@Plugin(name = "thread", category = StrLookup.CATEGORY)
public class ThreadLookup implements StrLookup {

	@Override
	public String lookup(String key) {
		// combine http request thread,avoid multiple log file
		String name = Thread.currentThread().getName();
		if (name.startsWith("BATCH") || name.startsWith("ASYNC")) {
			return name;
		}
		return "Threads";
	}

	@Override
	public String lookup(LogEvent event, String key) {
		String name = event.getThreadName() == null ? Thread.currentThread().getName() : event.getThreadName();
		// combine http request thread,avoid multiple log file
		if (name.startsWith("BATCH") || name.startsWith("ASYNC")) {
			return name;
		}
		return "Threads";
	}

}
