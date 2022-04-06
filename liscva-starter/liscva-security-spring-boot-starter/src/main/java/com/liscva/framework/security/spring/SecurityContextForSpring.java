package com.liscva.framework.security.spring;


import com.liscva.framework.core.context.model.LiscvaRequest;
import com.liscva.framework.core.context.model.LiscvaResponse;
import com.liscva.framework.core.context.model.LiscvaStorage;
import com.liscva.framework.security.context.SecurityContext;
import com.liscva.framework.security.spring.util.PathMatcherHolder;
import com.liscva.framework.security.spring.util.SpringUtil;
import com.liscva.framework.web.model.RequestForServlet;
import com.liscva.framework.web.model.ResponseForServlet;
import com.liscva.framework.web.model.StorageForServlet;

/**
 * 上下文处理器 [ SpringMVC版本实现 ]
 *
 */
public class SecurityContextForSpring implements SecurityContext {

	/**
	 * 获取当前请求的Request对象
	 */
	@Override
	public LiscvaRequest getRequest() {
		return new RequestForServlet(SpringUtil.getRequest());
	}

	/**
	 * 获取当前请求的Response对象
	 */
	@Override
	public LiscvaResponse getResponse() {
		return new ResponseForServlet(SpringUtil.getResponse());
	}

	/**
	 * 获取当前请求的 [存储器] 对象 
	 */
	@Override
	public LiscvaStorage getStorage() {
		return new StorageForServlet(SpringUtil.getRequest());
	}

	/**
	 * 校验指定路由匹配符是否可以匹配成功指定路径 
	 */
	@Override
	public boolean matchPath(String pattern, String path) {
		return PathMatcherHolder.getPathMatcher().match(pattern, path);
	}

	/**
	 * 此上下文是否有效 
	 */
	@Override
	public boolean isValid() {
		return SpringUtil.isWeb();
	}

}
