# pandas_parent

微服务2.x的版本

### 1. 版本

* Spring Boot： 2.7.18
* Spring Cloud：2021.0.9

### 2. 模块

* pandas-parent： 父工程
* pandas-eureka： 注册中心
* pandas-gateway： 网关
* pandas-common： 通用模块
* pandas-order： 账单服务
* pandas-user：用户服务

### 3. 详细说明

#### 3.1 pandas-parent

> 主要是用来做系统版本控制

* pom

```xml

<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.wxf</groupId>
    <artifactId>pandas_parent</artifactId>
    <version>1.0-SNAPSHOT</version>
    <packaging>pom</packaging>

    <name>pandas_parent</name>
    <description>父工程</description>

    <modules>
        <module>pandas-eureka</module>
        <module>pandas-gateway</module>
        <module>pandas-common</module>
        <module>pandas-order</module>
        <module>pandas-user</module>
    </modules>

    <properties>
        <maven.compiler.source>8</maven.compiler.source>
        <maven.compiler.target>8</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <spring-cloud.version>2021.0.9</spring-cloud.version>
        <spring-boot.version>2.7.18</spring-boot.version>
        <pandas-common.version>1.0-SNAPSHOT</pandas-common.version>

    </properties>

    <dependencies>

    </dependencies>

    <dependencyManagement>
        <dependencies>
            <!-- spring-boot-dependencies -->
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-dependencies</artifactId>
                <version>${spring-boot.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>

            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>${spring-cloud.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>

            <dependency>
                <groupId>com.wxf.commons</groupId>
                <artifactId>pandas-common</artifactId>
                <version>${pandas-common.version}</version>
            </dependency>

        </dependencies>
    </dependencyManagement>
</project>

```

#### 3.1 pandas-eureka

* pom

```xml

<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>com.wxf</groupId>
        <artifactId>pandas_parent</artifactId>
        <version>1.0-SNAPSHOT</version>
    </parent>

    <groupId>com.wxf.eureka</groupId>
    <artifactId>pandas-eureka</artifactId>
    <version>1.0-SNAPSHOT</version>
    <packaging>jar</packaging>

    <name>pandas_eureka</name>
    <description>Eureka注册中心</description>

    <properties>
        <maven.compiler.source>8</maven.compiler.source>
        <maven.compiler.target>8</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>

    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <!-- spring-cloud-starter-netflix-eureka-server -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
        </dependency>


    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <version>${spring-boot.version}</version>
                <executions>
                    <execution>
                        <goals>
                            <goal>repackage</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>

</project>

```

* 程序入口

```java

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Eureka 注册中心
 *
 * @author Wxf
 * @since 2024-03-11 16:31:12
 **/
@EnableEurekaServer
@SpringBootApplication
public class EurekaServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServiceApplication.class, args);
    }
}

```

* application.yml

```yaml
server:
  port: 9876

spring:
  application:
    name: eureka-service
  profiles:
    active: dev

```

* application-dev.yml

```yaml
server:
  port: 9876

eureka:
  server:
    enable-self-preservation: false # 是否开启自我保护
  instance:
    appname: ${spring.application.name} # 应用服务名称
    hostname: eureka-server-A
    prefer-ip-address: true # 偏向IP
    ip-address: 192.168.202.9 # 指定IP地址
    lease-renewal-interval-in-seconds: 30 # 续租时间间隔
    lease-expiration-duration-in-seconds: 90 # 过期时间
  client:
    service-url:
      defaultZone: http://eureka-server-A:9876/eureka,http://eureka-server-B:9877/eureka # 注册中心服务列表
    fetch-registry: true # 拉取服务列表
    register-with-eureka: true # 是否注册到注册中心

```

* application-test.yml

```yaml
server:
  port: 9877

eureka:
  server:
    enable-self-preservation: false # 是否开启自我保护
  instance:
    appname: ${spring.application.name} # 应用服务名称
    hostname: eureka-server-B
    prefer-ip-address: true # 偏向IP
    ip-address: 192.168.202.9 # 指定IP地址
    lease-renewal-interval-in-seconds: 30 # 续租时间间隔
    lease-expiration-duration-in-seconds: 90 # 过期时间
  client:
    service-url:
      defaultZone: http://eureka-server-A:9876/eureka,http://eureka-server-B:9877/eureka # 注册中心服务列表
    fetch-registry: true # 拉取服务列表
    register-with-eureka: true # 是否注册到注册中心

```

* host 配置

