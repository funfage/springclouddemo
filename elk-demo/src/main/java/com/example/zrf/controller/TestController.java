package com.example.zrf.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

@RestController("/test")
public class TestController {

    private final Logger log = LoggerFactory.getLogger(TestController.class);

    public Map index() throws UnknownHostException {
        Map<String, Object> map = new HashMap<>();
        InetAddress localHost = InetAddress.getLocalHost();
        String hostName = localHost.getHostName();
        String hostAddress = localHost.getHostAddress();
        map.put("主机名", hostName);
        map.put("主机地址", hostAddress);
        log.info("测试日志");
        return map;
    }


    @GetMapping("/error1")
    public String logError1() {
        log.error("error...");
        log.error("交易失败,失败原因:{}", "网络不稳定");
        return "error";
    }

    @GetMapping("/warn1")
    public String logWarn() {
        log.warn("warning....");
        log.warn("文件发送超时");
        return "warn";
    }

}
