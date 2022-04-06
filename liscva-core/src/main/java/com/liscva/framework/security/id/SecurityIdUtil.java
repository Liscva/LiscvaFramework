package com.liscva.framework.security.id;

/**
 * Id 身份凭证模块-工具类
 *
 */
public class SecurityIdUtil {

	/**
	 * 在 Request 上储存 Id-Token 时建议使用的key 
	 */
	public static final String ID_TOKEN = SecurityIdTemplate.ID_TOKEN;

	/**
	 * 底层 SecurityIdTemplate 对象
	 */
	public static SecurityIdTemplate securityIdTemplate = new SecurityIdTemplate();
	
	// -------------------- 获取 & 校验 
	
	/**
	 * 获取当前Id-Token, 如果不存在，则立即创建并返回 
	 * @return 当前token
	 */
	public static String getToken() {
		return securityIdTemplate.getToken();
	}

	/**
	 * 判断一个Id-Token是否有效 
	 * @param token 要验证的token
	 * @return 这个token是否有效 
	 */
	public static boolean isValid(String token) {
		return securityIdTemplate.isValid(token);
	}

	/**
	 * 校验一个Id-Token是否有效 (如果无效则抛出异常) 
	 * @param token 要验证的token
	 */
	public static void checkToken(String token) {
		securityIdTemplate.checkToken(token);
	}

	/**
	 * 校验当前Request提供的Id-Token是否有效 (如果无效则抛出异常) 
	 */
	public static void checkCurrentRequestToken() {
		securityIdTemplate.checkCurrentRequestToken();
	}
	
	/**
	 * 刷新一次Id-Token (注意集群环境中不要多个服务重复调用)
	 * @return 新Token 
	 */
	public static String refreshToken() {
		return securityIdTemplate.refreshToken();
	}

	
	// -------------------- 获取Token 
	
	/**
	 * 获取Id-Token，不做任何处理 
	 * @return token 
	 */
	public static String getTokenNh() {
		return securityIdTemplate.getTokenNh();
	}
	
	/**
	 * 获取Past-Id-Token，不做任何处理 
	 * @return token 
	 */
	public static String getPastTokenNh() {
		return securityIdTemplate.getPastTokenNh();
	}

}
