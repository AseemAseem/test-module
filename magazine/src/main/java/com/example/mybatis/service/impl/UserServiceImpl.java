package com.example.mybatis.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.example.mybatis.entity.UserEntity;
import com.example.mybatis.mapper.UserMapper;
import com.example.mybatis.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2019-06-06
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements IUserService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
}
