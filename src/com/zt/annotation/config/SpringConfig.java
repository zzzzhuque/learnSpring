package com.zt.annotation.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration  // 作为配置类
@ComponentScan(basePackages = {"com.zt.annotation"})
public class SpringConfig {

}
