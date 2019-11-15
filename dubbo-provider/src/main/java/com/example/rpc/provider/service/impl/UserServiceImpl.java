package com.example.rpc.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.example.rpc.commoninterface.service.IUserService;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class UserServiceImpl implements IUserService {
    @Override
    public String getUserName() {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String format = sf.format(new Date());
        return "from dubbo-provider: LuFei "+format;
    }
}
