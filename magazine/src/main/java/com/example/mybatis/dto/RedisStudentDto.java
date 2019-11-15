package com.example.mybatis.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "学生成绩dto")
public class RedisStudentDto {

    String key;
    String name;
    int score;
}
