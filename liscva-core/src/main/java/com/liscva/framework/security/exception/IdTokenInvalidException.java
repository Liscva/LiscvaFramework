package com.liscva.framework.security.exception;

import com.liscva.framework.core.exception.CoreException;

/**
 * 一个异常：代表提供的 Id-Token 无效 
 * 
 * 
 */
public class IdTokenInvalidException extends CoreException {

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 6806129545290130144L;
	
	/**
	 * 一个异常：代表提供的 Id-Token 无效 
	 * @param message 异常描述 
	 */
	public IdTokenInvalidException(String message) {
		super(message);
	}

}
