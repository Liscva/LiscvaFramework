package com.liscva.framework.web.model;

import com.liscva.framework.core.exception.CoreException;
import com.liscva.framework.core.context.model.LiscvaRequest;
import com.liscva.framework.core.util.FoxUtil;
import com.liscva.framework.security.SecurityManager;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Request for Servlet
 *
 */
public class RequestForServlet implements LiscvaRequest {

	/**
	 * 底层Request对象
	 */
	protected HttpServletRequest request;
	
	/**
	 * 实例化
	 * @param request request对象 
	 */
	public RequestForServlet(HttpServletRequest request) {
		this.request = request;
	}
	
	/**
	 * 获取底层源对象 
	 */
	@Override
	public Object getSource() {
		return request;
	}

	/**
	 * 在 [请求体] 里获取一个值 
	 */
	@Override
	public String getParam(String name) {
		return request.getParameter(name);
	}

	/**
	 * 在 [请求头] 里获取一个值 
	 */
	@Override
	public String getHeader(String name) {
		return request.getHeader(name);
	}

	/**
	 * 在 [Cookie作用域] 里获取一个值 
	 */
	@Override
	public String getCookieValue(String name) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie != null && name.equals(cookie.getName())) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}

	/**
	 * 返回当前请求path (不包括上下文名称) 
	 */
	@Override
	public String getRequestPath() {
		return request.getServletPath();
	}

	/**
	 * 返回当前请求的url，例：http://xxx.com/test
	 * @return see note
	 */
	public String getUrl() {
		String currDomain = SecurityManager.getConfig().getCurrDomain();
		if(FoxUtil.isEmpty(currDomain) == false) {
			return currDomain + this.getRequestPath();
		}
		return request.getRequestURL().toString();
	}
	
	/**
	 * 返回当前请求的类型 
	 */
	@Override
	public String getMethod() {
		return request.getMethod();
	}

	/**
	 * 转发请求 
	 */
	@Override
	public Object forward(String path) {
		try {
			HttpServletResponse response = (HttpServletResponse)SecurityManager.getSecurityContext().getResponse().getSource();
			request.getRequestDispatcher(path).forward(request, response);
			return null;
		} catch (ServletException | IOException e) {
			throw new CoreException(e);
		}
	}
	
}
