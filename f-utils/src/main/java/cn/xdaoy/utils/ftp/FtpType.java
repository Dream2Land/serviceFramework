package cn.xdaoy.utils.ftp;

public enum FtpType {
	
	FTP("FTP"),
    SFTP("SFTP");
    
    private String value;
    
    private FtpType(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
}
