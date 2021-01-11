package cn.xdaoy.module.mq;

import javax.jms.Destination;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;


@Component
public class MQSender {
	
	private final transient Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private JmsTemplate jmsTemplate;
	
	/**
	 * send 
	 * @param tag message tag
	 * @param obj message object
	 */
	public void sendMessage(final MQType type,final String tag,final String msg) {
		//send json str,avoid class file not found in other project
		Message message = new Message().buildHeader(type.getValue(), tag).buildBody(msg);
		logger.info("==>mq send message:"+JSON.toJSONString(message));
		String flag = "success";
		try {
			jmsTemplate.convertAndSend(getDestination(type), message);
		}catch (Exception e) {
			logger.error("==>send mq message error",e);
			flag="fail";
		}
		logger.info("==>mq send message {}",flag);
		
	}
	
	private Destination getDestination(final MQType destination) {
		if(MQType.QUEUE == destination) {
			return new ActiveMQQueue(destination.getValue());
		}
		return new ActiveMQTopic(destination.getValue());
	}
}
