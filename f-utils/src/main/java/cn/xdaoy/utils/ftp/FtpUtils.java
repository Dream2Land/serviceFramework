package cn.xdaoy.utils.ftp;

public class FtpUtils {
	
	private FtpUtils() {}
	
	public static FtpInterface getInstance(FtpType ftpType,String host,int port,String username,String password) throws Exception {
		if(FtpType.FTP == ftpType) {
			return new FtpImpl(host,port,username,password).login();
		}
		return new SftpImpl(host,port,username,password).login();
	}
}
