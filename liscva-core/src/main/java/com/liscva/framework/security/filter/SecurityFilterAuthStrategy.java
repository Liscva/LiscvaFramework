package com.liscva.framework.security.filter;

/**
 * 全局过滤器-认证策略
 *
 */
public interface SecurityFilterAuthStrategy {
	
	/**
	 * 执行方法 
	 * @param r 无含义参数，留作扩展 
	 */
	public void run(Object r);
	
}
