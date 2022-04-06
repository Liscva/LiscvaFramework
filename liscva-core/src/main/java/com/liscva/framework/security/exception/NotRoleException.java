package com.liscva.framework.security.exception;

import com.liscva.framework.core.exception.CoreException;
import com.liscva.framework.security.lsp.LspUtil;

/**
 * 一个异常：代表会话未能通过角色认证 
 * 
 * 
 *
 */
public class NotRoleException extends CoreException {

	/**
	 * 序列化版本号 
	 */
	private static final long serialVersionUID = 8243974276159004739L;

	/** 角色标识 */
	private String role;

	/**
	 * @return 获得角色标识
	 */
	public String getRole() {
		return role;
	}

	/**
	 * 账号类型
	 */
	private String loginType;

	/**
	 * 获得账号类型
	 * 
	 * @return 账号类型
	 */
	public String getLoginType() {
		return loginType;
	}

	public NotRoleException(String role) {
		this(role, LspUtil.lspLogic.loginType);
	}

	public NotRoleException(String role, String loginType) {
		super("无此角色：" + role);
		this.role = role;
		this.loginType = loginType;
	}

}
