package com.liscva.framework.security.router;



import com.liscva.framework.core.fun.CoreFunction;
import com.liscva.framework.core.fun.ParamFunction;
import com.liscva.framework.core.fun.ParamRetFunction;
import com.liscva.framework.security.exception.BackResultException;
import com.liscva.framework.security.exception.StopMatchException;

import java.util.List;

/**
 * 路由匹配操作对象
 *
 */
public class SecurityRouterStaff {

	/**
	 * 是否命中的标记变量 
	 */
	public boolean isHit = true;
	
	/**
	 * @return 是否命中 
	 */
	public boolean isHit() {
		return isHit;
	}

	/**
	 * @param isHit 命中标记 
	 * @return 对象自身 
	 */
	public SecurityRouterStaff setHit(boolean isHit) {
		this.isHit = isHit;
		return this;
	}

	/**
	 * 重置命中标记为 true 
	 * @return 对象自身 
	 */
	public SecurityRouterStaff reset() {
		this.isHit = true;
		return this;
	}
	
	
	// ----------------- path匹配 
	
	/**
	 * 路由匹配 
	 * @param patterns 路由匹配符数组  
	 * @return 对象自身 
	 */
	public SecurityRouterStaff match(String... patterns) {
		if(isHit)  {
			isHit = SecurityRouter.isMatchCurrURI(patterns);
		}
		return this;
	}

	/**
	 * 路由匹配排除 
	 * @param patterns 路由匹配符排除数组  
	 * @return 对象自身 
	 */
	public SecurityRouterStaff notMatch(String... patterns) {
		if(isHit)  {
			isHit = !SecurityRouter.isMatchCurrURI(patterns);
		}
		return this;
	}

	/**
	 * 路由匹配 
	 * @param patterns 路由匹配符集合 
	 * @return 对象自身 
	 */
	public SecurityRouterStaff match(List<String> patterns) {
		if(isHit)  {
			isHit = SecurityRouter.isMatchCurrURI(patterns);
		}
		return this;
	}

	/**
	 * 路由匹配排除 
	 * @param patterns 路由匹配符排除集合 
	 * @return 对象自身 
	 */
	public SecurityRouterStaff notMatch(List<String> patterns) {
		if(isHit)  {
			isHit = !SecurityRouter.isMatchCurrURI(patterns);
		}
		return this;
	}

	// ----------------- Method匹配 

	/**
	 * Http请求方法匹配 (Enum) 
	 * @param methods Http请求方法断言数组  
	 * @return 对象自身 
	 */
	public SecurityRouterStaff match(SecurityHttpMethod... methods) {
		if(isHit)  {
			isHit = SecurityRouter.isMatchCurrMethod(methods);
		}
		return this;
	}

	/**
	 * Http请求方法匹配排除 (Enum) 
	 * @param methods Http请求方法断言排除数组  
	 * @return 对象自身 
	 */
	public SecurityRouterStaff notMatch(SecurityHttpMethod... methods) {
		if(isHit)  {
			isHit = !SecurityRouter.isMatchCurrMethod(methods);
		}
		return this;
	}

	/**
	 * Http请求方法匹配 (String) 
	 * @param methods Http请求方法断言数组  
	 * @return 对象自身 
	 */
	public SecurityRouterStaff matchMethod(String... methods) {
		if(isHit)  {
			SecurityHttpMethod[] arr = SecurityHttpMethod.toEnumArray(methods);
			isHit = SecurityRouter.isMatchCurrMethod(arr);
		}
		return this;
	}

	/**
	 * Http请求方法匹配排除 (String) 
	 * @param methods Http请求方法断言排除数组  
	 * @return 对象自身 
	 */
	public SecurityRouterStaff notMatchMethod(String... methods) {
		if(isHit)  {
			SecurityHttpMethod[] arr = SecurityHttpMethod.toEnumArray(methods);
			isHit = !SecurityRouter.isMatchCurrMethod(arr);
		}
		return this;
	}


	// ----------------- 条件匹配 

	/**
	 * 根据 boolean 值进行匹配 
	 * @param flag boolean值 
	 * @return 对象自身 
	 */
	public SecurityRouterStaff match(boolean flag) {
		if(isHit)  {
			isHit = flag;
		}
		return this;
	}

	/**
	 * 根据 boolean 值进行匹配排除 
	 * @param flag boolean值 
	 * @return 对象自身 
	 */
	public SecurityRouterStaff notMatch(boolean flag) {
		if(isHit)  {
			isHit = !flag;
		}
		return this;
	}
	
