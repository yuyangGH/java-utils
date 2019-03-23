package com.qianqiangongzi.utils;

import java.util.Map.Entry;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 在不知道Json字符串key和value的情况下，按照某种规则统一替换和改变value的值。
 *
 * @author 谦谦公子爱编程
 */
public class JsonValueReplaceUtils {
	/**
	 * 转义json字符串里面的value的特殊字符，key不用管
	 * 
	 * @param jsonStr
	 * @return
	 */
	public static String replaceJsonValue(String jsonStr) {
		JSONObject obj = JSON.parseObject(jsonStr);
		replaceValue(obj);
		return obj.toJSONString();
	}

	/**
	 * 递归转义value的值
	 * 
	 * @param obj
	 */
	private static void replaceValue(JSONObject obj) {
		Set<Entry<String, Object>> keys = obj.entrySet();
		keys.forEach(key -> {
			Object value = obj.get(key.getKey());
			if (value instanceof JSONObject) {
				replaceValue((JSONObject) value);
			} else if (value instanceof String) {
				obj.put(key.getKey(), escapeQueryChars((String) value));
			}
		});
	}

	/**
	 * solr检索时，转换特殊字符
	 * 
	 * @author Lin
	 * @date 2017年5月24日
	 * @param s
	 * @return
	 */
	public static String escapeQueryChars(String s) {
		if (s == null) {
			return s;
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			// These characters are part of the query syntax and must be escaped
			if (c == '\\' || c == '+' || c == '-' || c == '!' || c == '(' || c == ')' || c == ':' || c == '^'
					|| c == '[' || c == ']' || c == '\"' || c == '{' || c == '}' || c == '~' || c == '*' || c == '?'
					|| c == '|' || c == '&' || c == ';' || c == '/' || c == '.' || c == '$'
					|| Character.isWhitespace(c)) {
				sb.append('\\');
			}
			sb.append(c);
		}
		return sb.toString();
	}
}
