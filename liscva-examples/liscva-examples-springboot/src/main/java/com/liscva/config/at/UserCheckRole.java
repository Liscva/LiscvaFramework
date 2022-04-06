package com.liscva.config.at;

import com.liscva.framework.security.annotation.CheckRole;
import com.liscva.framework.security.annotation.Mode;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 角色认证(User版)：必须具有指定角色标识才能进入该方法 
 * <p> 可标注在函数、类上（效果等同于标注在此类的所有方法上）
 *
 */
@CheckRole(type = StpUserUtil.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE})
public @interface UserCheckRole {

	/**
	 * 需要校验的角色标识
	 * @return 需要校验的角色标识
	 */
	@AliasFor(annotation = CheckRole.class)
	String [] value() default {};

	/**
	 * 验证模式：AND | OR，默认AND
	 * @return 验证模式
	 */
	@AliasFor(annotation = CheckRole.class)
	Mode mode() default Mode.AND;
	
}
