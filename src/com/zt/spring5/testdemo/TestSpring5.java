package com.zt.spring5.testdemo;

import com.zt.spring5.Book;
import com.zt.spring5.Orders;
import com.zt.spring5.User;
import com.zt.spring5.autowire.Employee;
import com.zt.spring5.collectiontype.Stu;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestSpring5 {

    @Test
    public void testAdd() {
        // 加载spring配置文件
        ApplicationContext context = new ClassPathXmlApplicationContext("bean1.xml");

        // 获取配置创建的对象
        User user = context.getBean("user", User.class);

        System.out.println(user);
        user.add();
    }

    @Test
    public void testSetBook() {
        // 加载Spring配置文件
         ApplicationContext context = new ClassPathXmlApplicationContext("bean2.xml");

         // 获取配置创建的对象
        Book book = context.getBean("book", Book.class);

        System.out.println(book);
        book.printBookInfo();
    }

    @Test
    public void testXmlOrders() {
        // 加载Spring配置文件
        ApplicationContext context = new ClassPathXmlApplicationContext("bean3.xml");

        // 获取配置创建的对象
        Orders orders = context.getBean("orders", Orders.class);

        System.out.println(orders);
        orders.printOrders();
    }

    @Test
    public void testCollection() {
        // 加载Spring配置文件
        ApplicationContext context = new ClassPathXmlApplicationContext("bean7.xml");

        // 获取配置创建的对象
        Stu stu = context.getBean("stu", Stu.class);

        System.out.println(stu);
        stu.printStuInfo();
    }

    @Test
    public void testCollectionBook() {
        // 加载Spring配置文件
        ApplicationContext context = new ClassPathXmlApplicationContext("bean8.xml");

        // 获取配置创建的对象
        com.zt.spring5.collectiontype.Book book = context.getBean("book", com.zt.spring5.collectiontype.Book.class);

        System.out.println(book);
    }

    @Test
    public void testAutowire() {
        // 加载Spring配置文件
        ApplicationContext context = new ClassPathXmlApplicationContext("bean10.xml");

        // 获取配置创建的对象
        Employee employee = context.getBean("employee", Employee.class);
        System.out.println(employee);
    }
}