```shell
127.0.0.1 eureka-server-A
127.0.0.1 eureka-server-B

```

* 验证

> 分别访问 http://127.0.0.1:9876、http://127.0.0.1:9877 可以看到相应的服务注册信息

#### 3.2 pandas-common

#### 3.3 pandas-order

```xml

<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>com.wxf</groupId>
        <artifactId>pandas_parent</artifactId>
        <version>1.0-SNAPSHOT</version>
    </parent>

    <groupId>com.wxf.order</groupId>
    <artifactId>pandas-order</artifactId>
    <packaging>jar</packaging>

    <name>pandas-order</name>

    <properties>
        <maven.compiler.source>8</maven.compiler.source>
        <maven.compiler.target>8</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>

    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <!-- spring-cloud-starter-netflix-eureka-client -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>

        <!-- spring-cloud-starter-loadbalancer -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-loadbalancer</artifactId>
        </dependency>

        <!-- spring-cloud-starter-openfeign -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-openfeign</artifactId>
        </dependency>


    </dependencies>


    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <version>${spring-boot.version}</version>
                <executions>
                    <execution>
                        <goals>
                            <goal>repackage</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>

</project>

```

* 程序入口

```java
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * 订单服务入口
 *
 * @author Wxf
 * @since 2024-03-18 09:01:21
 **/
@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
public class OrderServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder().build();
    }
}

```

* 调用user-service

```java

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 用户feign客户端
 *
 * @author Wxf
 * @since 2024-03-18 14:15:15
 **/
@FeignClient(value = "user-service", path = "/users", fallback = UserFeignClientFallBack.class)
public interface UserFeignClient {

    @GetMapping
    public Integer getPort();
}

```

* 调用user-service中的fallback

```java
/**
 * @author Wxf
 * @since 2024-03-18 17:58:15
 **/
public class UserFeignClientFallBack implements UserFeignClient {

    @Override
    public Integer getPort() {
        return -1;
    }
}

```

* 日志配置

> 需要配置配置文件内的配置使用

```java

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Wxf
 * @since 2024-03-19 09:00:22
 **/
@Configuration
public class FeignConfig {

    /*
        NONE 不输出日志
        BASIC 只有请求方法、URL、响应状态代码、执行时间
        HEADERS基本信息以及请求和响应头
        FULL 请求和响应 的heads、body、metadata，建议使用这个级别
     */
    @Bean
    public Logger.Level feignLevel() {
        return Logger.Level.FULL;
    }
}

```

* 提供接口，供测试

```java

import com.wxf.order.clients.UserFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author Wxf
 * @since 2024-03-18 11:32:56
 **/
@RestController
@RequestMapping
public class IndexController {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private UserFeignClient userFeignClient;

    @GetMapping("/users")
    public Integer getUser() {
        return restTemplate.getForObject("http://user-service/users/", Integer.class);
    }


    @GetMapping("/users/fallback")
    public Integer getUserFallBack() {
        return userFeignClient.getPort();
    }

}

```

* application.yml

```yaml
server:
  port: 8092
  servlet:
    context-path: /orders

spring:
  profiles:
    active: dev
  application:
    name: order-service
```

* application-dev.yml

```yaml
server:
  port: 8092

spring:
  cloud:
    loadbalancer:
      retry:
        enabled: true # 默认开启负载均衡

eureka:
  instance:
    appname: ${spring.application.name} # 应用服务名称
    prefer-ip-address: true # 偏向IP
    ip-address: 192.168.202.9 # 指定IP地址
    lease-renewal-interval-in-seconds: 30 # 续租时间间隔
    lease-expiration-duration-in-seconds: 90 # 过期时间
  client:
    service-url:
      defaultZone: http://eureka-server-A:9876/eureka,http://eureka-server-B:9877/eureka # 注册中心服务列表
    fetch-registry: true # 拉取服务列表
    register-with-eureka: true # 是否注册到注册中心

# 负载均衡，指定服务的话则为全局
user-service:
  ribbon:
    ConnectTimeOut: 1000 # 连接超市时间
    ReadTimeOut: 10000 # 请求处理时间
    OkToRetyrOnAllOperations: true # 对所有操作都重试
    MaxAutoRetyiesNextServer: 2 # 切换实例重拾次数
    NFLoadBalancerRuleClassName: com.netflix.loadbalancer.RoundRobinRule # 负载均衡策略

# 日志配置
logging:
  level:
    com.wxf.order.clients.UserFeignClient: debug

```

