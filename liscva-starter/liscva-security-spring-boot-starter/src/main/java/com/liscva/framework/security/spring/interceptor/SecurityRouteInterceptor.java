package com.liscva.framework.security.spring.interceptor;

import com.liscva.framework.security.exception.BackResultException;
import com.liscva.framework.security.exception.StopMatchException;
import com.liscva.framework.security.router.SecurityRouteFunction;
import com.liscva.framework.security.lsp.LspUtil;
import com.liscva.framework.web.model.RequestForServlet;
import com.liscva.framework.web.model.ResponseForServlet;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 基于路由的拦截式鉴权
 */
public class SecurityRouteInterceptor implements HandlerInterceptor {

	/**
	 * 每次进入拦截器的[执行函数]，默认为登录校验 
	 */
	public SecurityRouteFunction function = (req, res, handler) -> LspUtil.checkLogin();

	/**
	 * 创建一个路由拦截器
	 */
	public SecurityRouteInterceptor() {
	}

	/**
	 * 创建, 并指定[执行函数]
	 * @param function [执行函数]
	 */
	public SecurityRouteInterceptor(SecurityRouteFunction function) {
		this.function = function;
	}

	/**
	 * 静态方法快速构建一个 
	 * @param function 自定义模式下的执行函数
	 * @return sa路由拦截器 
	 */
	public static SecurityRouteInterceptor newInstance(SecurityRouteFunction function) {
		return new SecurityRouteInterceptor(function);
	}
	
	
	// ----------------- 验证方法 ----------------- 

	/**
	 * 每次请求之前触发的方法 
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		try {
			function.run(new RequestForServlet(request), new ResponseForServlet(response), handler);
		} catch (StopMatchException e) {
			// 停止匹配，进入Controller 
		} catch (BackResultException e) {
			// 停止匹配，向前端输出结果 
			if(response.getContentType() == null) {
				response.setContentType("text/plain; charset=utf-8"); 
			}
			response.getWriter().print(e.getMessage());
			return false;
		}
		
		// 通过验证 
		return true;
	}

}
