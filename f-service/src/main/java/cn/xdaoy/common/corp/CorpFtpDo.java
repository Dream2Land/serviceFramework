package cn.xdaoy.common.corp;

import cn.xdaoy.common.bean.BaseBean;

public class CorpFtpDo extends BaseBean{

	private static final long serialVersionUID = 1L;

	private String corpid;
	
	private String host;
	
	private int port;
	
	private String username;
	
	private String password;
	
	private String remotepath;
	
	private String localpath;
	
	private String type;
	
	private String proc;

	public String getCorpid() {
		return corpid;
	}

	public void setCorpid(String corpid) {
		this.corpid = corpid;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRemotepath() {
		return remotepath;
	}

	public void setRemotepath(String remotepath) {
		this.remotepath = remotepath;
	}

	public String getLocalpath() {
		return localpath;
	}

	public void setLocalpath(String localpath) {
		this.localpath = localpath;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getProc() {
		return proc;
	}

	public void setProc(String proc) {
		this.proc = proc;
	}
	
}
