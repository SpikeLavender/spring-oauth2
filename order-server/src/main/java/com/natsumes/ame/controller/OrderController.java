package com.natsumes.ame.controller;

import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.MessageDigest;
import java.util.Date;

@RestController
@RequestMapping("/order")
public class OrderController {

    @GetMapping("/no-oauth/test")
    public String test1() {
        return new Date() + ": order test1 success";
    }

    @GetMapping("/need-oauth/test")
    public String test2() {
        return new Date() + ": order test2 success";
    }
}
