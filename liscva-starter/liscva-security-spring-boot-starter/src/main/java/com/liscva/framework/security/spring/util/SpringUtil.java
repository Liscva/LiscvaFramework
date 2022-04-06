package com.liscva.framework.security.spring.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.liscva.framework.core.exception.CoreException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


/**
 * Spring相关操作
 *
 */
public class SpringUtil {

    /**
     * 获取当前会话的 request
     * @return request
     */
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(servletRequestAttributes == null) {
            throw new CoreException("非Web上下文无法获取Request");
        }
        return servletRequestAttributes.getRequest();
    }

    /**
     * 获取当前会话的 response
     * @return response
     */
    public static HttpServletResponse getResponse() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(servletRequestAttributes == null) {
            throw new CoreException("非Web上下文无法获取Response");
        }
        return servletRequestAttributes.getResponse();
    }

    /**
     * 判断当前是否处于 Web 上下文中
     * @return request
     */
    public static boolean isWeb() {
        return RequestContextHolder.getRequestAttributes() != null;
    }

}
