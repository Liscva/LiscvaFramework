package com.liscva.framework.security.router;


import com.liscva.framework.core.context.model.LiscvaRequest;
import com.liscva.framework.core.context.model.LiscvaResponse;

/**
 * 路由拦截器验证方法Lambda
 */
@FunctionalInterface
public interface SecurityRouteFunction {

	/**
	 * 执行验证的方法
	 * 
	 * @param request  Request包装对象
	 * @param response Response包装对象
	 * @param handler  处理对象
	 */
	public void run(LiscvaRequest request, LiscvaResponse response, Object handler);

}
