package cn.xdaoy.utils.sm;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Security;

public class GMBaseUtil {
	static {
		Security.addProvider(new BouncyCastleProvider());
	}
}
