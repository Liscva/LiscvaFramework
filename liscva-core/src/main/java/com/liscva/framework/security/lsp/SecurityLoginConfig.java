package com.liscva.framework.security.lsp;

import java.util.Map;

/**
 * 
 * 快速构建 调用 `StpUtil.login()` 时的 [配置参数 Model ]
 *
 */
public class SecurityLoginConfig {

	/**
	 * @param device 此次登录的客户端设备标识 
	 * @return SecurityLoginModel配置对象
	 */
	public static SecurityLoginModel setDevice(String device) {
		return create().setDevice(device);
	}

	/**
	 * @param isLastingCookie 是否为持久Cookie（临时Cookie在浏览器关闭时会自动删除，持久Cookie在重新打开后依然存在）
	 * @return 对象自身
	 */
	public static SecurityLoginModel setIsLastingCookie(Boolean isLastingCookie) {
		return create().setIsLastingCookie(isLastingCookie);
	}

	/**
	 * @param timeout 指定此次登录token的有效期, 单位:秒 （如未指定，自动取全局配置的timeout值）
	 * @return 对象自身
	 */
	public static SecurityLoginModel setTimeout(Long timeout) {
		return create().setTimeout(timeout);
	}

	/**
	 * @param extraData 扩展信息（只在jwt模式下生效）
	 * @return 对象自身
	 */
	public static SecurityLoginModel setExtraData(Map<String, Object> extraData) {
		return create().setExtraData(extraData);
	}

	/**
	 * @param token 预定Token（预定本次登录生成的Token值）
	 * @return 对象自身
	 */
	public static SecurityLoginModel setToken(String token) {
		return create().setToken(token);
	}

	/**
	 * 写入扩展数据（只在jwt模式下生效） 
	 * @param key 键
	 * @param value 值 
	 * @return 对象自身 
	 */
	public static SecurityLoginModel setExtra(String key, Object value) {
		return create().setExtra(key, value);
	}

	/**
	 * 静态方法获取一个 SecurityLoginModel 对象
	 * @return SecurityLoginModel 对象
	 */
	public static SecurityLoginModel create() {
		return new SecurityLoginModel();
	}

}
