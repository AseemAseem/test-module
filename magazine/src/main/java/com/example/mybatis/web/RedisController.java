package com.example.mybatis.web;

import com.alibaba.fastjson.JSONObject;
import com.example.mybatis.common.base.BaseMsg;
import com.example.mybatis.dto.RedisDTO;
import com.example.mybatis.dto.RedisStudentDto;
import com.example.mybatis.entity.UserEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@Api(tags = "Redis操作")
@RestController
public class RedisController {

    @Autowired
    StringRedisTemplate redisTemplate;
//    @Autowired
//    RedisTemplate<String, String> redisTemplate;
//    @Autowired
//    RedisTemplate redisTemplate;

    private static final String opsPostfix = "-ops";

    @ApiOperation(value = "增")
    @RequestMapping(value = "/redisDto", method = RequestMethod.POST)
    public BaseMsg setRedisDto(@RequestBody RedisDTO dto) {
        BoundValueOperations valueOps = redisTemplate.boundValueOps(dto.getKey());
        valueOps.set(dto.getString());
        redisTemplate.opsForValue().set(dto.getKey() + opsPostfix, dto.getString());
        return BaseMsg.success();
    }

    @ApiOperation(value = "删")
    @RequestMapping(value = "/redisDto/{key}", method = RequestMethod.DELETE)
    public BaseMsg deleteRedisDto(String key) {
        Boolean aBoolean = redisTemplate.hasKey(key);
        if (!aBoolean) {
            return BaseMsg.errorMsg("不存在key");
        }
        Boolean delete = redisTemplate.delete(key);
        redisTemplate.delete(key + opsPostfix);
        if (delete) {
            return BaseMsg.success();
        } else {
            return BaseMsg.errorMsg("删除失败");
        }
    }

    @ApiOperation(value = "改")
    @RequestMapping(value = "/redisDto/{key}", method = RequestMethod.PUT)
    public BaseMsg updateRedisDtoByKey(String key, @RequestBody RedisDTO dto) {
        Boolean aBoolean = redisTemplate.hasKey(key);
        if (!aBoolean) {
            return BaseMsg.errorMsg("不存在key");
        }
        BoundValueOperations<String, String> valueops = redisTemplate.boundValueOps(key);
        redisTemplate.opsForValue().set(key + opsPostfix, dto.getString());
        valueops.set(dto.getString());
        return BaseMsg.success();
    }

    @ApiOperation(value = "查")
    @RequestMapping(value = "/redisDtos/{key}", method = RequestMethod.GET)
    public BaseMsg getRedisDtoByKey(String key) {
        Boolean aBoolean = redisTemplate.hasKey(key);
        if (!aBoolean) {
            return BaseMsg.errorMsg("不存在key");
        }
        BoundValueOperations<String, String> valueops = redisTemplate.boundValueOps(key);
        String s = valueops.get();
        return BaseMsg.successData(s);
    }

    @ApiOperation(value = "用户对象保存到redis")
    @RequestMapping(value = "/userToRedis   ", method = RequestMethod.POST)
    public BaseMsg userToRedis(String key, @RequestBody UserEntity user) {
        BoundValueOperations valueOps = redisTemplate.boundValueOps(key);
        valueOps.set(JSONObject.toJSONString(user));
        redisTemplate.opsForValue().set(key + opsPostfix, JSONObject.toJSONString(user));
        return BaseMsg.success();
    }

    @ApiOperation(value = "redis取出用户对象")
    @RequestMapping(value = "/getUserFromRedis/{key}", method = RequestMethod.GET)
    public BaseMsg getUserFromRedis(String key) {
        Boolean aBoolean = redisTemplate.hasKey(key);
        if (!aBoolean) {
            return BaseMsg.errorMsg("不存在key");
        }
        BoundValueOperations<String, String> valueops = redisTemplate.boundValueOps(key);
        String s = valueops.get();
        UserEntity userEntity = JSONObject.parseObject(s, UserEntity.class);
        return BaseMsg.successData(userEntity);
    }

    @ApiOperation(value = "存sortSet对象")
    @RequestMapping(value = "/getUserFromRedis/{key}", method = RequestMethod.POST)
    public BaseMsg setSortSet(){
        String key = "rank";
        ZSetOperations<String, String> zset = redisTemplate.opsForZSet();
        redisTemplate.delete(key);
        zset.add(key,"ming",66);
        zset.add(key,"li",77);
        zset.add(key,"zhao",88);
        zset.add(key,"qian",99);
        System.out.println("ming的排名是："+ zset.rank(key, "ming"));
        System.out.println("由小到大排序："+zset.range(key,0,-1));
        System.out.println("由大到小排序："+zset.reverseRange(key,0,-1));
        return BaseMsg.successMsg("请看控制台输出！");
    }
}