* application-test.yml

```yaml
server:
  port: 8093

spring:
  cloud:
    loadbalancer:
      retry:
        enabled: true # 默认开启负载均衡

eureka:
  instance:
    appname: ${spring.application.name} # 应用服务名称
    prefer-ip-address: true # 偏向IP
    ip-address: 192.168.202.9 # 指定IP地址
    lease-renewal-interval-in-seconds: 30 # 续租时间间隔
    lease-expiration-duration-in-seconds: 90 # 过期时间
  client:
    service-url:
      defaultZone: http://eureka-server-A:9876/eureka,http://eureka-server-B:9877/eureka # 注册中心服务列表
    fetch-registry: true # 拉取服务列表
    register-with-eureka: true # 是否注册到注册中心


# 负载均衡，指定服务的话则为全局
USER-SERVICE:
  ribbon:
    ConnectTimeOut: 1000 # 连接超市时间
    ReadTimeOut: 10000 # 请求处理时间
    OkToRetyrOnAllOperations: true # 对所有操作都重试
    MaxAutoRetyiesNextServer: 2 # 切换实例重拾次数
    NFLoadBalancerRuleClassName: com.netflix.loadbalancer.RoundRobinRule # 负载均衡策略

# 日志配置
logging:
  level:
    com.wxf.order.clients.UserFeignClient: debug
```

#### 3.4 pandas-user

* pom

```xml

<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>com.wxf</groupId>
        <artifactId>pandas_parent</artifactId>
        <version>1.0-SNAPSHOT</version>
    </parent>

    <groupId>com.wxf.user</groupId>
    <artifactId>pandas-user</artifactId>
    <packaging>jar</packaging>

    <name>pandas-user</name>

    <properties>
        <maven.compiler.source>8</maven.compiler.source>
        <maven.compiler.target>8</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <!-- spring-cloud-starter-netflix-eureka-client -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>

        <!-- spring-cloud-starter-loadbalancer -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-loadbalancer</artifactId>
        </dependency>

        <!-- spring-cloud-starter-openfeign -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-openfeign</artifactId>
        </dependency>


    </dependencies>


    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <version>${spring-boot.version}</version>
                <executions>
                    <execution>
                        <goals>
                            <goal>repackage</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>

</project>

```

* 程序入口

```java

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * 用户服务入口
 *
 * @author Wxf
 * @since 2024-03-18 09:00:52
 **/
@EnableFeignClients
@SpringBootApplication
@EnableEurekaClient
public class UserServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder().build();
    }
}

```

* 调用订单服务

```java

import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author Wxf
 * @since 2024-03-18 14:17:38
 **/
@FeignClient(value = "order-service", path = "/orders", fallback = OrderFeignClientFallBack.class)
public interface OrderFeignClient {
}

```

* 调用订单服务fallback

```java
/**
 * @author Wxf
 * @since 2024-03-18 14:56:51
 **/
public class OrderFeignClientFallBack implements OrderFeignClient {
}

```

* 提供接口

```java

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户Controller
 *
 * @author Wxf
 * @since 2024-03-18 10:47:05
 **/
@RestController
@RequestMapping
public class UserController {

    @Value("${server.port:8080}")
    private Integer port;


    @GetMapping
    public Integer getPort() {
        return port;
    }

}

```

* application.yml

```yaml
server:
  port: 8094
  servlet:
    context-path: /users

spring:
  profiles:
    active: dev
  application:
    name: user-service
```

* application.yml

```yaml
server:
  port: 8094

spring:
  cloud:
    loadbalancer:
      retry:
        enabled: true # 默认开启负载均衡

eureka:
  instance:
    appname: ${spring.application.name} # 应用服务名称
    prefer-ip-address: true # 偏向IP
    ip-address: 192.168.202.9 # 指定IP地址
    lease-renewal-interval-in-seconds: 30 # 续租时间间隔
    lease-expiration-duration-in-seconds: 90 # 过期时间
  client:
    service-url:
      defaultZone: http://eureka-server-A:9876/eureka,http://eureka-server-B:9877/eureka # 注册中心服务列表
    fetch-registry: true # 拉取服务列表
    register-with-eureka: true # 是否注册到注册中心


# 负载均衡，指定服务的话则为全局
ORDER-SERVICE:
  ribbon:
    ConnectTimeOut: 1000 # 连接超市时间
    ReadTimeOut: 10000 # 请求处理时间
    OkToRetyrOnAllOperations: true # 对所有操作都重试
    MaxAutoRetyiesNextServer: 2 # 切换实例重拾次数
    NFLoadBalancerRuleClassName: com.netflix.loadbalancer.RoundRobinRule # 负载均衡策略


```

