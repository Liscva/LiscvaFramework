package com.liscva.framework.security.action;


import com.liscva.framework.security.session.SecurityInfoSession;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.List;

/**
 * <p>此接口将会代理框架内部的一些关键性逻辑，方便开发者进行按需重写</p>
 *
 */
public interface SecurityInfoAction {

	/**
	 * 创建一个Token 
	 * @param loginId 账号id 
	 * @param loginType 账号类型 
	 * @return token
	 */
	public String createToken(Object loginId, String loginType); 
	
	/**
	 * 创建一个Session 
	 * @param sessionId Session的Id
	 * @return 创建后的Session 
	 */
	public SecurityInfoSession createSession(String sessionId);
	
	/**
	 * 判断：集合中是否包含指定元素（模糊匹配） 
	 * @param list 集合
	 * @param element 元素
	 * @return 是否包含
	 */
	public boolean hasElement(List<String> list, String element);

	/**
	 * 对一个Method对象进行注解检查（注解鉴权内部实现） 
	 * @param method Method对象
	 */
	public void checkMethodAnnotation(Method method);
	
	/**
	 * 从指定元素校验注解 
	 * @param target /
	 */
	public void validateAnnotation(AnnotatedElement target);
	
}
