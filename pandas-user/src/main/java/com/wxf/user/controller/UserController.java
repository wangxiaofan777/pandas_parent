package com.wxf.user.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户Controller
 *
 * @author Wxf
 * @since 2024-03-18 10:47:05
 **/
@RestController
@RequestMapping
public class UserController {

    @Value("${server.port:8080}")
    private Integer port;


    @GetMapping
    public Integer getPort(){
        return port;
    }

}
