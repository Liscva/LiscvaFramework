package com.liscva.framework.security.action;


import com.liscva.framework.CoreConsts;
import com.liscva.framework.core.util.FoxUtil;
import com.liscva.framework.security.SecurityManager;
import com.liscva.framework.security.annotation.*;
import com.liscva.framework.security.basic.SecurityBasicUtil;
import com.liscva.framework.security.session.SecurityInfoSession;
import com.liscva.framework.security.strategy.SecurityInfoStrategy;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.List;
import java.util.UUID;

/**
 * <p> 逻辑代理接口 [默认实现类]  </p>
 */
public class SecurityInfoActionDefaultImpl implements SecurityInfoAction {

	/**
	 * 创建一个Token 
	 */
	@Override
	public String createToken(Object loginId, String loginType) {
		// 根据配置的tokenStyle生成不同风格的token 
		String tokenStyle = SecurityManager.getConfig().getTokenStyle();
		// uuid 
		if(CoreConsts.TOKEN_STYLE_UUID.equals(tokenStyle)) {
			return UUID.randomUUID().toString();
		}
		// 简单uuid (不带下划线)
		if(CoreConsts.TOKEN_STYLE_SIMPLE_UUID.equals(tokenStyle)) {
			return UUID.randomUUID().toString().replaceAll("-", "");
		}
		// 32位随机字符串
		if(CoreConsts.TOKEN_STYLE_RANDOM_32.equals(tokenStyle)) {
			return FoxUtil.getRandomString(32);
		}
		// 64位随机字符串
		if(CoreConsts.TOKEN_STYLE_RANDOM_64.equals(tokenStyle)) {
			return FoxUtil.getRandomString(64);
		}
		// 128位随机字符串
		if(CoreConsts.TOKEN_STYLE_RANDOM_128.equals(tokenStyle)) {
			return FoxUtil.getRandomString(128);
		}
		// tik风格 (2_14_16)
		if(CoreConsts.TOKEN_STYLE_TIK.equals(tokenStyle)) {
			return FoxUtil.getRandomString(2) + "_" + FoxUtil.getRandomString(14) + "_" + FoxUtil.getRandomString(16) + "__";
		}
		// 默认，还是uuid 
		return UUID.randomUUID().toString();
	}

	/**
	 * 创建一个Session 
	 */
	@Override
	public SecurityInfoSession createSession(String sessionId) {
		return new SecurityInfoSession(sessionId);
	}

	/**
	 * 判断：集合中是否包含指定元素（模糊匹配） 
	 */
	@Override
	public boolean hasElement(List<String> list, String element) {
		
		// 空集合直接返回false
		if(list == null || list.size() == 0) {
			return false;
		}

		// 先尝试一下简单匹配，如果可以匹配成功则无需继续模糊匹配 
		if (list.contains(element)) {
			return true;
		}
		
		// 开始模糊匹配 
		for (String patt : list) {
			if(FoxUtil.vagueMatch(patt, element)) {
				return true;
			}
		}
		
		// 走出for循环说明没有一个元素可以匹配成功 
		return false;
	}

	/**
	 * 对一个Method对象进行注解检查（注解鉴权内部实现） 
	 */
	@Override
	public void checkMethodAnnotation(Method method) {

		// 先校验 Method 所属 Class 上的注解 
		validateAnnotation(method.getDeclaringClass());
		
		// 再校验 Method 上的注解  
		validateAnnotation(method);
	}

	/**
	 * 从指定元素校验注解 
	 * @param target see note 
	 */
	public void validateAnnotation(AnnotatedElement target) {
		
		// 校验 @CheckLogin 注解
		CheckLogin checkLogin = (CheckLogin) SecurityInfoStrategy.me.getAnnotation.apply(target, CheckLogin.class);
		if(checkLogin != null) {
			SecurityManager.getLspLogic(checkLogin.type()).checkByAnnotation(checkLogin);
		}
		
		// 校验 @CheckRole 注解
		CheckRole checkRole = (CheckRole) SecurityInfoStrategy.me.getAnnotation.apply(target, CheckRole.class);
		if(checkRole != null) {
			SecurityManager.getLspLogic(checkRole.type()).checkByAnnotation(checkRole);
		}
		
		// 校验 @CheckPermission 注解
		CheckPermission checkPermission = (CheckPermission) SecurityInfoStrategy.me.getAnnotation.apply(target, CheckPermission.class);
		if(checkPermission != null) {
			SecurityManager.getLspLogic(checkPermission.type()).checkByAnnotation(checkPermission);
		}

		// 校验 @CheckSafe 注解
		CheckSafe checkSafe = (CheckSafe) SecurityInfoStrategy.me.getAnnotation.apply(target, CheckSafe.class);
		if(checkSafe != null) {
			SecurityManager.getLspLogic(checkSafe.type()).checkByAnnotation(checkSafe);
		}
		
		// 校验 @CheckBasic 注解
		CheckBasic checkBasic = (CheckBasic) SecurityInfoStrategy.me.getAnnotation.apply(target, CheckBasic.class);
		if(checkBasic != null) {
			SecurityBasicUtil.check(checkBasic.realm(), checkBasic.account());
		}
		
	}
	
}
