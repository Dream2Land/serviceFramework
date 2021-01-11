package cn.xdaoy.utils.ftp;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.sshd.client.SshClient;
import org.apache.sshd.client.session.ClientSession;
import org.apache.sshd.client.subsystem.sftp.SftpClientFactory;
import org.apache.sshd.client.subsystem.sftp.fs.SftpFileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.xdaoy.utils.StringUtils;

/**
 * sftp implement by mina-sshd
 * 
 * @author xdtand
 *
 */
public class SftpOperator implements FtpInterface {

	private static final Logger logger = LoggerFactory.getLogger(SftpOperator.class);

	private SshClient client;

	private ClientSession session;
	
	private SftpFileSystem fs;

	private String host;

	private int port;

	private String username;

	private String password;

	public SftpOperator(String host, int port, String username, String password) {
		super();
		this.host = host;
		this.port = port;
		this.username = username;
		this.password = password;
	}

	@Override
	public FtpInterface login() throws Exception {
		logger.info("==>sftp login begin");
		client = SshClient.setUpDefaultClient();
		client.start();
		session = client.connect(username, host, port).verify().getSession();
		session.addPasswordIdentity(password);
		if (session.auth().verify(3000).isFailure()) {
			logger.warn("==>sftp password invalid");
			throw new Exception("sftp auth failure");
		}
		fs = SftpClientFactory.instance().createSftpFileSystem(session);
		logger.info("==>sftp login success");
		return this;
	}
	
	@Override
	public void logout() throws Exception {
		fs.close();
		session.close();
		client.stop();
	}

	@Override
	public void upload(String ftpdir, String ftpname, String filename) throws Exception {
		Path remoteRoot = fs.getDefaultDir().resolve(ftpdir);
		if (!Files.exists(remoteRoot))
			Files.createDirectories(remoteRoot);
		Path remoteFile = remoteRoot.resolve(ftpname);
		Files.deleteIfExists(remoteFile);
		Files.copy(Paths.get(filename), remoteFile);
	}

	@Override
	public void upload(String ftpdir, String ftpname, InputStream inputStream) throws Exception {
		Path remoteRoot = fs.getDefaultDir().resolve(ftpdir);
		if (!Files.exists(remoteRoot))
			Files.createDirectories(remoteRoot);
		Path remoteFile = remoteRoot.resolve(ftpname);
		Files.deleteIfExists(remoteFile);
		Files.copy(inputStream, remoteFile);
	}

	@Override
	public void download(String ftpdir, String ftpname, String filename) throws Exception {
		Files.deleteIfExists(Paths.get(filename));
		Path remoteFile = fs.getDefaultDir().resolve(ftpdir).resolve(ftpname);
		Files.copy(remoteFile, Paths.get(filename));
	}

	@Override
	public void download(String ftpdir, String ftpname, OutputStream outputStream) throws Exception {
		Path remoteFile = fs.getDefaultDir().resolve(ftpdir).resolve(ftpname);
		Files.copy(remoteFile, outputStream);
	}

	@Override
	public void delete(String ftpdir, String ftpname) throws Exception {
		Path remoteRoot = fs.getDefaultDir().resolve(ftpdir);
		if (StringUtils.isEmpty(ftpname)) {
			Files.deleteIfExists(remoteRoot);
			return;
		}
		Files.deleteIfExists(remoteRoot.resolve(ftpname));
	}

	@Override
	public void mkdir(String ftpdir) throws Exception {
		Path remoteRoot = fs.getDefaultDir().resolve(ftpdir);
		if (!Files.exists(remoteRoot))
			Files.createDirectories(remoteRoot);
	}

	public boolean exists(String path) throws Exception {
		return Files.exists(fs.getDefaultDir().resolve(path));
	}

	@Override
	public List<Path> list(String ftpdir) throws Exception {
		Path remoteFile = fs.getDefaultDir().resolve(ftpdir);
		return Files.list(remoteFile).collect(Collectors.toList());
	}

	public static void main(String[] args) {
		SftpOperator sftp = new SftpOperator("localhost", 22, "test", "123");
		try {
			sftp.login();
			sftp.mkdir("test/aaa/bbb");
			sftp.upload("test/aaa", "pom.xml.bak", "pom.xml");
			sftp.list("test/aaa").forEach(p -> System.out.println(p.toString()));
			sftp.download("test/aaa", "pom.xml.bak", "pom.xml.bak");
			sftp.delete("test/aaa", "pom.xml.bak");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				sftp.logout();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
}
