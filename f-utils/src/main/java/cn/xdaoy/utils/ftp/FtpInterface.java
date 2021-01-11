package cn.xdaoy.utils.ftp;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * ftp interface
 * for ftp & sftp
 * 
 * @author xdtand
 *
 */
public interface FtpInterface {
	
	/**
	 * login
	 * @return
	 */
	FtpInterface login()  throws Exception;
	
	/**
	 * logout
	 */
	void logout() throws Exception;
	
	/**
	 * upload
	 * @param ftpdir: remote full directory, like 'upload/test'
	 * @param ftpname: remote file name,like '1.txt'
	 * @param filename: local full file name, like '/data/test/1.txt'
	 * @throws Exception
	 */
	void upload(String ftpdir, String ftpname, String filename) throws Exception;
	
	/**
	 * upload
	 * @param ftpdir: remote full directory, like 'upload/test'
	 * @param ftpname: remote file name,like '1.txt'
	 * @param inputStream
	 * @throws Exception
	 */
	void upload(String ftpdir, String ftpname, InputStream inputStream) throws Exception;
	
	/**
	 * download
	 * @param ftpdir: remote full directory, like 'upload/test'
	 * @param ftpname: remote file name,like '1.txt'
	 * @param filename: local full file name, like '/data/test/1.txt'
	 * @throws Exception
	 */
	void download(String ftpdir, String ftpname, String filename) throws Exception;
	
	/**
	 * download
	 * @param ftpdir: remote full directory, like 'upload/test'
	 * @param ftpname: remote file name,like '1.txt'
	 * @param outputStream
	 * @throws Exception
	 */
	void download(String ftpdir, String ftpname, OutputStream outputStream) throws Exception;
	
	/**
	 * delete
	 * @param ftpdir: remote full directory, like 'upload/test'
	 * @param ftpname: remote file name,like '1.txt'
	 * @throws Exception
	 */
	void delete(String ftpdir, String ftpname) throws Exception;
	
	/**
	 * mkdir
	 * @param ftpdir: remote directory, like 'upload/test'
	 * @throws Exception
	 */
	void mkdir(String ftpdir) throws Exception;
	
	/**
	 * list files
	 * @param ftpdir: remote directory, like 'upload/test'
	 * @return
	 * @throws Exception
	 */
	List<?> list(String ftpdir) throws Exception;

}
