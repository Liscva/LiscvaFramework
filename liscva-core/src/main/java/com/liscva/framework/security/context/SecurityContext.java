package com.liscva.framework.security.context;

import com.liscva.framework.core.context.model.LiscvaRequest;
import com.liscva.framework.core.context.model.LiscvaResponse;
import com.liscva.framework.core.context.model.LiscvaStorage;

/**
 * 上下文处理器接口
 *
 */
public interface SecurityContext {

	/**
	 * 获取当前请求的 [Request] 对象
	 * 
	 * @return see note 
	 */
	public LiscvaRequest getRequest();

	/**
	 * 获取当前请求的 [Response] 对象
	 * 
	 * @return see note 
	 */
	public LiscvaResponse getResponse();

	/**
	 * 获取当前请求的 [存储器] 对象 
	 * 
	 * @return see note 
	 */
	public LiscvaStorage getStorage();

	/**
	 * 校验指定路由匹配符是否可以匹配成功指定路径 
	 * 
	 * @param pattern 路由匹配符 
	 * @param path 需要匹配的路径 
	 * @return see note 
	 */
	public boolean matchPath(String pattern, String path);

	/**
	 * 此上下文是否有效 
	 * @return / 
	 */
	public default boolean isValid() {
		return false;
	}
	
}
