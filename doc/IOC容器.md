# IOC容器

## IOC底层原理

1. 什么是IOC（Inverse Of Control）
    1. 控制反转，把对象创建和对象之间的调用过程，交给Spring管理
    2. 使用IOC是为了降低耦合度
    3. 入门案例就是IOC实现
    
2. IOC底层原理
    1. 工厂模式、xml解析、反射
    
3. 详解IOC底层原理

Service层调用Dao层方法的原始方式
```java
class UserService {
    public void execute() {
        UserDao dao = new UserDao();
        dao.add();
    }
}

class UserDao {
    public void add() {
       System.out.println("add");
    }
}
```
存在问题是耦合度太高，Dao层发生变化，Service层被迫发生变化。

### 解决方案一：工厂模式

```java
class UserService {
    public void execute() {
        UserDao dao = UserFactory.getDao();
        dao.add();
    }
}

class UserDao {
    public void add() {
       System.out.println("add");
    }
}

// 工厂类
class UserFactory {
    public static UserDao getDao() {
        return new UserDao();
    }
}
```

Service层通过工厂模式得到Dao层方法，即便Dao层方法发生改动，也是在工厂类做
相应修改，对Service层的开发者无感知，所以降低了Service层和Dao层的耦合度

### 解决方案二：IOC过程

第一步在xml文件中配置创建的对象`<bean id="classname" class="classpath"></bean>`

第二步针对Service层和Dao层的类，创建工厂类

```java
class UserService {
   public void execute() {
      UserDao dao = UserFactory.getDao();
      dao.add();
   }
}

class UserDao {
   public void add() {
      System.out.println("add");
   }
}

// 工厂类
class UserFactory {
    public static UserDao getDao() {
        // xml解析
       String classValue = class属性值;
       // 通过反射创建对象
       Class clazz = Class.forName(classValue);
       return (User) clazz.newInstance();
    }
}
```

这样做的好处是，如果Dao层方法SOURCE Path发生改变，只需要修改xml文件即可，
进一步降低耦合度

## IOC接口

1. IOC思想基于IOC容器完成，IOC容器底层就是对象工厂
   
2. Spring提供IOC容器实现的两种方式
   1. BeanFactory：IOC容器基本实现，是Spring内部使用的接口，不提供开发者使用
      1. **加载xml配置文件时不会创建对象，在获取对象getBean时才创建**
   2. ApplicationContext：BeanFactory的子接口，提供更多更强大的功能，面向开发者
      1. **加载xml配置文件时就会创建对象**
   
3. ApplicationContext接口的实现类
   1. FileSystemXmlApplicationContext
      1. 针对xml文件的Absolute Path
   2. ClassPathXmlApplicationContext
      1. 针对xml文件的Source Path

## IOC操作Bean管理

1. 什么是Bean管理
   1. Spring创建对象
   2. Spring注入属性
   
2. Bean管理操作有两种方式
   1. 基于xml配置文件方式实现
   2. 基于注解方式实现

### IOC操作Bean管理（基于xml）

1. 基于xml方式创建对象
   1. `<bean id="user" class="com.zt.spring5.User"></bean>`
   2. bean标签中有很多属性
      1. id属性：getBean获取对象的别名
      2. class属性：创建对象的Source Path
      3. name属性：作用和id一样，但可以加特殊符号，比如'/'，现在基本不用
   3. 创建对象时默认执行无参构造方法，若只定义了有参构造方法，执行时会报错

2. 基于xml方式注入属性
   1. DI：依赖注入，就是注入属性
      1. 使用set方法注入，代码：[bean2.xml](../src/bean2.xml)和[Book.java](../src/com/zt/spring5/Book.java)
      2. 使用有参构造注入，代码：[bean3.xml](../src/bean3.xml)和[Orders.java](../src/com/zt/spring5/Orders.java)
   2. p名称空间注入（了解即可）
      1. 使用p名称空间注入，可以简化基于xml配置方式
   
3. 基于xml方式注入其他类型属性
   1. 字面量
      1. null值`<property name="authorAddress">
         <null></null>
         </property>`
      2. 属性值包含特殊符号
      ```xml
        <!--
            属性值中包含特殊符号
            1、把<>进行转义，比如<是&lt; >是&gt;
            2、把带特殊符号的内容写到CDATA
        -->
        <property name="authorAddress">
            <value><![CDATA[<<Nan Jing>>]]></value>
        </property>
      ```
      
   2. 注入属性-外部Bean
      1. 创建两个类service类和dao类
      2. 在service调用dao里面的方法
      3. 代码在TestBean.TestBeanInject和bean4.xml

   3. 注入属性-内部Bean和级联赋值
      1. 一对多关系：部门和员工，部门是一，员工是多，一个部门有多个员工，
         一个员工属于一个部门
      2. 在实体类之间表示一对多关系
      3. 代码在bean5.xml
   
   4. 注入属性-级联赋值
      1. 形式类似外部bean注入
      2. 代码在bean6.xml
   
   5. 注入集合属性
      1. 注入数组类型属性
      2. 注入List集合类型属性
      3. 注入Map集合类型属性
      4. bean7.xml、stu.java
   
   6. 在集合里面设置对象类型值
      1. bean7.xml、Course.java
   
   7. 把集合注入部分提取出来
      1. 在Spring配置文件中引入名称空间util`xmlns:util="http://www.springframework.org/schema/util"`
      2. 使用util标签完成list集合注入提取
      3. bean8.xml
   
