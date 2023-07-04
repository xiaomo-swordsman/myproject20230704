package com.xiaomo.aop;

import org.springframework.stereotype.Component;

public class Advice {

        public void before(){
            System.out.println("Advice  前置增强...");
        }

        public void afterReturning(){
            System.out.println("Advice  后置增强...");
        }

        public void afterThrowing(){
            System.out.println("Advice  异常抛出增强...");
        }

        public void after(){
            System.out.println("Advice  最终增强...");
        }

        public void around(){
            System.out.println("Advice  环绕增强...");
        }
}
