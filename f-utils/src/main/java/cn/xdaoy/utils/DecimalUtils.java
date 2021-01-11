package cn.xdaoy.utils;

import java.math.BigDecimal;

public class DecimalUtils {
	
	private static final int scale = 2;
	
	public static BigDecimal add(String a,String b) throws Exception {
		if(StringUtils.isEmpty(a) || StringUtils.isEmpty(b)) {
			throw new Exception("参数错误");
		}
		BigDecimal stra = new BigDecimal(a);
		stra.setScale(scale);
		BigDecimal strb = new BigDecimal(b);
		strb.setScale(scale);
		return stra.add(strb);
	}
	
	public static BigDecimal subtract(String a,String b) throws Exception {
		if(StringUtils.isEmpty(a) || StringUtils.isEmpty(b)) {
			throw new Exception("参数错误");
		}
		BigDecimal stra = new BigDecimal(a);
		stra.setScale(scale);
		BigDecimal strb = new BigDecimal(b);
		strb.setScale(scale);
		return stra.subtract(strb);
	}
	
	public static BigDecimal multiply(String a,String b) throws Exception {
		if(StringUtils.isEmpty(a) || StringUtils.isEmpty(b)) {
			throw new Exception("参数错误");
		}
		BigDecimal stra = new BigDecimal(a);
		stra.setScale(scale);
		BigDecimal strb = new BigDecimal(b);
		strb.setScale(scale);
		return stra.multiply(strb).setScale(scale);
	}
	
	public static BigDecimal divide(String a,String b) throws Exception {
		if(StringUtils.isEmpty(a) || StringUtils.isEmpty(b)) {
			throw new Exception("参数错误");
		}
		BigDecimal strb = new BigDecimal(b);
		if(strb.compareTo(BigDecimal.ZERO)==0){
			throw new Exception("被除数不能为0");
		}
		BigDecimal stra = new BigDecimal(a);
		stra.setScale(scale);
		
		strb.setScale(scale);
		return stra.divide(strb).setScale(scale);
	
	}
}
