package com.example.rpc.consumer.service;

import com.example.rpc.consumer.service.impl.OrderServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceImplTest {

    @Autowired
    OrderServiceImpl orderService;

    @Test
    public void getUserNameTest(){
        String userName = orderService.getUserName();
        System.out.println(userName);
    }
}
