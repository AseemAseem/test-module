package com.example.rpc.consumer.service.impl;

import com.example.rpc.commoninterface.service.IUserService;
import com.example.rpc.consumer.service.IOrderService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements IOrderService {

    @Reference
    IUserService userService;

    @Override
    public String getUserName() {
//        try {
            return userService.getUserName();
//        } catch (Exception e) {
//            throw new DataCheckException("[自定义异常]No Service Found!");
//        }
    }
}
