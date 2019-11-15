package com.example.rpc.consumer.entity;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String uuid;
    private String name;
    private String password;
    private String version;
}
