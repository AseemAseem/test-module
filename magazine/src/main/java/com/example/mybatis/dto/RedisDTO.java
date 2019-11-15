package com.example.mybatis.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
@ApiModel(value = "redis数据dto")
public class RedisDTO {
    private int id;
    private String key;
    private String string;
    private List<String> list;
    private Set<String> set;
    private Set<String> sortSet;
    private Map<String, String> hash;
}
