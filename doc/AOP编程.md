# AOP编程

## 基本概念

AOP表示面向切面编程，利用AOP可以对业务逻辑的各个部分进行隔离，从而使得业务逻辑
各部分之间的耦合度降低，提高程序的可重用性，同时提高开发效率。即不通过修改源代码
的方式，增加新的功能。

## 底层原理

- AOP底层使用动态代理
    - 有接口，使用JDK动态代理
         ```java
         interface UserDao {
             public void login();
         }
      
        class UserDaoImpl implements UserDao {
            public void login() {
                // 登录实现过程
            }
        } 
        ```
        - 创建UserDao接口实现类的代理对象，增强类的方法

    - 无接口，使用CGLIB动态代理
        ```java
        class User {
            public void add() {
            
            }
        }
        
        // 原始方法
        class Person extends User {
            public void add() {
                super.add();
                // 增强逻辑
            }
        }
        ```
        - 创建当前类子类的代理对象
    
## JDK动态代理实践

1、使用JDK动态代理，需要使用Proxy类里面的方法创建代理对象
```java
// 返回指定接口接口的代理类实例，该接口方法调用分派给指定的调用处理程序
static Object newProxyInstance(ClassLoader loader, 类<?>[] interface, InvocationHandler h);
```
第一个参数，类加载器

第二个参数，增强方法所在的类，这个类实现的接口，支持多个接口

第三个参数，实现这个接口InvocationHandler，创建代理对象，写增强的部分

2、具体实现步骤

- 创建接口，定义方法
    ```java
    public interface UserDao {
    
        public int add(int a, int b);
    
        public String update(String id);
    }
    ```
  
- 创建接口实现类，实现方法
    ```java
    public class UserDaoImpl implements UserDao{
        @Override
        public int add(int a, int b) {
        return a+b;
        }
    
        @Override
        public String update(String id) {
            return id;
        }
    }
    ```
  
- 使用Proxy类创建接口代理对象

```java
public class JDKProxy {
    public static void main(String[] args) {
        // 创建接口实现类代理对象
        Class[] interfaces = {UserDao.class};
        UserDaoImpl userDao = new UserDaoImpl();
        UserDao dao = (UserDao) Proxy.newProxyInstance(JDKProxy.class.getClassLoader(), interfaces, new UserDaoProxy(userDao));
        int result = dao.add(1, 2);
        System.out.println("result: " + result);
        String update = dao.update("200852");
        System.out.println("update" + update);
    }
}

// 创建代理对象代码
class UserDaoProxy implements InvocationHandler {

    // 创建的是谁的代理对象，就把谁传递进来
    private Object obj;
    public UserDaoProxy(Object obj) {
        this.obj = obj;
    }

    // 增强的逻辑
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 方法之前
        System.out.println("方法执行之前..." + method.getName() + "：传递的参数..." + Arrays.toString(args));

        // 被增强的方法执行
        Object res = method.invoke(obj, args);

        // 方法之后
        System.out.println("方法执行之后..." + obj);
        return res;
    }
}
```

## AOP相关术语

1、连接点

类里面哪些方法可以被增强，哪些方法就称为连接点

2、切入点

实际被真正增强的方法称为切入点

3、通知（增强）

实际增强的代码逻辑部分称为通知（增强）。通知有多种类型

- 前置通知
- 后置通知
- 环绕通知
- 异常通知
- 最终通知

4、切面

切面是动作，把通知应用到切入点的动作

## AOP操作准备工作

1、Spring框架一般基于AspectJ实现AOP操作。AspectJ不是Spring组成部分，是一个
独立AOP框架，一般把AspectJ和Spring框架一起使用，进行AOP操作

2、基于AspectJ实现AOP操作

- 基于xml配置文件实现
- 基于注解方式实现（常用）

3、需要在项目工程中引入相关依赖

4、切入点表达式

- 切入点表达式作用是知道哪个类里面的哪个方法进行增强
- 语法结构：execution([权限修饰符][返回类型][类全路径][方法名称]\([参数列表]))

案例一：对com.aop.dao.BookDao类里面的add进行增强

execution(* com.aop.dao.BookDao.add(..))

- *表示public，protected，private
- 空格表示省略返回类型
- .add表示使用的方法
- (..)表示方法的参数

案例二：对com.aop.dao.BookDao类里面的所有方法进行增强

execution(* com.aop.dao.BookDao.*(..))

案例三：对com.aop.dao这个包里面所有类的所有方法进行增强

execution(* com.aop.dao.\*.*(..))

### AOP操作基于AspectJ注解

1、创建类，在类里面定义方法
```java
public class User {
    public void add() {
        System.out.println("add.........");
    }
}
```

2、创建增强类（编写增强逻辑）

- 在增强类里面，创建方法，让不同方法代表不同通知类型

```java
// 增强类
public class UserProxy {

    // 前置通知
    public void before() {
        System.out.println("before......");
    }
}
```

3、进行通知的配置

- 在Spring配置文件中，开启注解扫描
  
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
                           http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd
                           http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop.xsd">

    <!-- 开启注解扫描   -->
    <context:component-scan base-package="com.aop.annotation"></context:component-scan>
</beans>
```

- 使用注解创建User和UserProxy对象
  
```java
// 增强类
@Component
public class UserProxy {

    // 前置通知
    public void before() {
        System.out.println("before......");
    }
}

// 被增强类
@Component
public class User {
    public void add() {
        System.out.println("add.........");
    }
}
```

- 在增强类上面添加注解@Aspect
  
```java
// 增强类
@Component
@Aspect // 生成代理对象
public class UserProxy {

    // 前置通知
    public void before() {
        System.out.println("before......");
    }
}
```

- 在Spring配置文件中开启生成代理对象

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
                           http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd
                           http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop.xsd">

    <!-- 开启注解扫描   -->
    <context:component-scan base-package="com.aop.annotation"></context:component-scan>
    <!-- 开启AspectJ生成代理对象   -->
    <aop:aspectj-autoproxy></aop:aspectj-autoproxy>
</beans>
```

4、配置不同类型的通知

- 在增强类内，在作为通知方法上面添加通知类型注解，使用切入点表达式配置

```java
// 增强类
@Component
@Aspect // 生成代理对象
public class UserProxy {

    // 前置通知
    @Before(value = "execution(* com.aop.annotation.User.add(..))")
    public void before() {
        System.out.println("before......");
    }
}
```

5、公共切入点抽取

```java
// 增强类
@Component
@Aspect // 生成代理对象
public class UserProxy {

    // 相同切入点抽取
    @Pointcut(value = "execution(* com.aop.annotation.User.add(..))")
    public void pointdemo() {

    }

    // 前置通知
    @Before(value = "pointdemo()")
    public void before() {
        System.out.println("before......");
    }
}
```

### AOP操作基于AspectJ配置文件