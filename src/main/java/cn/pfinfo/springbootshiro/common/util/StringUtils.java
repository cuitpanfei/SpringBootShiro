package cn.pfinfo.springbootshiro.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author panfei
 */
public class StringUtils{

    /**
     * @param str
     * @return
     * @Description
     */
    public static boolean isEmpty(String str) {
        if (str == null || "".equals(str.trim())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param str
     * @return
     * @Description
     */
    public static boolean isNotEmpty(String str) {
        if ((str != null) && !"".equals(str.trim())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 标签中提取数字
     *
     * @param str
     * @return
     */
    public static Integer stringGetint(String str) {
        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        String num_str = m.replaceAll("").trim();
        return Integer.parseInt(num_str);
    }

	public static String uncapitalize(String s) {
		return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
	}
}
