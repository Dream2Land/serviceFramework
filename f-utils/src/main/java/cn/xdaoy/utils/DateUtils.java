package cn.xdaoy.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

	public static final DateTimeFormatter FT_DAY = DateTimeFormatter.ofPattern("yyyyMMdd");
	
	public static final DateTimeFormatter FT_TIME = DateTimeFormatter.ofPattern("HHmmss");

	public static String formatNow(DateTimeFormatter ft) {
		return ft.format(LocalDateTime.now());
	}
	
	public static String formatYesterday(DateTimeFormatter ft) {
		return ft.format(yesterday());
	}
	
	public static String formatTomorrow(DateTimeFormatter ft) {
		return ft.format(tomorrow());
	}
	
	public static LocalDateTime now() {
		return LocalDateTime.now();
	}
	
	public static LocalDateTime yesterday() {
		return LocalDateTime.now().minusDays(1);
	}
	
	public static LocalDateTime tomorrow() {
		return LocalDateTime.now().plusDays(1);
	}
	
	public static String formatDate(LocalDateTime date,DateTimeFormatter ft) {
		return ft.format(date);
	}
	
	public static String formatDate(LocalDate date,DateTimeFormatter ft) {
		return ft.format(date);
	}

	public static LocalDateTime parse(String date,DateTimeFormatter ft) {
		return LocalDateTime.parse(date, ft);
	}
	
	public static LocalDate parseDate(String date,DateTimeFormatter ft) {
		return LocalDate.parse(date, ft);
	}
	
	public static String getDayBefore(String specifiedDay, long day, DateTimeFormatter ft) {
		LocalDate sd = DateUtils.parseDate(specifiedDay, ft);
		sd = sd.minusDays(day);
		return DateUtils.formatDate(sd,ft);
	}
	
	public static String getDayAfter(String specifiedDay, long day,DateTimeFormatter ft) {
		LocalDate sd = DateUtils.parseDate(specifiedDay, ft);
		sd = sd.plusDays(day);
		return DateUtils.formatDate(sd,ft);
	}

}
