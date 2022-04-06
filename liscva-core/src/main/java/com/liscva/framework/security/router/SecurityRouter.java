package com.liscva.framework.security.router;

import com.liscva.framework.core.fun.CoreFunction;
import com.liscva.framework.core.fun.ParamFunction;
import com.liscva.framework.core.fun.ParamRetFunction;
import com.liscva.framework.security.SecurityManager;
import com.liscva.framework.security.context.SecurityHolder;
import com.liscva.framework.security.exception.BackResultException;
import com.liscva.framework.security.exception.StopMatchException;

import java.util.List;

/**
 * 路由匹配操作工具类
 *
 */
public class SecurityRouter {

	// -------------------- 路由匹配相关 -------------------- 
	
	/**
	 * 路由匹配
	 * @param pattern 路由匹配符 
	 * @param path 被匹配的路由  
	 * @return 是否匹配成功 
	 */
	public static boolean isMatch(String pattern, String path) {
		return SecurityManager.getSecurityContext().matchPath(pattern, path);
	}

	/**
	 * 路由匹配   
	 * @param patterns 路由匹配符集合 
	 * @param path 被匹配的路由  
	 * @return 是否匹配成功 
	 */
	public static boolean isMatch(List<String> patterns, String path) {
		if(patterns == null) {
			return false;
		}
		for (String pattern : patterns) {
			if(isMatch(pattern, path)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 路由匹配   
	 * @param patterns 路由匹配符数组  
	 * @param path 被匹配的路由  
	 * @return 是否匹配成功 
	 */
	public static boolean isMatch(String[] patterns, String path) {
		if(patterns == null) {
			return false;
		}
		for (String pattern : patterns) {
			if(isMatch(pattern, path)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Http请求方法匹配 
	 * @param methods Http请求方法断言数组  
	 * @param methodString Http请求方法
	 * @return 是否匹配成功 
	 */
	public static boolean isMatch(SecurityHttpMethod[] methods, String methodString) {
		if(methods == null) {
			return false;
		}
		for (SecurityHttpMethod method : methods) {
			if(method == SecurityHttpMethod.ALL || (method != null && method.toString().equalsIgnoreCase(methodString))) {
				return true;
			}
		}
		return false;
	}
	
	// ------ 使用当前URI匹配 
	
	/**
	 * 路由匹配 (使用当前URI) 
	 * @param pattern 路由匹配符 
	 * @return 是否匹配成功 
	 */
	public static boolean isMatchCurrURI(String pattern) {
		return isMatch(pattern, SecurityHolder.getRequest().getRequestPath());
	}

	/**
	 * 路由匹配 (使用当前URI) 
	 * @param patterns 路由匹配符集合 
	 * @return 是否匹配成功 
	 */
	public static boolean isMatchCurrURI(List<String> patterns) {
		return isMatch(patterns, SecurityHolder.getRequest().getRequestPath());
	}

	/**
	 * 路由匹配 (使用当前URI) 
	 * @param patterns 路由匹配符数组 
	 * @return 是否匹配成功 
	 */
	public static boolean isMatchCurrURI(String[] patterns) {
		return isMatch(patterns, SecurityHolder.getRequest().getRequestPath());
	}

	/**
	 * Http请求方法匹配 (使用当前请求方式) 
	 * @param methods Http请求方法断言数组  
	 * @return 是否匹配成功 
	 */
	public static boolean isMatchCurrMethod(SecurityHttpMethod[] methods) {
		return isMatch(methods, SecurityHolder.getRequest().getMethod());
	}
	

	// -------------------- 开始匹配 -------------------- 
	
	/**
	 * 初始化一个SecurityRouterStaff，开始匹配
	 * @return SecurityRouterStaff
	 */
	public static SecurityRouterStaff newMatch() {
		return new SecurityRouterStaff();
	}

	// ----------------- path匹配 
	
	/**
	 * 路由匹配 
	 * @param patterns 路由匹配符集合
	 * @return SaRouterStaff
	 */
	public static SecurityRouterStaff match(String... patterns) {
		return new SecurityRouterStaff().match(patterns);
	}

	/**
	 * 路由匹配排除 
	 * @param patterns 路由匹配符排除数组  
	 * @return SaRouterStaff
	 */
	public static SecurityRouterStaff notMatch(String... patterns) {
		return new SecurityRouterStaff().notMatch(patterns);
	}

	/**
	 * 路由匹配 
	 * @param patterns 路由匹配符集合 
	 * @return 对象自身 
	 */
	public static SecurityRouterStaff match(List<String> patterns) {
		return new SecurityRouterStaff().match(patterns);
	}

	/**
	 * 路由匹配排除 
	 * @param patterns 路由匹配符排除集合 
	 * @return 对象自身 
	 */
	public static SecurityRouterStaff notMatch(List<String> patterns) {
		return new SecurityRouterStaff().notMatch(patterns);
	}

	// ----------------- Method匹配 
	
	/**
	 * Http请求方式匹配 (Enum) 
	 * @param methods Http请求方法断言数组  
	 * @return SaRouterStaff
	 */
	public static SecurityRouterStaff match(SecurityHttpMethod... methods) {
		return new SecurityRouterStaff().match(methods);
	}

	/**
	 * Http请求方法匹配排除 (Enum) 
	 * @param methods Http请求方法断言排除数组  
	 * @return SaRouterStaff
	 */
	public static SecurityRouterStaff notMatch(SecurityHttpMethod... methods) {
		return new SecurityRouterStaff().notMatch(methods);
	}

	/**
	 * Http请求方法匹配 (String)  
	 * @param methods Http请求方法断言数组  
	 * @return SaRouterStaff
	 */
	public static SecurityRouterStaff matchMethod(String... methods) {
		return new SecurityRouterStaff().matchMethod(methods);
	}

	/**
	 * Http请求方法匹配排除 (String) 
	 * @param methods Http请求方法断言排除数组  
	 * @return SaRouterStaff
	 */
	public static SecurityRouterStaff notMatchMethod(String... methods) {
		return new SecurityRouterStaff().notMatchMethod(methods);
	}
	
	// ----------------- 条件匹配 

	/**
	 * 根据 boolean 值进行匹配 
	 * @param flag boolean值 
	 * @return SaRouterStaff
	 */
	public static SecurityRouterStaff match(boolean flag) {
		return new SecurityRouterStaff().match(flag);
	}

	/**
	 * 根据 boolean 值进行匹配排除 
	 * @param flag boolean值 
	 * @return SaRouterStaff
	 */
	public static SecurityRouterStaff notMatch(boolean flag) {
		return new SecurityRouterStaff().notMatch(flag);
	}
	
	/**
	 * 根据自定义方法进行匹配 (lazy) 
	 * @param fun 自定义方法
	 * @return SaRouterStaff
	 */
	public static SecurityRouterStaff match(ParamRetFunction<Object, Boolean> fun) {
		return new SecurityRouterStaff().match(fun);
	}

	/**
	 * 根据自定义方法进行匹配排除 (lazy) 
	 * @param fun 自定义排除方法
	 * @return SaRouterStaff
	 */
	public static SecurityRouterStaff notMatch(ParamRetFunction<Object, Boolean> fun) {
		return new SecurityRouterStaff().notMatch(fun);
	}

	
	// -------------------- 直接指定check函数 -------------------- 
	
	/**
	 * 路由匹配，如果匹配成功则执行认证函数 
	 * @param pattern 路由匹配符
	 * @param fun 要执行的校验方法 
	 * @return /
	 */
	public static SecurityRouterStaff match(String pattern, CoreFunction fun) {
		return new SecurityRouterStaff().match(pattern, fun);
	}

	/**
	 * 路由匹配，如果匹配成功则执行认证函数 
	 * @param pattern 路由匹配符
	 * @param fun 要执行的校验方法 
	 * @return /
	 */
	public static SecurityRouterStaff match(String pattern, ParamFunction<SecurityRouterStaff> fun) {
		return new SecurityRouterStaff().match(pattern, fun);
	}

	/**
	 * 路由匹配 (并指定排除匹配符)，如果匹配成功则执行认证函数 
	 * @param pattern 路由匹配符 
	 * @param excludePattern 要排除的路由匹配符 
	 * @param fun 要执行的方法 
	 * @return /
	 */
	public static SecurityRouterStaff match(String pattern, String excludePattern, CoreFunction fun) {
		return new SecurityRouterStaff().match(pattern, excludePattern, fun);
	}

	/**
	 * 路由匹配 (并指定排除匹配符)，如果匹配成功则执行认证函数 
	 * @param pattern 路由匹配符 
	 * @param excludePattern 要排除的路由匹配符 
	 * @param fun 要执行的方法 
	 * @return /
	 */
	public static SecurityRouterStaff match(String pattern, String excludePattern, ParamFunction<SecurityRouterStaff> fun) {
		return new SecurityRouterStaff().match(pattern, excludePattern, fun);
	}

	
	// -------------------- 提前退出 -------------------- 
	
	/**
	 * 停止匹配，跳出函数 (在多个匹配链中一次性跳出Auth函数) 
	 * @return SaRouterStaff
	 */
	public static SecurityRouterStaff stop() {
		throw new StopMatchException();
	}

	/**
	 * 停止匹配，结束执行，向前端返回结果 
	 * @return SaRouterStaff
	 */
	public static SecurityRouterStaff back() {
		throw new BackResultException("");
	}
	
	/**
	 * 停止匹配，结束执行，向前端返回结果 
	 * @param result 要输出的结果 
	 * @return SaRouterStaff
	 */
	public static SecurityRouterStaff back(Object result) {
		throw new BackResultException(result);
	}


	// -------------------- 历史API兼容 -------------------- 
	
	/**
	 * <h1>本函数设计已过时，请更换为：SaRouter.match(path...).ckeck(fun) </h1>
	 * 路由匹配，如果匹配成功则执行认证函数 
	 * @param patterns 路由匹配符集合
	 * @param function 要执行的方法 
	 */
	@Deprecated
	public static void match(List<String> patterns, CoreFunction function) {
		if(isMatchCurrURI(patterns)) {
			function.run();
		}
	}

	/**
	 * <h1>本函数设计已过时，请更换为：SaRouter.match(path...).notMatch(path...).ckeck(fun) </h1>
	 * 路由匹配 (并指定排除匹配符)，如果匹配成功则执行认证函数 
	 * @param patterns 路由匹配符集合
	 * @param excludePatterns 要排除的路由匹配符集合
	 * @param function 要执行的方法 
	 */
	@Deprecated
	public static void match(List<String> patterns, List<String> excludePatterns, CoreFunction function) {
		if(isMatchCurrURI(patterns)) {
			if(isMatchCurrURI(excludePatterns) == false) {
				function.run();
			}
		}
	}

}