* application-test.yml

```yaml
server:
  port: 8095

spring:
  cloud:
    loadbalancer:
      retry:
        enabled: true # 默认开启负载均衡

eureka:
  instance:
    appname: ${spring.application.name} # 应用服务名称
    prefer-ip-address: true # 偏向IP
    ip-address: 192.168.202.9 # 指定IP地址
    lease-renewal-interval-in-seconds: 30 # 续租时间间隔
    lease-expiration-duration-in-seconds: 90 # 过期时间
  client:
    service-url:
      defaultZone: http://eureka-server-A:9876/eureka,http://eureka-server-B:9877/eureka # 注册中心服务列表
    fetch-registry: true # 拉取服务列表
    register-with-eureka: true # 是否注册到注册中心


# 负载均衡，指定服务的话则为全局
ORDER-SERVICE:
  ribbon:
    ConnectTimeOut: 1000 # 连接超市时间
    ReadTimeOut: 10000 # 请求处理时间
    OkToRetyrOnAllOperations: true # 对所有操作都重试
    MaxAutoRetyiesNextServer: 2 # 切换实例重拾次数
    NFLoadBalancerRuleClassName: com.netflix.loadbalancer.RoundRobinRule # 负载均衡策略


```

#### 3.5 pandas-gateway

```xml

<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>com.wxf</groupId>
        <artifactId>pandas_parent</artifactId>
        <version>1.0-SNAPSHOT</version>
    </parent>

    <groupId>com.wxf.gateway</groupId>
    <artifactId>pandas-gateway</artifactId>
    <packaging>jar</packaging>

    <name>pandas_gateway</name>
    <description>网关服务</description>

    <properties>
        <maven.compiler.source>8</maven.compiler.source>
        <maven.compiler.target>8</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-gateway</artifactId>
        </dependency>

        <!-- spring-cloud-starter-netflix-eureka-client -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>

        <!-- spring-cloud-starter-loadbalancer -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-loadbalancer</artifactId>
        </dependency>

    </dependencies>


    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <version>${spring-boot.version}</version>
                <executions>
                    <execution>
                        <goals>
                            <goal>repackage</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>

</project>

```

* 程序入口

```java

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 网关服务
 *
 * @author Wxf
 * @since 2024-03-11 19:54:21
 **/
@SpringBootApplication
public class GatewayServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayServiceApplication.class, args);
    }
}

```

* application.yml

```yaml
spring:
  profiles:
    active: dev
```

* application-dev.yml

```yaml
server:
  port: 8001

spring:
  application:
    name: gateway-service
  cloud:
    gateway:
      enabled: true # 是否启用网关
      httpclient:
        connect-timeout: 1000 # 连接超时时间
        response-timeout: 5s # 响应超市时间
      routes:
        - id: user-service-route
          uri: lb://user-service
          predicates:
            - Path=/users/**
        - id: order-service-route
          uri: lb://order-service
          predicates:
            - Path=/orders/**
      discovery:
        locator:
          enabled: true # 开启服务发现和路由功能，默认为false
          lower-case-service-id: true # 默认路由会大写，转成小写

eureka:
  instance:
    appname: ${spring.application.name} # 应用服务名称
    prefer-ip-address: true # 偏向IP
    ip-address: 192.168.202.9 # 指定IP地址
    lease-renewal-interval-in-seconds: 30 # 续租时间间隔
    lease-expiration-duration-in-seconds: 90 # 过期时间
  client:
    service-url:
      defaultZone: http://eureka-server-A:9876/eureka,http://eureka-server-B:9877/eureka # 注册中心服务列表
    fetch-registry: true # 拉取服务列表
    register-with-eureka: true # 是否注册到注册中心

```

### 4. 简述一下整个服务搭建流程

> 配置文件中会有两个配置dev和test，这里主要是用来做测试的，多环境测试以及负载均衡
> 服务中的不同端口，是为了方便观察负载均衡的

* 首先要创建服务注册中心eureka-service集群
* 注册中心注册成功后，可以在两个服务中心看到相应的两个服务
* 然后创建user-service和order-service两个服务，并开启负载均衡
* 测试完成后，再通过feign实现调用
* 将网关服务注册到注册中心，并添加到负载均衡，通过网关进行调用可以看到实现了负载均衡