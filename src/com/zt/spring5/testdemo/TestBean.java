package com.zt.spring5.testdemo;

import com.zt.spring5.bean.Employee;
import com.zt.spring5.service.UserService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestBean {
    @Test
    public void TestBeanOutter() {
        // 加载spring配置文件
        ApplicationContext context = new ClassPathXmlApplicationContext("bean4.xml");

        // 获取配置创建的对象
        UserService userService = context.getBean("userService", UserService.class);
        userService.add();
    }

    @Test
    public void TestBeanInner() {
        // 加载Spring配置文件
        ApplicationContext context = new ClassPathXmlApplicationContext("bean5.xml");

        // 获取配置创建的对象
        Employee employee = context.getBean("employee", Employee.class);
        employee.printEmployeeInfo();
    }

    @Test
    public void TestBeanCascade() {
        // 加载Spring配置文件
        ApplicationContext context = new ClassPathXmlApplicationContext("bean6.xml");

        // 获取配置创建的对象
        Employee employee = context.getBean("employee", Employee.class);
        employee.printEmployeeInfo();
    }
}
