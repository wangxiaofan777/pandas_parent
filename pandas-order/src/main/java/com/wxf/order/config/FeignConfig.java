package com.wxf.order.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
}
