package com.wxf.order.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 用户feign客户端
 *
 * @author Wxf
 * @since 2024-03-18 14:15:15
 **/
@FeignClient(value = "user-service", path = "/users", fallback = UserFeignClientFallBack.class)
public interface UserFeignClient {

    @GetMapping
    public Integer getPort();
}
