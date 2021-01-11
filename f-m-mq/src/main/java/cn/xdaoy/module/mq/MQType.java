package cn.xdaoy.module.mq;

public enum MQType {

	TOPIC(MQConst.TOPIC), QUEUE(MQConst.QUEUE);

	private String value = "";

	private MQType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
