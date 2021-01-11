package cn.xdaoy.common.corp;

public enum FtpEnum {

	FTP("FTP"),SFTP("SFTP");
	
	private String name;
	
	private FtpEnum(String name) {
		this.name=name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
