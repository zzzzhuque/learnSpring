package com.zt.spring5.factoryBean;

import com.zt.spring5.collectiontype.Course;
import org.springframework.beans.factory.FactoryBean;

public class MyBean implements FactoryBean<Course> {

    @Override
    public boolean isSingleton() {
        return FactoryBean.super.isSingleton();
    }

    // 定义返回bean
    @Override
    public Course getObject() throws Exception {
        Course course = new Course();
        course.setCname("abc");
        return course;
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }
}
