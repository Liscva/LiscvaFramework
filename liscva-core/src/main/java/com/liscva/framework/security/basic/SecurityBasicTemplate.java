package com.liscva.framework.security.basic;

import com.liscva.framework.core.secure.Base64Util;
import com.liscva.framework.core.util.FoxUtil;
import com.liscva.framework.security.SecurityManager;
import com.liscva.framework.security.context.SecurityHolder;
import com.liscva.framework.security.exception.NotBasicAuthException;

/**
 * Http Basic 认证模块
 * 
 *
 */
public class SecurityBasicTemplate {
	
	/**
	 * 默认的 Realm 名称 
	 */
	public static final String DEFAULT_REALM = "Security-Info";

	/**
	 * 设置响应头，并抛出异常 
	 * @param realm 领域 
	 */
	public void throwNotBasicAuthException(String realm) {
		SecurityHolder.getResponse().setStatus(401).setHeader("WWW-Authenticate", "Basic Realm=" + realm);
		throw new NotBasicAuthException();
	}

	/**
	 * 获取浏览器提交的 Basic 参数 （裁剪掉前缀并解码）
	 * @return 值
	 */
	public String getAuthorizationValue() {
		
		// 获取请求头 Authorization 参数 
		String authorization = SecurityHolder.getRequest().getHeader("Authorization");
		
		// 如果不是以 Basic 作为前缀，则视为无效 
		if(authorization == null || authorization.startsWith("Basic ") == false) {
			return null;
		}
		
		// 裁剪前缀并解码 
		return Base64Util.decode(authorization.substring(6));
	}
	
	/**
	 * 对当前会话进行 Basic 校验（使用全局配置的账号密码），校验不通过则抛出异常  
	 */
	public void check() {
		check(DEFAULT_REALM, SecurityManager.getConfig().getBasic());
	}

	/**
	 * 对当前会话进行 Basic 校验（手动设置账号密码），校验不通过则抛出异常  
	 * @param account 账号（格式为 user:password）
	 */
	public void check(String account) {
		check(DEFAULT_REALM, account);
	}

	/**
	 * 对当前会话进行 Basic 校验（手动设置 Realm 和 账号密码），校验不通过则抛出异常 
	 * @param realm 领域 
	 * @param account 账号（格式为 user:password）
	 */
	public void check(String realm, String account) {
		if(FoxUtil.isEmpty(account)) {
			account = SecurityManager.getConfig().getBasic();
		}
		String authorization = getAuthorizationValue();
		if(FoxUtil.isEmpty(authorization) || authorization.equals(account) == false) {
			throwNotBasicAuthException(realm);
		}
	}

}
