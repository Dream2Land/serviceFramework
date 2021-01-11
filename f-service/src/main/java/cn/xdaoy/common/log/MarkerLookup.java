package cn.xdaoy.common.log;

import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.lookup.StrLookup;

@Plugin(name = "marker", category = StrLookup.CATEGORY)
public class MarkerLookup implements StrLookup {

	@Override
	public String lookup(String key) {
		String name = Thread.currentThread().getName();
		if (name.startsWith("BATCH") || name.startsWith("ASYNC")) {
			return "TASK";
		}
		return null;
	}

	@Override
	public String lookup(LogEvent event, String key) {
		final Marker marker = event.getMarker();
		String name = marker == null ? null : marker.getName();
		String tname = event.getThreadName() == null ? Thread.currentThread().getName() : event.getThreadName();
		if (null == name && (tname.startsWith("BATCH") || tname.startsWith("ASYNC"))) {
			return "TASK";
		}
        return name;
	}

}
