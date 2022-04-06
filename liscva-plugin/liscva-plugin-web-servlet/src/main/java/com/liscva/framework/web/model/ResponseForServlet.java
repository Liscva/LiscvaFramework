package com.liscva.framework.web.model;

import com.liscva.framework.core.exception.CoreException;
import com.liscva.framework.core.context.model.LiscvaResponse;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Response for Servlet
 *
 */
public class ResponseForServlet implements LiscvaResponse {

	/**
	 * 底层Request对象
	 */
	protected HttpServletResponse response;
	
	/**
	 * 实例化
	 * @param response response对象 
	 */
	public ResponseForServlet(HttpServletResponse response) {
		this.response = response;
	}
	
	/**
	 * 获取底层源对象 
	 */
	@Override
	public Object getSource() {
		return response;
	}

	/**
	 * 设置响应状态码 
	 */
	@Override
	public LiscvaResponse setStatus(int sc) {
		response.setStatus(sc);
		return this;
	}
	
	/**
	 * 在响应头里写入一个值 
	 */
	@Override
	public LiscvaResponse setHeader(String name, String value) {
		response.setHeader(name, value);
		return this;
	}

	/**
	 * 在响应头里添加一个值 
	 * @param name 名字
	 * @param value 值 
	 * @return 对象自身 
	 */
	public LiscvaResponse addHeader(String name, String value) {
		response.addHeader(name, value);
		return this;
	}
	
	/**
	 * 重定向 
	 */
	@Override
	public Object redirect(String url) {
		try {
			response.sendRedirect(url);
		} catch (IOException e) {
			throw new CoreException(e);
		}
		return null;
	}

	
}
