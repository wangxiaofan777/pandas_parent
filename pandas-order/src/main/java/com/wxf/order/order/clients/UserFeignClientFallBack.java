package com.wxf.order.order.clients;

/**
 * @author Wxf
 * @since 2024-03-18 17:58:15
 **/
public class UserFeignClientFallBack implements UserFeignClient{

    @Override
    public Integer getPort() {
        return -1;
    }
}
