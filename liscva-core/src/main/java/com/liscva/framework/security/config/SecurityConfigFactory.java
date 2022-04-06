package com.liscva.framework.security.config;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 配置文件的构建工厂类
 * <p>
 * 用于手动读取配置文件初始化 SecurityConfig 对象，只有在非IOC环境下你才会用到此类
 *
 *
 */
public class SecurityConfigFactory {

	/**
	 * 配置文件地址 
	 */
	public static String configPath = "liscva-security.properties";

	/**
	 * 根据configPath路径获取配置信息 
	 * 
	 * @return 一个SecurityConfig对象
	 */
	public static SecurityConfig createConfig() {
		Map<String, String> map = readPropToMap(configPath);
		if (map == null) {
			// throw new RuntimeException("找不到配置文件：" + configPath, null);
		}
		return (SecurityConfig) initPropByMap(map, new SecurityConfig());
	}

	/**
	 * 工具方法: 将指定路径的properties配置文件读取到Map中 
	 * 
	 * @param propertiesPath 配置文件地址
	 * @return 一个Map
	 */
	private static Map<String, String> readPropToMap(String propertiesPath) {
		Map<String, String> map = new HashMap<String, String>(16);
		try {
			InputStream is = SecurityConfigFactory.class.getClassLoader().getResourceAsStream(propertiesPath);
			if (is == null) {
				return null;
			}
			Properties prop = new Properties();
			prop.load(is);
			for (String key : prop.stringPropertyNames()) {
				map.put(key, prop.getProperty(key));
			}
		} catch (IOException e) {
			throw new RuntimeException("配置文件(" + propertiesPath + ")加载失败", e);
		}
		return map;
	}

	/**
	 * 工具方法: 将 Map 的值映射到一个 Model 上 
	 * 
	 * @param map 属性集合
	 * @param obj 对象, 或类型
	 * @return 返回实例化后的对象
	 */
	private static Object initPropByMap(Map<String, String> map, Object obj) {

		if (map == null) {
			map = new HashMap<String, String>(16);
		}

		// 1、取出类型
		Class<?> cs = null;
		if (obj instanceof Class) {
			// 如果是一个类型，则将obj=null，以便完成静态属性反射赋值
			cs = (Class<?>) obj;
			obj = null;
		} else {
			// 如果是一个对象，则取出其类型
			cs = obj.getClass();
		}

		// 2、遍历类型属性，反射赋值
		for (Field field : cs.getDeclaredFields()) {
			String value = map.get(field.getName());
			if (value == null) {
				// 如果为空代表没有配置此项
				continue;
			}
			try {
				Object valueConvert = getObjectByClass(value, field.getType());
				field.setAccessible(true);
				field.set(obj, valueConvert);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				throw new RuntimeException("属性赋值出错：" + field.getName(), e);
			}
		}
		return obj;
	}

	/**
	 * 工具方法: 将字符串转化为指定数据类型 
	 * 
	 * @param str 值
	 * @param cs  要转换的类型
	 * @return 转化好的结果
	 */
	@SuppressWarnings("unchecked")
	private static <T> T getObjectByClass(String str, Class<T> cs) {
		Object value;
		if (str == null) {
			value = null;
		} else if (cs.equals(String.class)) {
			value = str;
		} else if (cs.equals(int.class) || cs.equals(Integer.class)) {
			value = Integer.valueOf(str);
		} else if (cs.equals(long.class) || cs.equals(Long.class)) {
			value = Long.valueOf(str);
		} else if (cs.equals(short.class) || cs.equals(Short.class)) {
			value = Short.valueOf(str);
		} else if (cs.equals(float.class) || cs.equals(Float.class)) {
			value = Float.valueOf(str);
		} else if (cs.equals(double.class) || cs.equals(Double.class)) {
			value = Double.valueOf(str);
		} else if (cs.equals(boolean.class) || cs.equals(Boolean.class)) {
			value = Boolean.valueOf(str);
		} else {
			throw new RuntimeException("未能将值：" + str + "，转换类型为：" + cs, null);
		}
		return (T) value;
	}

}
