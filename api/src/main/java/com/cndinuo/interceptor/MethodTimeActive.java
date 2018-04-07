package com.cndinuo.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 监控方法执行时间
 * @author dgb
 */
public class MethodTimeActive implements MethodInterceptor {

    private static final Logger log = LoggerFactory.getLogger("service");
    /**
     * 拦截要执行的方法
     */
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        // 创建一个计时器
        StopWatch watch = new StopWatch();
        // 计时器开始
        watch.start(); 
        // 执行方法
        Object object = invocation.proceed();
        // 计时器停止
        watch.stop();
        // 类名称
        String className = invocation.getMethod().getDeclaringClass().getSimpleName();
        // 方法名称
        String methodName = invocation.getMethod().getName();
        // 获取计时器计时时间
        Long time = watch.getTime();
        log.info("方法【"+className+"."+methodName+"】执行用时【"+time+"】ms");
        return object;
    }
    
}