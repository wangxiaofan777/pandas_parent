package com.wxf.user.clients;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author Wxf
 * @since 2024-03-18 14:17:38
 **/
@FeignClient(value = "order-service", path = "/orders", fallback = OrderFeignClientFallBack.class)
public interface OrderFeignClient {
}
