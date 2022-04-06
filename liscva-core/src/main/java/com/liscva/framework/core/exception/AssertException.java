package com.liscva.framework.core.exception;

/**
 * 一个异常：代表 API 已被禁用 
 * 
 */
public class AssertException extends CoreException {

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 6806129545290130133L;

	/** 异常提示语 */
	public static final String BE_MESSAGE = "参数受检异常";

	/**
	 * 一个异常：代表 API 已被禁用
	 */
	public AssertException() {
		super(BE_MESSAGE);
	}

	/**
	 * 一个异常：代表 API 已被禁用
	 * @param message 异常描述
	 */
	public AssertException(String message) {
		super(message);
	}
}
