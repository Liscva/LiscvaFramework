package com.liscva.framework.security.router;

import com.liscva.framework.core.exception.CoreException;

import java.util.HashMap;
import java.util.Map;

/**
 * Http 请求各种请求类型的枚举表示 
 * 
 * <p> 参考：Spring - HttpMethod
 *
 */
public enum SecurityHttpMethod {
	
	GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS, TRACE, CONNECT, 
	
	/**
	 * 代表全部请求方式 
	 */
	ALL;
	
	private static final Map<String, SecurityHttpMethod> map = new HashMap<>();

	static {
		for (SecurityHttpMethod reqMethod : values()) {
			map.put(reqMethod.name(), reqMethod);
		}
	}

	/**
	 * String 转 enum 
	 * @param method 请求类型 
	 * @return ReqMethod 对象 
	 */
	public static SecurityHttpMethod toEnum(String method) {
		if(method == null) {
			throw new CoreException("无效Method：" + method);
		}
		SecurityHttpMethod reqMethod = map.get(method.toUpperCase());
		if(reqMethod == null) {
			throw new CoreException("无效Method：" + method);
		}
		return reqMethod;
	}

	/**
	 * String[] 转 enum[]
	 * @param methods 请求类型数组 
	 * @return ReqMethod 对象 
	 */
	public static SecurityHttpMethod[] toEnumArray(String... methods) {
		SecurityHttpMethod[] arr = new SecurityHttpMethod[methods.length];
		for (int i = 0; i < methods.length; i++) {
			arr[i] = SecurityHttpMethod.toEnum(methods[i]);
		}
		return arr;
	}

}
