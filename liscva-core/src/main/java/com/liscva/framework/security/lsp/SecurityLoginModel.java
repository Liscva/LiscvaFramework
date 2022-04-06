package com.liscva.framework.security.lsp;



import com.liscva.framework.CoreConsts;
import com.liscva.framework.security.SecurityManager;
import com.liscva.framework.security.config.SecurityConfig;
import com.liscva.framework.security.dao.SecurityInfoDao;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 调用 `LspUtil.login()` 时的 [配置参数 Model ]
 *
 */
public class SecurityLoginModel {

	/**
	 * 此次登录的客户端设备标识 
	 */
	public String device;
	
	/**
	 * 是否为持久Cookie（临时Cookie在浏览器关闭时会自动删除，持久Cookie在重新打开后依然存在）
	 */
	public Boolean isLastingCookie;

	/**
	 * 指定此次登录token的有效期, 单位:秒 （如未指定，自动取全局配置的timeout值）
	 */
	public Long timeout;

	/**
	 * 扩展信息（只在jwt模式下生效）
	 */
	public Map<String, Object> extraData;

	/**
	 * 预定Token（预定本次登录生成的Token值）
	 */
	public String token;
	
	
	/**
	 * @return 此次登录的客户端设备标识 
	 */
	public String getDevice() {
		return device;
	}

	/**
	 * @param device 此次登录的客户端设备标识 
	 * @return 对象自身
	 */
	public SecurityLoginModel setDevice(String device) {
		this.device = device;
		return this;
	}

	/**
	 * @return 参考 是否为持久Cookie（临时Cookie在浏览器关闭时会自动删除，持久Cookie在重新打开后依然存在）
	 */
	public Boolean getIsLastingCookie() {
		return isLastingCookie;
	}

	/**
	 * @param isLastingCookie 是否为持久Cookie（临时Cookie在浏览器关闭时会自动删除，持久Cookie在重新打开后依然存在）
	 * @return 对象自身
	 */
	public SecurityLoginModel setIsLastingCookie(Boolean isLastingCookie) {
		this.isLastingCookie = isLastingCookie;
		return this;
	}

	/**
	 * @return 指定此次登录token的有效期, 单位:秒 （如未指定，自动取全局配置的timeout值）
	 */
	public Long getTimeout() {
		return timeout;
	}

	/**
	 * @param timeout 指定此次登录token的有效期, 单位:秒 （如未指定，自动取全局配置的timeout值）
	 * @return 对象自身
	 */
	public SecurityLoginModel setTimeout(long timeout) {
		this.timeout = timeout;
		return this;
	}

	/**
	 * @return 扩展信息（只在jwt模式下生效）
	 */
	public Map<String, Object> getExtraData() {
		return extraData;
	}

	/**
	 * @param extraData 扩展信息（只在jwt模式下生效）
	 * @return 对象自身
	 */
	public SecurityLoginModel setExtraData(Map<String, Object> extraData) {
		this.extraData = extraData;
		return this;
	}

	/**
	 * @return 预定Token（预定本次登录生成的Token值）
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token 预定Token（预定本次登录生成的Token值）
	 * @return 对象自身
	 */
	public SecurityLoginModel setToken(String token) {
		this.token = token;
		return this;
	}

	/*
	 * toString 
	 */
	@Override
	public String toString() {
		return "SecurityLoginModel [device=" + device + ", isLastingCookie=" + isLastingCookie + ", timeout=" + timeout
				+ ", extraData=" + extraData + ", token=" + token + "]";
	}

	// ------ 附加方法 
	

	/**
	 * 写入扩展数据（只在jwt模式下生效） 
	 * @param key 键
	 * @param value 值 
	 * @return 对象自身 
	 */
	public SecurityLoginModel setExtra(String key, Object value) {
		if(this.extraData == null) {
			this.extraData = new LinkedHashMap<>();
		}
		this.extraData.put(key, value);
		return this;
	}

	/**
	 * 获取扩展数据（只在jwt模式下生效） 
	 * @param key 键
	 * @return 扩展数据的值 
	 */
	public Object getExtra(String key) {
		if(this.extraData == null) {
			return null;
		}
		return this.extraData.get(key);
	}

	/**
	 * @return Cookie时长
	 */
	public int getCookieTimeout() {
		if(isLastingCookie == false) {
			return -1;
		}
		if(timeout == SecurityInfoDao.NEVER_EXPIRE) {
			return Integer.MAX_VALUE;
		}
		return (int)(long)timeout;
	}

	/**
	 * @return 获取device参数，如果为null，则返回默认值
	 */
	public String getDeviceOrDefault() {
		if(device == null) {
			return CoreConsts.DEFAULT_LOGIN_DEVICE;
		}
		return device;
	}
	
	/**
	 * 构建对象，初始化默认值 
	 * @return 对象自身
	 */
	public SecurityLoginModel build() {
		return build(SecurityManager.getConfig());
	}
	
	/**
	 * 构建对象，初始化默认值 
	 * @param config 配置对象 
	 * @return 对象自身
	 */
	public SecurityLoginModel build(SecurityConfig config) {
		if(isLastingCookie == null) {
			isLastingCookie = true;
		}
		if(timeout == null) {
			timeout = config.getTimeout();
		}
		return this;
	}
	
	/**
	 * 静态方法获取一个 SecurityLoginModel 对象
	 * @return SecurityLoginModel 对象
	 */
	public static SecurityLoginModel create() {
		return new SecurityLoginModel();
	}

	
	/**
	 * 更换为 getDeviceOrDefault() 
	 * @return / 
	 */
	@Deprecated
	public String getDeviceOrDefalut() {
		return getDeviceOrDefault();
	}
}
