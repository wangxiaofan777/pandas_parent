package com.wxf.eureka.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 首页Controller
 *
 * @author Wxf
 * @since 2024-03-21 09:25:31
 **/
@RestController
public class IndexController {

    @RequestMapping("/hello")
    public String home() {
        return "Hello World";
    }

}
