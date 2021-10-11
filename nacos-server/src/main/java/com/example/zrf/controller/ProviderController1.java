package com.example.zrf.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;

@RefreshScope
@RestController
public class ProviderController1 {


    @Value("${DevConfig}")
    private String devConfig;

    @GetMapping("invoke")
    public String invoke() {
        return LocalTime.now() + " invoke,devConfig:" + devConfig;
    }


}
