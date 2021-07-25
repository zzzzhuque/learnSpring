package com.aop.test;

import com.aop.annotation.User;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class testAop {
    @Test
    public void testAnnotation() {
        // 加载配置文件
        ApplicationContext context = new ClassPathXmlApplicationContext("bean13.xml");

        User user = context.getBean("user", User.class);
        user.add();
    }
}
