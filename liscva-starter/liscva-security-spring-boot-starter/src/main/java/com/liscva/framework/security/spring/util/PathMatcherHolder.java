package com.liscva.framework.security.spring.util;

import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

public class PathMatcherHolder {

	/**
	 * 路由匹配器
	 */
	public static PathMatcher pathMatcher;

	/**
	 * 获取路由匹配器
	 * @return 路由匹配器
	 */
	public static PathMatcher getPathMatcher() {
		if(pathMatcher == null) {
			pathMatcher = new AntPathMatcher();
		}
		return pathMatcher;
	}
	
	/**
	 * 写入路由匹配器
	 * @param pathMatcher 路由匹配器
	 */
	public static void setPathMatcher(PathMatcher pathMatcher) {
		PathMatcherHolder.pathMatcher = pathMatcher;
	}
	
}
