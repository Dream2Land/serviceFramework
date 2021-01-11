package cn.xdaoy.module.mq;

import java.io.Serializable;

public class Message implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Header header;
	
	private String body;
	
	public class Header implements Serializable{
		private static final long serialVersionUID = 1L;
		private String topic;
		private String tag;
		public Header(String topic, String tag) {
			super();
			this.topic = topic;
			this.tag = tag;
		}
		public String getTopic() {
			return topic;
		}
		public String getTag() {
			return tag;
		}
	}
	
	public Message buildHeader(String topic, String tag) {
		this.header = new Header(topic,tag);
		return this;
	}
	
	public Message buildBody(String body) {
		this.body = body;
		return this;
	}

	public Header getHeader() {
		return header;
	}

	public String getBody() {
		return body;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
