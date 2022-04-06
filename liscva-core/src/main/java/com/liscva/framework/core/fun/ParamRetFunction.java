package com.liscva.framework.core.fun;

/**
 * 设定一个函数，传入一个参数，并返回一个值，方便在Lambda表达式下的函数式编程
 * getSecurityInfoDao()
 *
 */
@FunctionalInterface
public interface ParamRetFunction<T, R> {
	
	/**
	 * 执行的方法 
	 * @param param 传入的参数 
	 * @return 返回值 
	 */
	public R run(T param);
	
}
