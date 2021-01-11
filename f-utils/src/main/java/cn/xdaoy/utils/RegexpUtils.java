package cn.xdaoy.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * string regexp util
 * 
 * @author xdtand
 *
 */
public class RegexpUtils {
	
	public static final String NUMBER_REG = "^[0-9]+$";
	
	public static final String LETTER_REG = "^[a-zA-Z]+$";
	
	public static final String LETTER_NUM_REG = "^[0-9a-zA-Z]+$";
	
	public static final String CHINESE = "^[\u4e00-\u9fa5]+$";
	
	public static final String CHINESE_LETTER_NUM_REG = "^[0-9a-zA-Z\u4e00-\u9fa5]+$";
	
	public static final String PHONE_REG = "^[1][3,4,5,7,8][0-9]{9}$";
	
	public static final String DATE_REG = "^[0-9]{4}-(((0[13578]|(10|12))-(0[1-9]|[1-2][0-9]|3[0-1]))|(02-(0[1-9]|[1-2][0-9]))|((0[469]|11)-(0[1-9]|[1-2][0-9]|30)))$";
	
	public static final String EMAIL_REG = "^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$";
	
	public static final String VALID_CHARACTER_REG = "^[0-9a-zA-z!@#_.*]+$";
	
	//身份证
	public static final String UNIQID_REG = "^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$";

	public static final String ICON_REGEXP = "^(/{0,1}//w){1,}//.(gif|dmp|png|jpg)$|^//w{1,}//.(gif|dmp|png|jpg)$";
	
	public static final String URL_REGEXP = "(//w+)://([^/:]+)(://d*)?([^#//s]*)"; 
	
	public static final String HTTP_REGEXP = "(http|https|ftp)://([^/:]+)(://d*)?([^#//s]*)";
	
	/**
	 * whether match specification regexp
	 * 
	 * @param regexp
	 * @param targetStr
	 * @return
	 */
	public static boolean isMatch(String regexp,String targetStr){
		if(StringUtils.isEmpty(targetStr) || StringUtils.isEmpty(regexp)){
			return false;
		}
		return Pattern.matches(regexp,targetStr);
	}
	
	/**
	 * whether match specification regexp or empty
	 * 
	 * @param regexp
	 * @param targetStr
	 * @return
	 */
	public static boolean isEmptyOrMatch(String regexp,String targetStr){
		if(StringUtils.isEmpty(targetStr)){
			return true;
		}
		if(StringUtils.isEmpty(regexp)){
			return false;
		}
		return Pattern.matches(regexp,targetStr);
	}
	
	/**
     * 获取正则匹配的部分
     *
     * @param regex 正则表达式
     * @param input 要匹配的字符串
     * @return 正则匹配的部分
     */
    public static List<String> getMatches(String regex, CharSequence input) {
        if (input == null) return null;
        List<String> matches = new ArrayList<>();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            matches.add(matcher.group());
        }
        return matches;
    }

    /**
     * 获取正则匹配分组
     *
     * @param input 要分组的字符串
     * @param regex 正则表达式
     * @return 正则匹配分组
     */
    public static String[] getSplits(String input, String regex) {
        if (input == null) return null;
        return input.split(regex);
    }

    /**
     * 替换正则匹配的第一部分
     *
     * @param input       要替换的字符串
     * @param regex       正则表达式
     * @param replacement 代替者
     * @return 替换正则匹配的第一部分
     */
    public static String getReplaceFirst(String input, String regex, String replacement) {
        if (input == null) return null;
        return Pattern.compile(regex).matcher(input).replaceFirst(replacement);
    }

    /**
     * 替换所有正则匹配的部分
     *
     * @param input       要替换的字符串
     * @param regex       正则表达式
     * @param replacement 代替者
     * @return 替换所有正则匹配的部分
     */
    public static String getReplaceAll(String input, String regex, String replacement) {
        if (input == null) return null;
        return Pattern.compile(regex).matcher(input).replaceAll(replacement);
    }
	
}
