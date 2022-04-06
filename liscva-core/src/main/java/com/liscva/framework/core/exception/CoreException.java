package com.liscva.framework.core.exception;

import com.liscva.framework.core.connect.FinalConnect;
import com.liscva.framework.core.util.FoxUtil;

/**
 * 框架内部逻辑发生错误抛出的异常
 * (自定义此异常方便开发者在做全局异常处理时分辨异常类型)
 *
 */
public class CoreException extends RuntimeException {

	private int code;

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 6806129545290130132L;

	/**
	 * 构建一个异常
	 * 
	 * @param message 异常描述信息
	 */
	public CoreException(String message) {
		this(FinalConnect.CODE_ERROR,message);
	}

	/**
	 * 构建一个异常
	 *
	 * @param cause 异常对象
	 */
	public CoreException(Throwable cause) {
		this(FinalConnect.CODE_ERROR,cause);
	}

	public CoreException(int code, String message) {
		super(message);
		this.code = code;
	}



	/**
	 * 构建一个异常
	 *
	 * @param code 异常代码
	 * @param cause 异常对象
	 */
	public CoreException(int code,Throwable cause) {
		super(cause);
		this.code = code;
	}


	/**
	 * 构建一个异常
	 * 
	 * @param message 异常信息
	 * @param cause 异常对象
	 */
	public CoreException(String message, Throwable cause) {
		this(FinalConnect.CODE_ERROR,message, cause);

	}

	/**
	 * 构建一个异常
	 *
	 * @param code 异常代码
	 * @param message 异常信息
	 * @param cause 异常对象
	 */
	public CoreException(int code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}


	/**
	 * 如果flag==true，则抛出message异常
	 * @param flag 标记
	 * @param message 异常信息
	 */
	public static void throwBy(boolean flag, String message) {
		if(flag) {
			throw new CoreException(message);
		}
	}

	/**
	 * 如果value==null或者isEmpty，则抛出message异常
	 * @param value 值
	 * @param message 异常信息
	 */
	public static void throwByNull(Object value, String message) {
		if(FoxUtil.isEmpty(value)) {
			throw new CoreException(message);
		}
	}
}
