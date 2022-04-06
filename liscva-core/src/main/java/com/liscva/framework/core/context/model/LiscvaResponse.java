package com.liscva.framework.core.context.model;

/**
 * Response 包装类
 *
 */
public interface LiscvaResponse {

	/**
	 * 获取底层源对象 
	 * @return see note 
	 */
	public Object getSource();
	
	/**
	 * 删除指定Cookie 
	 * @param name Cookie名称
	 */
	public default void deleteCookie(String name) {
		addCookie(name, null, null, null, 0);
	}

	/**
	 * 写入指定Cookie
	 * @param name     Cookie名称
	 * @param value    Cookie值
	 * @param path     Cookie路径
	 * @param domain   Cookie的作用域
	 * @param timeout  过期时间 （秒）
	 */
	public default void addCookie(String name, String value, String path, String domain, int timeout) {
		this.addCookie(new LiscvaCookie(name, value).setPath(path).setDomain(domain).setMaxAge(timeout));
	}
	
	/**
	 * 写入指定Cookie
	 * @param cookie Cookie-Model
	 */
	public default void addCookie(LiscvaCookie cookie) {
		this.addHeader(LiscvaCookie.HEADER_NAME, cookie.toHeaderValue());
	}
	
	/**
	 * 设置响应状态码
	 * @param sc 响应状态码
	 * @return 对象自身
	 */
	public LiscvaResponse setStatus(int sc);
	
	/**
	 * 在响应头里写入一个值 
	 * @param name 名字
	 * @param value 值 
	 * @return 对象自身 
	 */
	public LiscvaResponse setHeader(String name, String value);

	/**
	 * 在响应头里添加一个值 
	 * @param name 名字
	 * @param value 值 
	 * @return 对象自身 
	 */
	public LiscvaResponse addHeader(String name, String value);
	
	/**
	 * 在响应头写入 [Server] 服务器名称 
	 * @param value 服务器名称  
	 * @return 对象自身 
	 */
	public default LiscvaResponse setServer(String value) {
		return this.setHeader("Server", value);
	}

	/**
	 * 重定向 
	 * @param url 重定向地址 
	 * @return 任意值 
	 */
	public Object redirect(String url);
	
}
