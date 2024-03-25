package com.wxf.order.config;

import feign.Logger;
import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Objects;

/**
 * @author Wxf
 * @since 2024-03-19 09:00:22
 **/
@Configuration
public class FeignConfig {

    /*
        NONE 不输出日志
        BASIC 只有请求方法、URL、响应状态代码、执行时间
        HEADERS基本信息以及请求和响应头
        FULL 请求和响应 的heads、body、metadata，建议使用这个级别
     */
    @Bean
    public Logger.Level feignLevel() {
        return Logger.Level.FULL;
    }


    // feign转发请求头
//    @Bean
    public RequestInterceptor requestInterceptor() {
        return template -> {
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

            if (Objects.nonNull(requestAttributes)) {
                HttpServletRequest request = requestAttributes.getRequest();

                Enumeration<String> headerNames = request.getHeaderNames();

                while (headerNames.hasMoreElements()) {
                    String headerName = headerNames.nextElement();
                    String headerValue = request.getHeader(headerName);

                    template.header(headerName, headerValue);
                }
            }


        };
    }
}
