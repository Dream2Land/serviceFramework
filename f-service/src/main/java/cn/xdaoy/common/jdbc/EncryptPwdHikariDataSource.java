package cn.xdaoy.common.jdbc;

import com.zaxxer.hikari.HikariDataSource;

import cn.xdaoy.utils.AESUtils;

public class EncryptPwdHikariDataSource extends HikariDataSource {
    
	private final static String PKEY ="ghj2jjo673K32bs";
	

	@Override
    public String getPassword(){
        String encPassword = super.getPassword();
        if(null==encPassword){
            return null;
        }
        try {
			return AESUtils.decrypt(encPassword, PKEY);
		} catch (Exception e) {
			System.out.println("==>enc pwd:"+encPassword);
			e.printStackTrace();
		}
        return null;
    }
}
