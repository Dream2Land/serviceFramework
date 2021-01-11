package cn.xdaoy.utils.ftp;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

import cn.xdaoy.utils.file.FileUtils;

public class SftpImpl implements FtpInterface {

	private transient Logger logger = LoggerFactory.getLogger(SftpImpl.class);

	private ChannelSftp sftp;

	private Session session;

	private String username;

	private String password;

	private String privateKey;

	private String host;

	private int port;

	public SftpImpl(String host, int port, String username, String password) {
		this.username = username;
		this.password = password;
		this.host = host;
		this.port = port;
	}

	public SftpImpl(String username, String host, int port) {
		this.username = username;
		this.host = host;
		this.port = port;
	}

	public SftpImpl() {
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	@Override
	public SftpImpl login() {
		try {
			JSch jsch = new JSch();
			if (privateKey != null) {
				jsch.addIdentity(privateKey);
			}

			session = jsch.getSession(username, host, port);

			if (password != null) {
				session.setPassword(password);
			}
			Properties config = new Properties();
			config.put("StrictHostKeyChecking", "no");

			session.setConfig(config);
			session.connect();

			Channel channel = session.openChannel("sftp");
			channel.connect();
			logger.info("==>sft login success，host:{},port:{},user:{}", host,port,username);
			sftp = (ChannelSftp) channel;
		} catch (JSchException e) {
			logger.error("==>sft login failure，host:{},port:{},user:{}", host,port,username);
		}
		return this;
	}

	@Override
	public void logout() {
		if (sftp != null) {
			if (sftp.isConnected()) {
				sftp.disconnect();
			}
		}
		if (session != null) {
			if (session.isConnected()) {
				session.disconnect();
			}
		}
	}

	@Override
	public void upload(String ftpdir, String ftpname, String filename) throws Exception {
		logger.info("==>sft upload begin，ftpdir:{},ftpname:{},filename:{}", ftpdir,ftpname,filename);
		sftp.cd(ftpdir);
		sftp.put(ftpname, filename);
		logger.info("==>sft upload success，ftpdir:{},ftpname:{},filename:{}", ftpdir,ftpname,filename);
	}

	@Override
	public void upload(String ftpdir, String ftpname, InputStream inputStream) throws Exception {
		logger.info("==>sft upload begin，ftpdir:{},ftpname:{}", ftpdir,ftpname);
		sftp.cd(ftpdir);
		sftp.put(inputStream, ftpname);
		logger.info("==>sft upload success，ftpdir:{},ftpname:{}", ftpdir,ftpname);
	}

	@Override
	public void download(String ftpdir, String ftpname, String filename) throws Exception {
		logger.info("==>sft download begin，ftpdir:{},ftpname:{},filename:{}", ftpdir,ftpname,filename);
		if (ftpdir != null && !"".equals(ftpdir)) {
			sftp.cd(ftpdir);
		}
		FileUtils.createFile(filename, true);
		sftp.get(ftpname, new FileOutputStream(filename));
		logger.info("==>sftp download success，ftpdir：{},ftpname：{},filename:{}", ftpdir, ftpname,filename);
	}
	
	@Override
	public void download(String ftpdir, String ftpname, OutputStream outputStream) throws Exception {
		logger.info("==>sft download begin，ftpdir:{},ftpname:{}", ftpdir,ftpname);
		if (ftpdir != null && !"".equals(ftpdir)) {
			sftp.cd(ftpdir);
		}
		sftp.get(ftpname, outputStream);
		logger.info("==>sftp download success，ftpdir：{},ftpname：{}", ftpdir, ftpname);
	}

	@Override
	public void delete(String ftpdir, String ftpname) throws SftpException {
		sftp.cd(ftpdir);
		sftp.rm(ftpname);
		logger.info("==>sftp rm success，ftpdir：" + ftpdir + ",ftpname：" + ftpname);
	}

	@Override
	public Vector<?> list(String ftpdir) throws SftpException {
		return sftp.ls(ftpdir);
	}

	@Override
	public void mkdir(String ftpdir) throws Exception {
		String[] dirs = ftpdir.split("/");
		String tempPath = "";
		for (String dir : dirs) {
			if (null == dir || "".equals(dir))
				continue;
			tempPath += "/" + dir;
			try {
				sftp.cd(tempPath);
			} catch (SftpException ex) {
				sftp.mkdir(tempPath);
				sftp.cd(tempPath);
			}
		}

	}

}
