package com.hkc.blog.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @ClassName IntelliJ IDEA
 * @Author 胡开成
 * @Date 2020/2/28 22:08
 */
@Aspect
@Component
public class LogAspect {
    private Logger logger= LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* com.hkc.blog.web.*.*(..))")
    public void log() {}

    @Before("log()")
    public void doBefore(JoinPoint joinPoint){
            ServletRequestAttributes attributes= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request=attributes.getRequest();
            String url=request.getRequestURL().toString();
            String ip=request.getRemoteAddr()+request.getRemoteHost();
//          得到类和方法名
            String classMethod=joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName();
            Object[] args=joinPoint.getArgs();
            RequestsLog log=new RequestsLog(url,ip,classMethod,args);
            logger.info("Request : {} ",log);
    }
    @After("log()")
    public void doAfter(){
        logger.info("-----------after-----------");
    }

    @AfterReturning(returning = "result",pointcut = "log()")
    public void doAfterReturn(Object result){
        logger.info("Result : {}",result);
    }

    private class RequestsLog{
        private String url;
        private String ip;
        private String className;
        private Object[] args;

        public RequestsLog(String url, String ip, String className, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.className = className;
            this.args = args;
        }

        @Override
        public String toString() {
            return "RequestsLog{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", className='" + className + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }
}
