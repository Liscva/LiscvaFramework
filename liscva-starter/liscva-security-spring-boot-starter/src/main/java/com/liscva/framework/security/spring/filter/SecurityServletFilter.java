package com.liscva.framework.security.spring.filter;


import com.liscva.framework.CoreConsts;
import com.liscva.framework.core.exception.CoreException;
import com.liscva.framework.security.exception.BackResultException;
import com.liscva.framework.security.exception.StopMatchException;
import com.liscva.framework.security.filter.SecurityFilterAuthStrategy;
import com.liscva.framework.security.filter.SecurityFilterErrorStrategy;
import com.liscva.framework.security.router.SecurityRouter;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Servlet全局过滤器
 *
 */
@Order(CoreConsts.ASSEMBLY_ORDER)
public class SecurityServletFilter implements Filter {

	// ------------------------ 设置此过滤器 拦截 & 放行 的路由 

	/**
	 * 拦截路由 
	 */
	private List<String> includeList = new ArrayList<>();

	/**
	 * 放行路由 
	 */
	private List<String> excludeList = new ArrayList<>();

	/**
	 * 添加 [拦截路由] 
	 * @param paths 路由
	 * @return 对象自身
	 */
	public SecurityServletFilter addInclude(String... paths) {
		includeList.addAll(Arrays.asList(paths));
		return this;
	}
	
	/**
	 * 添加 [放行路由]
	 * @param paths 路由
	 * @return 对象自身
	 */
	public SecurityServletFilter addExclude(String... paths) {
		excludeList.addAll(Arrays.asList(paths));
		return this;
	}

	/**
	 * 写入 [拦截路由] 集合
	 * @param pathList 路由集合 
	 * @return 对象自身
	 */
	public SecurityServletFilter setIncludeList(List<String> pathList) {
		includeList = pathList;
		return this;
	}
	
	/**
	 * 写入 [放行路由] 集合
	 * @param pathList 路由集合 
	 * @return 对象自身
	 */
	public SecurityServletFilter setExcludeList(List<String> pathList) {
		excludeList = pathList;
		return this;
	}
	
	/**
	 * 获取 [拦截路由] 集合
	 * @return see note 
	 */
	public List<String> getIncludeList() {
		return includeList;
	}
	
	/**
	 * 获取 [放行路由] 集合
	 * @return see note 
	 */
	public List<String> getExcludeList() {
		return excludeList;
	}


	// ------------------------ 钩子函数
	
	/**
	 * 认证函数：每次请求执行 
	 */
	public SecurityFilterAuthStrategy auth = r -> {};

	/**
	 * 异常处理函数：每次[认证函数]发生异常时执行此函数
	 */
	public SecurityFilterErrorStrategy error = e -> {
		throw new CoreException(e);
	};

	/**
	 * 前置函数：在每次[认证函数]之前执行 
	 */
	public SecurityFilterAuthStrategy beforeAuth = r -> {};

	/**
	 * 写入[认证函数]: 每次请求执行 
	 * @param auth see note 
	 * @return 对象自身
	 */
	public SecurityServletFilter setAuth(SecurityFilterAuthStrategy auth) {
		this.auth = auth;
		return this;
	}

	/**
	 * 写入[异常处理函数]：每次[认证函数]发生异常时执行此函数 
	 * @param error see note 
	 * @return 对象自身
	 */
	public SecurityServletFilter setError(SecurityFilterErrorStrategy error) {
		this.error = error;
		return this;
	}

	/**
	 * 写入[前置函数]：在每次[认证函数]之前执行
	 * @param beforeAuth see note 
	 * @return 对象自身
	 */
	public SecurityServletFilter setBeforeAuth(SecurityFilterAuthStrategy beforeAuth) {
		this.beforeAuth = beforeAuth;
		return this;
	}

	
	// ------------------------ doFilter

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		try {
			// 执行全局过滤器 
			SecurityRouter.match(includeList).notMatch(excludeList).check(r -> {
				beforeAuth.run(null);
				auth.run(null);
			});
			
		} catch (StopMatchException e) {
			
		} catch (Throwable e) {
			// 1. 获取异常处理策略结果 
			String result = (e instanceof BackResultException) ? e.getMessage() : String.valueOf(error.run(e));
			
			// 2. 写入输出流 
			if(response.getContentType() == null) {
				response.setContentType("text/plain; charset=utf-8"); 
			}
			response.getWriter().print(result);
			return;
		}
		
		// 执行 
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}
	
	@Override
	public void destroy() {
	}

	
	
}
