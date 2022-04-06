package com.liscva.framework.security.exception;

import com.liscva.framework.core.exception.CoreException;

/**
 * 一个异常：代表停止路由匹配，进入Controller 
 * 
 * 
 */
public class StopMatchException extends CoreException {

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 6806129545290130143L;

	/**
	 * 构造 
	 */
	public StopMatchException() {
		super("停止匹配");
	}

}
