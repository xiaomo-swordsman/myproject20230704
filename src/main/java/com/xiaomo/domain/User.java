package com.xiaomo.domain;

import lombok.Data;

import java.util.Date;

@Data
public class User {

    private int id;

    private String username;

    private String password;

    private String name;

//    private long createTime;

    private Date createTime;

}
