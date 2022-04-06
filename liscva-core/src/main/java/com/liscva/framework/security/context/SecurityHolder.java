package com.liscva.framework.security.context;

import com.liscva.framework.core.context.model.LiscvaRequest;
import com.liscva.framework.core.context.model.LiscvaResponse;
import com.liscva.framework.core.context.model.LiscvaStorage;
import com.liscva.framework.security.SecurityManager;

/**
 * 上下文持有类
 *
 */
public class SecurityHolder {
	
	/**
	 * 获取当前请求的 SaTokenContext
	 * 
	 * @return see note 
	 */
	public static SecurityContext getContext() {
		return SecurityManager.getSecurityContext();
	}

	/**
	 * 获取当前请求的 [Request] 对象
	 * 
	 * @return see note 
	 */
	public static LiscvaRequest getRequest() {
		return SecurityManager.getSecurityContext().getRequest();
	}

	/**
	 * 获取当前请求的 [Response] 对象
	 * 
	 * @return see note 
	 */
	public static LiscvaResponse getResponse() {
		return SecurityManager.getSecurityContext().getResponse();
	}

	/**
	 * 获取当前请求的 [存储器] 对象 
	 * 
	 * @return see note 
	 */
	public static LiscvaStorage getStorage() {
		return SecurityManager.getSecurityContext().getStorage();
	}

}
