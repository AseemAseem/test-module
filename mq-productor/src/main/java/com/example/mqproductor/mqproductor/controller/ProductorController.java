package com.example.mqproductor.mqproductor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ProductorController {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @GetMapping(value = "/send")
    @ResponseBody
    public String send() {
        Map map = new HashMap();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String now = format.format(new Date());
        map.put("time", now);
        map.put("name", "小明");
        jmsMessagingTemplate.convertAndSend("my_map", map);
        return "发送成功！"+now;
    }
}
