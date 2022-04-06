package com.liscva.framework.security.filter;

/**
 * 全局过滤器-异常处理策略
 */
public interface SecurityFilterErrorStrategy {
	
	/**
	 * 执行方法 
	 * @param e 异常对象
	 * @return 输出对象(请提前序列化)
	 */
	public Object run(Throwable e);
	
}
