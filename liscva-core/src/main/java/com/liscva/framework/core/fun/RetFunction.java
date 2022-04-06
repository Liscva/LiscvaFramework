package com.liscva.framework.core.fun;

/**
 * 设定一个函数，并返回一个值，方便在Lambda表达式下的函数式编程
 * getSecurityInfoDao()
 *
 */
@FunctionalInterface
public interface RetFunction {
	
	/**
	 * 执行的方法 
	 * @return 返回值 
	 */
	public Object run();
	
}
