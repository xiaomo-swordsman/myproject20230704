package com.xiaomo.service;

import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    @Override
    public void save() {
        System.out.println("userservice save...");
    }
}
