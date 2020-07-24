package com.natsumes.ame.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class Controller {
    @GetMapping("/auto/test")
    public String test1() {
        return "auto test success: " + new Date();
    }

    @GetMapping("/demo/test")
    public String test2() {
        return "demo test success: " + new Date();
    }

    @GetMapping("test3")
    public String test3() {
        return "test3 success: " + new Date();
    }
}
