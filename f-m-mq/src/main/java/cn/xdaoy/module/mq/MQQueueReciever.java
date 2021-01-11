package cn.xdaoy.module.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;

import com.alibaba.fastjson.JSON;

/**
 * MQ queue message reciever
 * 
 * @author xdtand/matengfei
 *
 */
public abstract class MQQueueReciever {
	
	protected final transient Logger logger = LoggerFactory.getLogger(this.getClass());

	@JmsListener(destination = MQConst.QUEUE)
	public void recieveTag(Message message) {
		logger.info("==>mq recieve queue msg:"+JSON.toJSONString(message));
		if(tag().equals(message.getHeader().getTag())) {
			recieve(message.getBody());
		}
	}
	
	/**
	 * modify message type
	 * @return
	 */
	public abstract String tag();
	
	
	/**
	 * modify message usage
	 * @param obj
	 */
	public abstract void recieve(String obj);
}
