package com.liscva.framework.security.context;


import com.liscva.framework.core.context.model.LiscvaRequest;
import com.liscva.framework.core.context.model.LiscvaResponse;
import com.liscva.framework.core.context.model.LiscvaStorage;
import com.liscva.framework.core.exception.CoreException;

/**
 * 上下文处理器 [默认错误实现类]
 * 
 * <p>  
 * 一般情况下框架会为你自动注入合适的上下文处理器，如果走到这里，就代表框架引入错误
 * </p>
 *
 */
public class SecurityContextDefaultImpl implements SecurityContext {
	
	/**
	 * 默认的上下文处理器对象  
	 */
	public static SecurityContext defaultContext = new SecurityContextDefaultImpl();

	/**
	 * 默认的错误提示语 
	 */
	public static final String ERROR_MESSAGE = "未初始化任何有效上下文处理器";
	
	/**
	 * 获取当前请求的 [Request] 对象
	 */
	@Override
	public LiscvaRequest getRequest() {
		throw new CoreException(ERROR_MESSAGE);
	}
	
	/**
	 * 获取当前请求的 [Response] 对象
	 */
	@Override
	public LiscvaResponse getResponse() {
		throw new CoreException(ERROR_MESSAGE);
	}

	/**
	 * 获取当前请求的 [存储器] 对象 
	 */
	@Override
	public LiscvaStorage getStorage() {
		throw new CoreException(ERROR_MESSAGE);
	}


	/**
	 * 校验指定路由匹配符是否可以匹配成功指定路径 
	 */
	@Override
	public boolean matchPath(String pattern, String path) {
		throw new CoreException(ERROR_MESSAGE);
	}

	
}
