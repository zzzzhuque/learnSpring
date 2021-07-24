package com.zt.annotation.testdemo;

import com.zt.annotation.config.SpringConfig;
import com.zt.annotation.service.UserService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestSpring5Demo {
    @Test
    public void testComponent() {
        ApplicationContext context = new ClassPathXmlApplicationContext("bean12.xml");
        UserService userService = context.getBean("userService", UserService.class);
        System.out.println(userService);
        userService.add();
    }

    @Test
    public void testAutowired() {
        ApplicationContext context = new ClassPathXmlApplicationContext("bean12.xml");
        UserService userService = context.getBean("userService", UserService.class);
        System.out.println(userService);
        userService.add();
    }

    @Test
    public void testSpringConfig() {
        // 加载配置类
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);

        UserService userService = context.getBean("userService", UserService.class);
        System.out.println(userService);
        userService.add();
    }
}
