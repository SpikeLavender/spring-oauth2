package com.natsumes.ame.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/user")
public class UserController {
    @GetMapping("/no-oauth/test")
    public String test1() {
        return new Date() + ": user test1 success";
    }

    @GetMapping("/need-oauth/test")
    public String test2() {
        return new Date() + ": user test2 success";
    }
}
