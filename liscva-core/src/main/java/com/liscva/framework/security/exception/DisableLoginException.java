package com.liscva.framework.security.exception;

import com.liscva.framework.core.exception.CoreException;

/**
 * 一个异常：代表账号已被封禁 
 * 
 * 
 */
public class DisableLoginException extends CoreException {

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 6806129545290130143L;

	/** 异常标记值 */
	public static final String BE_VALUE = "disable";
	
	/** 异常提示语 */
	public static final String BE_MESSAGE = "此账号已被封禁";

	/**
	 * 账号类型 
	 */
	private String loginType;

	/**
	 * 被封禁的账号id 
	 */
	private Object loginId;
	
	/**
	 * 封禁剩余时间，单位：秒 
	 */
	private long disableTime;
	
	/**
	 * 获取账号类型 
	 * 
	 * @return See Note
	 */
	public String getLoginType() {
		return loginType;
	}

	/**
	 * 获取: 被封禁的账号id 
	 * 
	 * @return See above
	 */
	public Object getLoginId() {
		return loginId;
	}
	
	/**
	 * 获取: 封禁剩余时间，单位：秒
	 * @return See above
	 */
	public long getDisableTime() {
		return disableTime;
	}
	
	/**
	 * 一个异常：代表账号已被封禁 
	 * 
	 * @param loginType 账号类型
	 * @param loginId  被封禁的账号id 
	 * @param disableTime 封禁剩余时间，单位：秒 
	 */
	public DisableLoginException(String loginType, Object loginId, long disableTime) {
		super(BE_MESSAGE);
		this.loginId = loginId;
		this.loginType = loginType;
		this.disableTime = disableTime;
	}

}
