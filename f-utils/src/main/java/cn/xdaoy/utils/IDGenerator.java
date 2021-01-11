package cn.xdaoy.utils;

import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;


public class IDGenerator {

	private static final AtomicInteger SEQ = new AtomicInteger();
	
	private static final Random random = new Random();

	/**
	 * uuid
	 * 
	 * @return
	 */
	public static String getUUID() {
		String s = UUID.randomUUID().toString();
		return s.replace("-", "");
	}

	public static String getDatePatternID() {
		return DateUtils.formatNow(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
	}

	/**
	 * order
	 * rule：TimeMillis + concurrentSeq + random(10)
	 * @return
	 */
	public static String getOrderNO() {
		String seq = String.valueOf(getSeqnum());
		seq = StringUtils.leftPad(seq, 6, '0');
		return String.valueOf(System.currentTimeMillis()) + seq + random.nextInt(10);
	}

	public static int getSeqnum() {
		if (SEQ.get()==0 || SEQ.get() > 999999) {
			SEQ.set(1);
		}
		return SEQ.getAndIncrement();
	}
}
