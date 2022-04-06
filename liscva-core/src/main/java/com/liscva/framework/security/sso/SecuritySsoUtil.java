package com.liscva.framework.security.sso;

import com.liscva.framework.security.lsp.LspUtil;

import com.liscva.framework.security.sso.SecuritySsoTemplate.CallSloUrlFunction;

/**
 * SSO 单点登录模块 工具类
 */
public class SecuritySsoUtil {

	/**
	 * 底层 SecuritySsoTemplate 对象
	 */
	public static SecuritySsoTemplate securitySsoTemplate = new SecuritySsoTemplate(LspUtil.lspLogic);
	

	// ---------------------- Ticket 操作 ---------------------- 
	
	/**
	 * 根据 账号id 创建一个 Ticket码 
	 * @param loginId 账号id 
	 * @return Ticket码 
	 */
	public static String createTicket(Object loginId) {
		return securitySsoTemplate.createTicket(loginId);
	}
	
	/**
	 * 删除 Ticket 
	 * @param ticket Ticket码
	 */
	public static void deleteTicket(String ticket) {
		securitySsoTemplate.deleteTicket(ticket);
	}
	
	/**
	 * 删除 Ticket索引 
	 * @param loginId 账号id 
	 */
	public static void deleteTicketIndex(Object loginId) {
		securitySsoTemplate.deleteTicketIndex(loginId);
	}

	/**
	 * 根据 Ticket码 获取账号id，如果Ticket码无效则返回null 
	 * @param ticket Ticket码
	 * @return 账号id 
	 */
	public static Object getLoginId(String ticket) {
		return securitySsoTemplate.getLoginId(ticket);
	}

	/**
	 * 根据 Ticket码 获取账号id，并转换为指定类型 
	 * @param <T> 要转换的类型 
	 * @param ticket Ticket码
	 * @param cs 要转换的类型 
	 * @return 账号id 
	 */
	public static <T> T getLoginId(String ticket, Class<T> cs) {
		return securitySsoTemplate.getLoginId(ticket, cs);
	}

	/**
	 * 校验ticket码，获取账号id，如果此ticket是有效的，则立即删除 
	 * @param ticket Ticket码
	 * @return 账号id 
	 */
	public static Object checkTicket(String ticket) {
		return securitySsoTemplate.checkTicket(ticket);
	}
	

	// ---------------------- 构建URL ---------------------- 

	/**
	 * 构建URL：Server端 单点登录地址
	 * @param clientLoginUrl Client端登录地址 
	 * @param back 回调路径 
	 * @return [SSO-Server端-认证地址 ]
	 */
	public static String buildServerAuthUrl(String clientLoginUrl, String back) {
		return securitySsoTemplate.buildServerAuthUrl(clientLoginUrl, back);
	}

	/**
	 * 构建URL：Server端向Client下放ticke的地址 
	 * @param loginId 账号id 
	 * @param redirect Client端提供的重定向地址 
	 * @return see note 
	 */
	public static String buildRedirectUrl(Object loginId, String redirect) {
		return securitySsoTemplate.buildRedirectUrl(loginId, redirect);
	}
	
	/**
	 * 校验重定向url合法性
	 * @param url 下放ticket的url地址 
	 */
	public static void checkRedirectUrl(String url) {
		securitySsoTemplate.checkRedirectUrl(url);
	}

	/**
	 * 获取：所有允许的授权回调地址，多个用逗号隔开 (不在此列表中的URL将禁止下放ticket) 
	 * @return see note 
	 */
	public static String getAllowUrl() {
		return securitySsoTemplate.getAllowUrl();
	}

	/**
	 * 构建URL：Server端 账号资料查询地址 
	 * @param loginId 账号id
	 * @return Server端 账号资料查询地址 
	 */
	public static String buildUserinfoUrl(Object loginId) {
		return securitySsoTemplate.buildUserinfoUrl(loginId);
	}
	
	// ------------------- SSO 模式三 ------------------- 
	
	/**
	 * 校验secretkey秘钥是否有效 
	 * @param secretkey 秘钥 
	 */
	public static void checkSecretkey(String secretkey) {
		securitySsoTemplate.checkSecretkey(secretkey);
	}

	/**
	 * 构建URL：校验ticket的URL 
	 * @param ticket ticket码
	 * @param ssoLogoutCallUrl 单点注销时的回调URL 
	 * @return 构建完毕的URL 
	 */
	public static String buildCheckTicketUrl(String ticket, String ssoLogoutCallUrl) {
		return securitySsoTemplate.buildCheckTicketUrl(ticket, ssoLogoutCallUrl);
	}

	/**
	 * 为指定账号id注册单点注销回调URL 
	 * @param loginId 账号id
	 * @param sloCallbackUrl 单点注销时的回调URL 
	 */
	public static void registerSloCallbackUrl(Object loginId, String sloCallbackUrl) {
		securitySsoTemplate.registerSloCallbackUrl(loginId, sloCallbackUrl);
	}

	/**
	 * 循环调用Client端单点注销回调 
	 * @param loginId 账号id
	 * @param fun 调用方法 
	 */
	public static void forEachSloUrl(Object loginId, CallSloUrlFunction fun) {
		securitySsoTemplate.forEachSloUrl(loginId, fun);
	}

	/**
	 * 构建URL：单点注销URL 
	 * @param loginId 要注销的账号id
	 * @return 单点注销URL 
	 */
	public static String buildSloUrl(Object loginId) {
		return securitySsoTemplate.buildSloUrl(loginId);
	}

	/**
	 * 指定账号单点注销 
	 * @param secretkey 校验秘钥
	 * @param loginId 指定账号 
	 * @param fun 调用方法 
	 */
	public static void singleLogout(String secretkey, Object loginId, CallSloUrlFunction fun) {
		securitySsoTemplate.singleLogout(secretkey, loginId, fun);
	}

	/**
	 * 获取：账号资料 
	 * @param loginId 账号id
	 * @return 账号资料 
	 */
	public static Object getUserinfo(Object loginId) {
		return securitySsoTemplate.getUserinfo(loginId);
	}
	
}
