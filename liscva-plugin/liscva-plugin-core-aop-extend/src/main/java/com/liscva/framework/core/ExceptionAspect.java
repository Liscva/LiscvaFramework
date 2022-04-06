package com.liscva.framework.core;

import com.liscva.framework.core.exception.CoreException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import com.liscva.framework.core.exception.Exception;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

@Aspect
@Configuration
public class ExceptionAspect {


    //定义一个切入点
    @Pointcut("@annotation(com.liscva.framework.core.exception.Exception)")
    public void annotationPointCut() {

    }

    @Around("annotationPointCut()")
    public Object doBefore(ProceedingJoinPoint joinPoint) {
        try {
            Object obj = joinPoint.proceed();
            return obj;
        } catch (Throwable e) {
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            Method method = methodSignature.getMethod();
            Object[] args = joinPoint.getArgs();

            Exception annotation = method.getAnnotation(Exception.class);
            //2.最关键的一步:通过这获取到方法的所有参数名称的字符串数组
            String[] parameterNames = methodSignature.getParameterNames();
            // 拦截的方法参数
            Parameter[] parameters = method.getParameters();
            String msg = annotation.msg();
            for (int i = 0, len = parameters.length; i < len; i++) {
                Object param = args[i];
                if(!ObjectUtils.isEmpty(param)){
                    msg = msg.replaceAll("\\{"+parameterNames[i]+"\\}",param.toString());
                }
            }
            throw new CoreException(annotation.code(),msg);
        }
    }
}