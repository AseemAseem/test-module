package com.example.mq.consumer.mqconsumer.controller;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Controller
public class ComsumerController {

    @JmsListener(destination = "my_map")
    public void readMap(Map map) {
        System.out.println("接收到主题为 my_map 的消息，内容为:");
        System.out.println(map);
    }
}
