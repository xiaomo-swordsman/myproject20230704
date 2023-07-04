package com.xiaomo.aop;

import org.springframework.stereotype.Service;

public class TargetImpl implements Target{

    @Override
    public void save() {
        System.out.println("target save 执行了...");
    }

    @Override
    public void update() {
        System.out.println("target update 执行了...");
    }
}
