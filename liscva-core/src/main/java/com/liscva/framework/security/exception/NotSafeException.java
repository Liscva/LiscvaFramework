package com.liscva.framework.security.exception;

import com.liscva.framework.core.exception.CoreException;

/**
 * 一个异常：代表会话未能通过二级认证 
 * 
 * 
 */
public class NotSafeException extends CoreException {

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 6806129545290130144L;
	
	/** 异常提示语 */
	public static final String BE_MESSAGE = "二级认证失败";

	/**
	 * 一个异常：代表会话未通过二级认证 
	 */
	public NotSafeException() {
		super(BE_MESSAGE);
	}

}
