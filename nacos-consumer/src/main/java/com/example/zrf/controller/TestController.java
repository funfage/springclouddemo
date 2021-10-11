package com.example.zrf.controller;

import com.alibaba.fastjson.JSON;
import com.example.zrf.fegin.ProviderClient;
import com.example.zrf.utils.HttpClientUtil;
import org.apache.http.client.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@RestController
public class TestController {

    private RestTemplate restTemplate;

    public TestController() {
    }

    @Autowired
    public TestController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Autowired
    ProviderClient providerClient;

    @GetMapping("/echo/{str}")
    public String echo(@PathVariable String str) {
        return restTemplate.getForObject("http://nacos-provider/echo/" + str, String.class);
    }

    @GetMapping("/hi-feign")
    public String hiFeign() {
        return providerClient.hi("feign");
    }

    @GetMapping("/test")
    public String test() {
        return restTemplate.getForObject("https://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel=15521417345", String.class);
    }

    @PostMapping("test1")
    public String test1(HttpServletRequest request) {
        StringBuilder responseStrBuilder = null;
        try {
            BufferedReader streamReader = new BufferedReader( new InputStreamReader(request.getInputStream(), StandardCharsets.UTF_8));
            responseStrBuilder = new StringBuilder();
            String inputStr;
            while ((inputStr = streamReader.readLine()) != null) {
                responseStrBuilder.append(inputStr);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //发送到服务端
        String jsonString = responseStrBuilder == null ? "" : responseStrBuilder.toString();
        String url = "http://localhost:8080/";
        return  HttpClientUtil.doPostJson(url, jsonString);
    }

//    @PostMapping("test2")
    public String test2(Map<String, Object> sys, Map<String, Object> commReq, Map<String, Object> input) {
        Map<String, Object> jsonMap = new HashMap<>();
        if (sys.size() > 0) {
            jsonMap.put("sys", sys);
        }
        if (input.size() > 0) {
            jsonMap.put("input", input);
        }
        if (commReq.size() > 0) {
            jsonMap.put("comm_req", commReq);
        }
        //发送到服务端
        String jsonString = JSON.toJSONString(jsonMap);
        System.out.println(jsonString);
        String url = "http://localhost:8080/";
        return  HttpClientUtil.doPostJson(url, jsonString);
    }

    public static void main(String[] args) {
        TestController testController = new TestController();
        Map<String, Object> sys = new HashMap<>();
        sys.put("username", "2134");
        sys.put("password", "2134");
        Map<String, Object> commReq = new HashMap<>();
        commReq.put("aaaaaa", "23232424");
        commReq.put("bbbbbb", "23234343");
        Map<String, Object> input = new HashMap<>();
        input.put("ccccc", "4535252");
        input.put("ddddd", "5452434");
        testController.test2(sys, commReq, input);
    }
}
