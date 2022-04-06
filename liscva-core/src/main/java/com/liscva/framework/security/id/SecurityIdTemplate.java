package com.liscva.framework.security.id;


import com.liscva.framework.core.util.FoxUtil;
import com.liscva.framework.security.SecurityManager;
import com.liscva.framework.security.context.SecurityHolder;
import com.liscva.framework.security.exception.IdTokenInvalidException;

/**
 * Id 身份凭证模块
 * <p> 身份凭证的获取与校验，可用于微服务内部调用鉴权
 *
 */
public class SecurityIdTemplate {

	/**
	 * 在 Request 上储存 Id-Token 时建议使用的key 
	 */
	 public static final String ID_TOKEN = "SA_ID_TOKEN";
	
	// -------------------- 获取 & 校验 
	
	/**
	 * 获取当前Id-Token, 如果不存在，则立即创建并返回 
	 * @return 当前token
	 */
	public String getToken() {
		String currentToken = getTokenNh(); 
		if(FoxUtil.isEmpty(currentToken)) {
			// 注意这里的自刷新不能做到高并发可用 
			currentToken = refreshToken();
		}
		return currentToken;
	}

	/**
	 * 判断一个Id-Token是否有效 
	 * @param token 要验证的token
	 * @return 这个token是否有效 
	 */
	public boolean isValid(String token) {
		// 1、 如果传入的token未空，立即返回false 
		if(FoxUtil.isEmpty(token)) {
			return false;
		}
		
		// 2、 验证当前 Id-Token 及 Past-Id-Token 
		return token.equals(getToken()) || token.equals(getPastTokenNh());
	}

	/**
	 * 校验一个Id-Token是否有效 (如果无效则抛出异常) 
	 * @param token 要验证的token
	 */
	public void checkToken(String token) {
		if(isValid(token) == false) {
			token = (token == null ? "" : token);
			throw new IdTokenInvalidException("无效Id-Token：" + token);
		}
	}

	/**
	 * 校验当前Request提供的Id-Token是否有效 (如果无效则抛出异常) 
	 */
	public void checkCurrentRequestToken() {
		checkToken(SecurityHolder.getRequest().getHeader(ID_TOKEN));
	}
	
	/**
	 * 刷新一次Id-Token (注意集群环境中不要多个服务重复调用) 
	 * @return 新Token 
	 */
	public String refreshToken() {
		
		// 1. 先将当前 Id-Token 写入到 Past-Id-Token 中 
		String idToken = getTokenNh(); 
		if(FoxUtil.isEmpty(idToken) == false) {
			savePastToken(idToken, getTokenTimeout());
		}
		
		// 2. 再刷新当前Id-Token 
		String newIdToken = createToken();
		saveToken(newIdToken);
		
		// 3. 返回新的 Id-Token 
		return newIdToken;
	}

	
	// ------------------------------ 保存Token 
	
	/**
	 * 保存Id-Token
	 * @param token token 
	 */
	public void saveToken(String token) {
		if(FoxUtil.isEmpty(token)) {
			return;
		}
		SecurityManager.getSecurityInfoDao().set(splicingTokenSaveKey(), token, SecurityManager.getConfig().getIdTokenTimeout());
	}
	
	/**
	 * 保存Past-Id-Token
	 * @param token token
	 * @param timeout 有效期（单位：秒）
	 */
	public void savePastToken(String token, long timeout){
		if(FoxUtil.isEmpty(token)) {
			return;
		}
		SecurityManager.getSecurityInfoDao().set(splicingPastTokenSaveKey(), token, timeout);
	}
	
	
	// -------------------- 获取Token 
	
	/**
	 * 获取Id-Token，不做任何处理 
	 * @return token 
	 */
	public String getTokenNh() {
		return SecurityManager.getSecurityInfoDao().get(splicingTokenSaveKey());
	}
	
	/**
	 * 获取Past-Id-Token，不做任何处理 
	 * @return token 
	 */
	public String getPastTokenNh() {
		return SecurityManager.getSecurityInfoDao().get(splicingPastTokenSaveKey());
	}

	/**
	 * 获取Id-Token的剩余有效期 (单位：秒) 
	 * @return token 
	 */
	public long getTokenTimeout() {
		return SecurityManager.getSecurityInfoDao().getTimeout(splicingTokenSaveKey());
	}
	

	// -------------------- 创建Token 
	
	/**
	 * 创建一个Id-Token 
	 * @return Token 
	 */
	public String createToken() {
		return FoxUtil.getRandomString(64);
	}


	// -------------------- 拼接key 

	/** 
	 * 拼接key：Id-Token 的存储 key
	 * @return key
	 */
	public String splicingTokenSaveKey() {
		return SecurityManager.getConfig().getTokenName() + ":var:id-token";
	}

	/** 
	 * 拼接key：次级 Id-Token 的存储 key
	 * @return key
	 */
	public String splicingPastTokenSaveKey() {
		return SecurityManager.getConfig().getTokenName() + ":var:past-id-token";
	}

}
