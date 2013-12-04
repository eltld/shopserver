
package com.server.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public final class StringUtils {
	@SuppressWarnings("unused")
    private static Log log = LogFactory.getLog(StringUtils.class);

	private StringUtils() {
	}
	
	public static String toString(Object obj) {
		return (obj == null) ? null : obj.toString();
	}

	/**
	 * 首字母小写
	 * 
	 * @param s
	 *            String
	 * @return String
	 */
	public static String firstCharLowerCase(String s) {
		if (isValid(s)) {
			return s.substring(0, 1).toLowerCase() + s.substring(1);
		}
		return s;
	}

	/**
	 * 首字母大写
	 * 
	 * @param s
	 *            String
	 * @return String
	 */
	public static String firstCharUpperCase(String s) {
		if (isValid(s)) {
			return s.substring(0, 1).toUpperCase() + s.substring(1);
		}
		return s;
	}

	/**
	 * 检查对象是否有效 obj != null && obj.toString().length() > 0
	 * 
	 * @param obj
	 * @return boolean
	 */
	public static boolean isValid(Object obj) {
		return obj != null && obj.toString().length() > 0;
	}

	/**
	 * 转化为String对象
	 * 
	 * @param obj
	 * @return boolean
	 */
	public static String asString(Object obj) {
		return obj != null ? obj.toString() : "";
	}

	/**
	 * 返回其中一个有效的对象 value != null && value.toString().length() > 0
	 * 
	 * @param values
	 */
	public static String tryThese(String... values) {
		for (int i = 0; i < values.length; i++) {
			String value = values[i];
			if (StringUtils.isValid(value)) {
				return value;
			}
		}
		return null;
	}

	/**
	 * 连接字符串
	 * 
	 * @param list
	 * @param charactor
	 * @return
	 */
    public static String jion(List<Object> list, String charactor) {
		StringBuffer s = new StringBuffer();
		for (int i = 0; i < list.size(); i++) {
			s.append(list.get(i)).append(charactor);
		}
		return s.toString();
	}

	/**
	 * 连接字符串
	 * 
	 * @param list
	 * @param charactor
	 * @return
	 */
    public static String jion(List<Object> list, String charactor, boolean tail) {
		String s = jion(list, charactor);
		if (tail)
			return s;
		else
			return s.substring(0, s.length() - charactor.length());
	}

	/**
	 * 取得匹配的字符串
	 * 
	 * @param input
	 * @param regex
	 * @return
	 */
	public static List<String> matchs(String input, String regex) {
		return matchs(input, regex, 0);
	}

	/**
	 * 取得匹配的字符串
	 * 
	 * @param input
	 * @param regex
	 * @return
	 */
	public static List<String> matchs(String input, String regex, int group) {
		Pattern pattern = Pattern.compile(regex);
		Matcher match = pattern.matcher(input);
		List<String> matches = new ArrayList<String>();
		while (match.find()) {
			matches.add(match.group(group));
		}
		return matches;
	}

	/**
	 * 截取指定长度字符串
	 * 
	 * @param input
	 * @param tail
	 * @param length
	 * @return
	 */
	public static String getShorterString(String str, int maxLength) {
		return getShorterString(str, "...", maxLength);
	}

	/**
	 * 截取指定长度字符串
	 * 
	 * @param input
	 * @param tail
	 * @param length
	 * @return
	 */
	public static String getShorterString(String input, String tail, int length) {
		tail = isValid(tail) ? tail : "";
		StringBuffer buffer = new StringBuffer(512);
		try {
			int len = input.getBytes("GBK").length;
			if (len > length) {
				int ln = 0;
				for (int i = 0; ln < length; i++) {
					String temp = input.substring(i, i + 1);
					if (temp.getBytes("GBK").length == 2)
						ln += 2;
					else
						ln++;

					if (ln <= length)
						buffer.append(temp);
				}
			} else {
				return input;
			}
			buffer.append(tail);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}

	/**
	 * 取得GBK编码
	 * 
	 * @return
	 */
	public static String getBytesString(String input, String code) {
		try {
			byte[] b = input.getBytes(code);
			return Arrays.toString(b);
		} catch (UnsupportedEncodingException e) {
			return String.valueOf(code.hashCode());
		}
	}

	/**
	 * 转换格式 CUST_INFO_ID - > custInfoId
	 * 
	 * @param input
	 * @return
	 */
	public static String getFieldString(String input) {
		String field = input.toLowerCase();
		String[] values = field.split("\\_");
		StringBuffer b = new StringBuffer(input.length());
		for (int i = 0; i < values.length; i++) {
			if (i == 0)
				b.append(values[i]);
			else
				b.append(firstCharUpperCase(values[i]));
		}
		return b.toString();
	}

	/**
	 * 转化为JSON值
	 * 
	 * @param value
	 * @return
	 * @throws IOException
	 */
	public static String toJsonValue(Object value) throws IOException {
		if (value instanceof Number) {
			return value.toString();
		} else {
			return "'" + value.toString() + "'";
		}
	}


	/**
	 * Bean对象转化为String
	 * 
	 * @param bean
	 * @return
	 */
    public static String bean2String(Object bean) {
		final String GET = "get";
		StringBuilder java = new StringBuilder(1024);
		java.append("{");
		try {
			Class<?> cls = bean.getClass();
			Method[] methods = cls.getMethods();
			for (int i = 0; i < methods.length; i++) {
				String methodName = methods[i].getName();
				int mod = methods[i].getModifiers();
				if (methodName.startsWith(GET) && Modifier.isPublic(mod) && methods[i].getParameterTypes().length == 0) {
					Object value = methods[i].invoke(bean, new Object[] {});
					String fieldName = methodName.substring(GET.length());
					java.append(StringUtils.firstCharLowerCase(fieldName) + "=" + value + ";");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		java.append("}");
		return java.toString();
	}
    
    public static boolean isEmpty(List<?> list){
        return (list == null || list.size() == 0);
    }
    
    public static <T>boolean isEmpty(T [] array){
        return ((array == null) || (array.length) == 0);
    }
}
