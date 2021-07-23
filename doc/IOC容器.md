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
      4. bean7.xml

### IOC操作Bean管理（基于注解）