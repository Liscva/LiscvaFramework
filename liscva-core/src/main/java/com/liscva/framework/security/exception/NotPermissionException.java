package com.liscva.framework.security.exception;

import com.liscva.framework.core.exception.CoreException;
import com.liscva.framework.security.lsp.LspUtil;

/**
 * 一个异常：代表会话未能通过权限认证 
 * 
 * 
 *
 */
public class NotPermissionException extends CoreException {

	/**
	 * 序列化版本号 
	 */
	private static final long serialVersionUID = 6806129545290130141L;

	/** 权限码 */
	private String code;

	/**
	 * @return 获得权限码
	 */
	public String getCode() {
		return code;
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

	public NotPermissionException(String code) {
		this(code, LspUtil.lspLogic.loginType);
	}

	public NotPermissionException(String code, String loginType) {
		super("无此权限：" + code);
		this.code = code;
		this.loginType = loginType;
	}

}
