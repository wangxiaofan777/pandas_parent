package com.wxf.order.controller;

import com.wxf.order.clients.UserFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author Wxf
 * @since 2024-03-18 11:32:56
 **/
@RestController
@RequestMapping
public class IndexController {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private UserFeignClient userFeignClient;

    @GetMapping("/users")
    public Integer getUser(){
        return restTemplate.getForObject("http://user-service/users/", Integer.class);
    }


    @GetMapping("/users/fallback")
    public Integer getUserFallBack(){
        return userFeignClient.getPort();
    }

}
