package com.jxzy.AppMigration.NavigationApp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class indexController {
    @GetMapping("")
    public String test(){
        return "index"; /* 默认访问templates下面的.html页面 */
    }
    @GetMapping("/yhysxys")
    public String yhysxy(){
        return "yhysxy"; /* 默认访问templates下面的.html页面 */
    }
}
