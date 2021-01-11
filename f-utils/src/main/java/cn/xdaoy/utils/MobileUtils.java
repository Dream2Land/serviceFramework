package cn.xdaoy.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * mobile check
 * 
 * @author xdtand
 *
 */
public class MobileUtils {

	static String phoneReg = "\\bNokia|SAMSUNG|MIDP-2|CLDC1.1|SymbianOS|MAUI|UNTRUSTED/1.0"
			+ "|Windows CE|iPhone|iPad|Android|BlackBerry|UCWEB|ucweb|BREW|J2ME|YULONG|YuLong|COOLPAD|TIANYU|TY-"
			+ "|K-Touch|Haier|DOPOD|Lenovo|LENOVO|HUAQIN|AIGO-|CTC/1.0"
			+ "|CTC/2.0|CMCC|DAXIAN|MOT-|SonyEricsson|GIONEE|HTC|ZTE|HUAWEI|webOS|GoBrowser|IEMobile|WAP2.0\\b";
	static String tableReg = "\\b(ipad|tablet|(Nexus 7)|up.browser" + "|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";

	// regexp：phone,pad
	static Pattern phonePat = Pattern.compile(phoneReg, Pattern.CASE_INSENSITIVE);
	static Pattern tablePat = Pattern.compile(tableReg, Pattern.CASE_INSENSITIVE);

	/**
	 * mobile check
	 * 
	 * @Title: isMobile
	 * @param userAgent
	 * @return
	 */
	public static boolean isMobile(HttpServletRequest request) {
		String userAgent = request.getHeader("USER-AGENT");
		if (StringUtils.isEmpty(userAgent)) {
			return false;
		}
		Matcher matcherPhone = phonePat.matcher(userAgent);
		Matcher matcherTable = tablePat.matcher(userAgent);
		if (matcherPhone.find() || matcherTable.find()) {
			return true;
		}
		return false;

	}

	/**
	 * check wechart
	 * 
	 * @param request
	 * @return
	 */
	public static boolean isWeChat(HttpServletRequest request) {
		String userAgent = request.getHeader("user-agent").toLowerCase();
		return userAgent == null || userAgent.indexOf("micromessenger") == -1 ? false : true;
	}
}
