package com.liscva.framework.core.fun;

/**
 * 设定一个函数，方便在Lambda表达式下的函数式编程
 * 
 * getSecurityInfoDao()
 *
 */
@FunctionalInterface
public interface CoreFunction {

	/**
	 * 执行的方法
	 */
	public void run();

}
