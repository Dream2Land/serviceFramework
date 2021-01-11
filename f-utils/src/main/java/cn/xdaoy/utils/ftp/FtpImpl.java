package cn.xdaoy.utils.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.xdaoy.utils.StringUtils;
import cn.xdaoy.utils.file.FileUtils;

public class FtpImpl implements FtpInterface {

	private static final transient Logger logger = LoggerFactory.getLogger(FtpImpl.class);

	private String hostname;
	private Integer port = 21;
	private String username;
	private String password;

	private boolean isUTF8 = false;

	private FTPClient ftpClient = null;

	public FtpImpl(String hostname, String username, String password) {
		this.hostname = hostname;
		this.username = username;
		this.password = password;
	}

	public FtpImpl(String hostname, Integer port, String username, String password) {
		this.hostname = hostname;
		this.port = port;
		this.username = username;
		this.password = password;
	}

	@Override
	public FtpImpl login() throws Exception {
		ftpClient = new FTPClient();
		ftpClient.setControlEncoding("utf-8");
		logger.info("connecting...ftp:" + this.hostname + ":" + this.port);
		ftpClient.connect(hostname, port);
		if (!ftpClient.login(username, password)) {
			logger.error("connect failed...ftp:" + this.hostname + ":" + this.port);
			return this;
		}
		if (FTPReply.isPositiveCompletion(ftpClient.sendCommand("OPTS UTF8", "ON"))) {
			isUTF8 = true;
			logger.debug("ftp utf-8 support");
		}
		logger.info("connect success...ftp:" + this.hostname + ":" + this.port);
		return this;
	}

	@Override
	public void logout() throws Exception {
		ftpClient.logout();
		if (ftpClient.isConnected()) {
			ftpClient.disconnect();
		}
	}

	@Override
	public void upload(String ftpdir, String ftpname, String filename) throws Exception {
		InputStream inputStream = null;
		try {
			logger.info("==>upload begin,ftp dir:{},ftp filename:{},filename:{}", ftpdir, ftpname, filename);
			inputStream = new FileInputStream(new File(filename));
			upload(ftpdir, ftpname, inputStream);
		} finally {
			if (null != inputStream) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		logger.info("==>upload finished");
	}

	@Override
	public void upload(String ftpdir, String ftpname, InputStream inputStream) throws Exception {
		try {
			if (!isUTF8) {
				ftpdir = new String(ftpdir.getBytes("UTF-8"), "iso-8859-1");
				ftpname = new String(ftpname.getBytes("UTF-8"), "iso-8859-1");
			}
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			if (!StringUtils.isEmpty(ftpdir)) {
				mkdir(ftpdir);
				ftpClient.makeDirectory(ftpdir);
				ftpClient.changeWorkingDirectory(ftpdir);
			}
			ftpClient.storeFile(ftpname, inputStream);
		} finally {
			if (null != inputStream) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void mkdir(String remote) throws IOException {

		if (StringUtils.isEmpty(remote)) {
			return;
		}
		if (remote.contains("\\")) {
			logger.warn("==>path seperator invalid");
			return;
		}
		String directory = remote + "/";
		if (!isUTF8) {
			directory = new String(directory.getBytes("UTF-8"), "iso-8859-1");
		}
		if (directory.equalsIgnoreCase("/") || ftpClient.changeWorkingDirectory(directory)) {
			return;
		}

		String[] paths = directory.split("\\/");
		String cpath = "";
		for (String path : paths) {
			cpath += "/" + path;
			if (!ftpClient.changeWorkingDirectory(cpath)) {
				if (ftpClient.makeDirectory(cpath)) {
					ftpClient.changeWorkingDirectory(cpath);
				} else {
					logger.error("create ftp dir failure");
				}
			}
		}
	}

	@Override
	public void download(String ftpdir, String ftpname, String filename) throws Exception {
		OutputStream os = null;
		try {
			FileUtils.createFile(filename, true);
			os = new FileOutputStream(filename);
			download(ftpdir, ftpname, os);
			logger.info("download success");
		} finally {
			if (null != os) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void download(String ftpdir, String ftpname, OutputStream outputStream) throws Exception {
		if (!isUTF8) {
			ftpdir = new String(ftpdir.getBytes("UTF-8"), "iso-8859-1");
			ftpname = new String(ftpname.getBytes("UTF-8"), "iso-8859-1");
		}
		ftpClient.changeWorkingDirectory(ftpdir);

		boolean exists = false;
		FTPFile[] ftpFiles = ftpClient.listFiles();
		for (FTPFile file : ftpFiles) {
			if (ftpname.equalsIgnoreCase(file.getName())) {
				exists = true;
				ftpClient.retrieveFile(file.getName(), outputStream);
			}
		}
		if (!exists) {
			throw new FileNotFoundException("ftp file not exists，file:" + ftpname);
		}
	}

	@Override
	public void delete(String ftpdir, String filename) throws Exception {
		if (!isUTF8) {
			ftpdir = new String(ftpdir.getBytes("UTF-8"), "iso-8859-1");
			filename = new String(filename.getBytes("UTF-8"), "iso-8859-1");
		}
		if (ftpClient.changeWorkingDirectory(ftpdir)) {
			ftpClient.dele(filename);
		} else {
			logger.info("ftp del failure！");
		}
		logger.info("ftp del success");
	}

	@Override
	public List<?> list(String ftpdir) throws Exception {
		if (!isUTF8) {
			ftpdir = new String(ftpdir.getBytes("UTF-8"), "iso-8859-1");
		}
		ftpClient.changeWorkingDirectory(ftpdir);
		return Arrays.asList(ftpClient.listFiles());
	}

}