4. FactoryBean
   1. Spring有两种bean，一种普通bean，一种工厂bean
   2. 普通bean在配置文件中定义bean类型就是返回类型
   3. 工厂bean在配置文件中定义bean类型可以和返回类型不一样
      1. 创建类，让这个类作为工厂bean，实现接口FactoryBean
      2. 实现接口里面的方法，在实现的方法中定义返回的bean类型
      3. 工厂bean要求普通类实现接口FactoryBean，代码在MyBean.java
   
5. bean作用域
   1. 在Spring里面，设置创建bean实例是单实例还是多实例
   2. 在Spring里面，默认情况下，bean是单实例对象
   3. `<bean scope="singleton/prototype">`前者为单实例，后者为多实例
      1. 设置scope是singleton时，加载spring配置文件时就会创建单实例对象
      2. 设置scope是prototype时，在调用getBean方法时创建多实例对象
   
6. bean生命周期（从对象创建到对象销毁的过程）
   1. 通过构造器创建bean实例（无参数构造）
   2. 为bean的属性设置值和对其他bean引用（调用set方法）
   3. **把bean实例传递bean后置处理器的方法**
   4. 调用bean的初始化方法（需要进行配置初始化的方法init-method）
   5. **把bean实例传递bean后置处理器的方法**
   6. bean可以使用了（对象获取到了）
   7. 当容器关闭的时候，调用bean的销毁方法（需要配置销毁的方法destroy-method）
   
7. xml自动装配
   1. 概念：根据装配规则（属性名称或者属性类型），Spring自动将匹配的属性值进行注入
   2. 使用autowire属性，代码在bean10.xml
   3. 但这个用的少，一般用@autowire
   
8. 外部属性文件

比如连接数据库，用druid配置如下
```xml
<!-- 直接配置连接池   -->
 <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
     <property name="driverClassName" value="com.mysql.cj.jdbc.Driver"></property>
     <property name="url" value="jdbc:mysql://localhost:3306/t_user"></property>
     <property name="username" value="root"></property>
     <property name="password" value="123456789"></property>
 </bean>
```
但这些具体信息可以整合到properties文件中，详见jdbc.properties，并使用context
名称空间。

在xml中使用标签引入外部属性文件
```xml
<!-- 引入外部属性文件 -->
<context:property-placeholder location="classpath:jdbc.properties"/>
<bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
  <property name="driverClassName" value="${prop.driverClass}"></property>
  <property name="url" value="${prop.url}"></property>
  <property name="username" value="${prop.username}"></property>
  <property name="password" value="${prop.password}"></property>
</bean>
```

代码在bean11.xml

### IOC操作Bean管理（基于注解）

1、介绍注解
- 注解的概念：注解是代码中特殊的标记
- 格式：@注解名称(属性名称=属性值, 属性名称=属性值, ...)
- 注解可作用在类、方法、属性
- 使用目的：简化xml配置

2、Spring针对Bean管理中创建对象提供注解

- @Component
- @Service
- @Controller
- @Repository

上面四个注解功能相同，都可以用来创建bean实例，只是会把每个注解放在不同层

3、基于注解方式实现对象创建

1. 引入依赖
2. 开启组建扫描，查看bean12.xml
   1. include-filter说明扫描哪些类
   2. exclude-filter说明不扫描哪些类
3. 创建类，在类上添加创建对象注解

4、基于注解方式实现属性注入

- @Autowired：根据属性类型自动装配🔥
   - 把service和dao对象创建，在service和dao类添加创建对象注解
   - 在service层注入dao层对象，在service类添加dao类型属性，在属性上使用注解
   
- @Qualifier：根据属性名称注入
   - 需要和@Autowired配合使用，当一个接口有多个实现类，使用类型难以唯一匹配，就需要这个注解
   - @Qualifier(value = 属性名称)
- @Resource：可以根据类型注入，可以根据名称注入
   - @Resource根据类型注入
   - @Resource(name = 属性名称)根据名称注入
   - 属于javax，java扩展包，不建议用
- @Value：注入普通类型属性
   - @Value(value = "abc")
   - private String name;
   
5、完全注解开发

- 创建配置类，替代xml配置文件
```java
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration  // 作为配置类
@ComponentScan(basePackages = {"com.zt.annotation"})
public class SpringConfig {
    
}
```
- 编写测试类
```java
 @Test
 public void testSpringConfig() {
     // 加载配置类
     ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);

     UserService userService = context.getBean("userService", UserService.class);
     System.out.println(userService);
     userService.add();
 }
```

这部分属于注解开发，在Spring Boot中得到了很大应用