	/**
	 * 根据自定义方法进行匹配 (lazy)  
	 * @param fun 自定义方法
	 * @return 对象自身 
	 */
	public SecurityRouterStaff match(ParamRetFunction<Object, Boolean> fun) {
		if(isHit)  {
			isHit = fun.run(this);
		}
		return this;
	}

	/**
	 * 根据自定义方法进行匹配排除 (lazy) 
	 * @param fun 自定义排除方法
	 * @return 对象自身 
	 */
	public SecurityRouterStaff notMatch(ParamRetFunction<Object, Boolean> fun) {
		if(isHit)  {
			isHit = !fun.run(this);
		}
		return this;
	}
	
	
	// ----------------- 函数校验执行 

	/**
	 * 执行校验函数 (无参) 
	 * @param fun 要执行的函数 
	 * @return 对象自身 
	 */
	public SecurityRouterStaff check(CoreFunction fun) {
		if(isHit)  {
			fun.run();
		}
		return this;
	}
	
	/**
	 * 执行校验函数 (带参) 
	 * @param fun 要执行的函数 
	 * @return 对象自身 
	 */
	public SecurityRouterStaff check(ParamFunction<SecurityRouterStaff> fun) {
		if(isHit)  {
			fun.run(this);
		}
		return this;
	}
	
	/**
	 * 自由匹配 （ 在free作用域里执行stop()不会跳出Auth函数，而是仅仅跳出free代码块 ）
	 * @param fun 要执行的函数 
	 * @return 对象自身 
	 */
	public SecurityRouterStaff free(ParamFunction<SecurityRouterStaff> fun) {
		if(isHit)  {
			try {
				fun.run(this);
			} catch (StopMatchException e) {
				// 跳出 free自由匹配代码块 
			}
		}
		return this;
	}

	
	// ----------------- 直接指定check函数 
	
	/**
	 * 路由匹配，如果匹配成功则执行认证函数 
	 * @param pattern 路由匹配符
	 * @param fun 要执行的校验方法 
	 * @return /
	 */
	public SecurityRouterStaff match(String pattern, CoreFunction fun) {
		return this.match(pattern).check(fun);
	}

	/**
	 * 路由匹配，如果匹配成功则执行认证函数 
	 * @param pattern 路由匹配符
	 * @param fun 要执行的校验方法 
	 * @return /
	 */
	public SecurityRouterStaff match(String pattern, ParamFunction<SecurityRouterStaff> fun) {
		return this.match(pattern).check(fun);
	}

	/**
	 * 路由匹配 (并指定排除匹配符)，如果匹配成功则执行认证函数 
	 * @param pattern 路由匹配符 
	 * @param excludePattern 要排除的路由匹配符 
	 * @param fun 要执行的方法 
	 * @return /
	 */
	public SecurityRouterStaff match(String pattern, String excludePattern, CoreFunction fun) {
		return this.match(pattern).notMatch(excludePattern).check(fun);
	}

	/**
	 * 路由匹配 (并指定排除匹配符)，如果匹配成功则执行认证函数 
	 * @param pattern 路由匹配符 
	 * @param excludePattern 要排除的路由匹配符 
	 * @param fun 要执行的方法 
	 * @return /
	 */
	public SecurityRouterStaff match(String pattern, String excludePattern, ParamFunction<SecurityRouterStaff> fun) {
		return this.match(pattern).notMatch(excludePattern).check(fun);
	}
	
	
	// ----------------- 提前退出 

	/**
	 * 停止匹配，跳出函数 (在多个匹配链中一次性跳出Auth函数) 
	 * @return 对象自身 
	 */
	public SecurityRouterStaff stop() {
		if(isHit) {
			throw new StopMatchException();
		}
		return this;
	}

	/**
	 * 停止匹配，结束执行，向前端返回结果 
	 * @return 对象自身 
	 */
	public SecurityRouterStaff back() {
		if(isHit) {
			throw new BackResultException("");
		}
		return this;
	}
	
	/**
	 * 停止匹配，结束执行，向前端返回结果 
	 * @return 对象自身 
	 * @param result 要输出的结果 
	 */
	public SecurityRouterStaff back(Object result) {
		if(isHit) {
			throw new BackResultException(result);
		}
		return this;
	}

	
}
