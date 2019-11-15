package com.example.rpc.springbootprovider.service.impl;

import com.example.rpc.commoninterface.service.IUserService;
import org.apache.dubbo.config.annotation.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class UserServiceImpl implements IUserService {
    @Override
    public String getUserName() {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String format = sf.format(new Date());
        return "from dubbo-springboot-provider: Black Beard "+format;
    }
}
