package com.liscva.framework.core.exception;

/**
 * 一个异常：代表 API 已被禁用 
 * 
 */
public class NoSysConfigException extends CoreException {

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 6806129545290130133L;

	/** 异常提示语 */
	public static final String BE_MESSAGE = "系统配置不存在";

	/**
	 * 一个异常：代表 API 已被禁用
	 */
	public NoSysConfigException() {
		super(BE_MESSAGE);
	}

	/**
	 * 一个异常
	 * @param cause 异常堆栈
	 */
	public NoSysConfigException(Throwable cause) {
		this(BE_MESSAGE,cause);
	}

	public NoSysConfigException(String message, Throwable cause) {
		super(message, cause);
	}
}
