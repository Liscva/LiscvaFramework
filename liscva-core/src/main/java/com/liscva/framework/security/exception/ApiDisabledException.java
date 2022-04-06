package com.liscva.framework.security.exception;

import com.liscva.framework.core.exception.CoreException;

/**
 * 一个异常：代表 API 已被禁用 
 * 
 */
public class ApiDisabledException extends CoreException {

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 6806129545290130133L;
	
	/** 异常提示语 */
	public static final String BE_MESSAGE = "This API is disabled";

	/**
	 * 一个异常：代表 API 已被禁用  
	 */
	public ApiDisabledException() {
		super(BE_MESSAGE);
	}

	/**
	 * 一个异常：代表 API 已被禁用  
	 * @param message 异常描述 
	 */
	public ApiDisabledException(String message) {
		super(message);
	}
}
