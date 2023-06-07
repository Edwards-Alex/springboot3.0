# SpringBoot 3.0

## Spring Boot3-核心特性

### 1、快速入门

#### 1. spring Initializer 创建向导

​		创建向导快速创建项目

#### 2.springboot3 依赖管理机制

1. 为什么导入`start-web`所有依赖都导入进来？
   + 开发什么场景，导入什么场景启动器
   + maven 的依赖传递原则：A - B - C : A就拥有了B和C
   + 导入场景启动器，场景启动器自动把这个场景的所有核心依赖全部导入进来、
2. 为什么不用写版本号？
   + 每个boot项目都有一个父项目 `spring-boot-start-parent` 
   + parent 父项目 

####  3.自动配置机制

  		1. 初步理解
  	   		1. 自动配置的Tomcat、SpringMVC等
  	   			以前：DispathServlet、ViewResolver、CharsetEncoding...
  	   			现在：自动配置好这些组件
  	   			验证：容器中有了什么组件
  	   			 public static void main(String[] args) {
  	       			 //java10新特性：局部变量可以使用var自动推断
  	       			 var context = SpringApplication.run(DemoApplication.class, args);
  	        			for (String beanDefinitionName : context.getBeanDefinitionNames()) {
  	            			System.out.println(beanDefinitionName);
  	       				 }
  	       		}
  	   		2. 默认的包扫描规则
  	   			@SpringBootApplication 注释的是主程序
  	   			SpringBoot 只会扫描主程序及其下的子包，自动的component-scan功能
  	   			自定义扫描路径 @SpringBootApplication(scanBasePackages="com.atguigu")
  	   						@ComponentScan("com.atguigu") 直接指定扫描路径
  	   		3. 配置默认值
  	   			配置文件的配置值都是和某个类的对象值一一对应的
  	   			绑定了配置文件中每一项的类：配置属性类
  	   			比如：ServerProperties 绑定了所有和服务器有关的配置
  	   				 MultipartProperties 绑定了所有和文件上传有关的配置
  	   				 ... 参照官方文档或者参照绑定的配置属性类
  	   		4. 按需加载自动配置
  	   			导入场景spring-boot-starter-web
  	   			场景启动器除了会导入相关依赖，还导入了spring-boot-starter,是所有starter的starter,基础		核心starter。
  	   			spring-boot-starter 导入了一个包 spring-boot-autoconfigure,包里面是各种场景的			autoconfigration 自动配置类
  	   			虽然全场景自动配置都在 spring-boot-autoconfigure 这个包，但是不是全都开启的。
  	   				导入哪个场景就开启哪个自动配置
  	   		
  	   		总结：导入场景启动器、出发spring-boot-autoconfigure 这个包的自动配置生效、容器中就会有相关的		   场景功能。



流程：

 1. 导入`starter-web`场景启动器：导入了web开发场景

     1. 场景启动器导入了相关场景的所有依赖：`starter-json`、`start-tomcat`、`springmvc`
         	2. 每个场景启动器都引入了一个 `spring-boot-starter`，核心场景启动器。
         	3. **核心场景启动器**引入了`spring-boot-configure`包。
         	4. `spring-boot-autpconfigure`囊括了所有场景的所有配置
         	5. 只要这个包下所有类都能生效，那么相当于SpringBoot写好的整合功能就生效了。
         	6. SpringBoot 默认扫描不到`spring-boot-configure`下写好的所有配置类。（这些配置类给我们做了整合操作）
         	7. 

 2. 主程序： `@SpringBootApplication`

    1. `@SpringBootApplication` 由三个注解组成 `@SpringBootConfiguration` `@EnableAutoConfiguration` `@ComponentScan` 

    2. springboot 默认只能扫描到主程序所在包及其子包，扫描不到`spring-boot-autoconfigure` 包中官方写好的**配置类**

    3. @EnableAutoConfiguration：SpringBoot开启自动配置的核心。

       1. 是由`@Import({AutoConfigurationImportSelector.class})`提供功能：批量给容器中导入组件。

       2. springboot启动会默认加载140多个配置类。

       3. 这140多个配置类来自于`spring-boot-autoconfiguration` 下`MATA-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.import`指定的

          项目启动的时候利用@Import批量导入机制把autoconfigure包下140多个`xxxAutoConfiguration`类导入进来（**自动配置类**）

       4. 按需生效：

          	1. 并不是者140多个自动配置类都会生效
          	2. 每一个自动配置类，都有条件注解`@ConditionalOnXxx`,只有条件成立，才能生效。

    4. XXXAutoConfiguration **自动配置类**

       1.  给容器中使用@Bean放一堆组件
       2.  每个自动配置类都可能有这个注解`@EnableConfigurationProperties(ServerPeoperties.class)`,用来把配置文件配的指定前缀的属性值封装到`XXXProperties`属性类中去
       3.  已Tomcat为例，所有服务器的配置以server开头的。配置都封装到了属性类中。
       4.  给**容器**中放的所有**组件**的一些**核心参数**，都来自于`XXPeoperties`。`XXPeoperties`都是和**配置文件**绑定的
       5.  只要修改配置文件的值，核心文件的底层参数都能修改。

 3. 写业务，全程无需关心任何整合（底层这些整合写好了，也生效了）

​	**<font color=red>核心流程</font>**：

  1. 导入`starter`，就去导入`AutoConfiguration`包。

  2. `AutoConfiguration`包里有一个文件`MATA-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.import`,里面指定所有启动要加载的自动配置类

  3. `@EnableAutoConfiguration`会自动把上面文件里面写的所有**自动配置类**导入进来。**XXXAutoConfiguration**是有条件注释按需加载。

  4. `XXXAutoConfiguration`给容器中导入一堆组件，组件都是从`xxxProperties`中提取属性值。

  5. `xxxProperties`又是和**配置文件**进行了绑定

     效果：导入`starter`,修改配置文件，就能修改底层行为。

     

     

     ![image-20230605155306346](C:\Users\10326\AppData\Roaming\Typora\typora-user-images\image-20230605155306346.png)

#### 4. 如何学好springboot

摄影：

- 傻瓜：自动配置好
- 单反：焦距、光感、快门等...
- 傻瓜+单反

1. 理解自动配置原理
   1. 导入starter --> 生效xxxAutoConfiguration --> 组件 -->xxxProperties -->配置文件
2. 理解**其他框架底层**
   1. 拦截器
3. 可以随时定制化任何组件
   1. 配置文件
   2. 自定义组件

普通开发: `导入starter`,Contorller,Service,Mapper,偶尔修改配置文件

**高级开发**：自定义组件，自定义配置，自定义starter

核心：

- 这个场景自动配置导入了哪些组件，我们能不能Autowired进来使用
- 能不能通过修改配置改变组件的一些默认参数
- 需不需要自己完全定义这个组件
- **<font color=red>场景定制化</font>**



<font color=red>最佳实战</font>

选场景，导入到项目

- 官方：starter
- 第三方：去仓库搜

写配置，改配置文件关键项

- 数据库修改(链接地址、账号密码...)

分析这个场景给我们导入了**哪些能用的组件**

- 自动装配这些组件进行后续使用
- 不满意boot提供的自动配好的默认组件
  - 定制化
    - 改配置
    - 自定义组件

整合redis：

- 选场景：`spring-boot-starter-data-redis`
  - 场景AutoConfiguration就是这个场景的自动配置类

- 写配置：
  - 分析到这个场景的自动配置类开启了哪些属性绑定关系
  - `@EnableConfigurationProperties(RedisProperties.class)`
  - 修改redis的相关配置

- 分析组件：
  - 分析道`RedisAutoConfiguration`给容器中放了`StringRedisTemplate`
  - 给业务代码中自动装配`StringRedisTemplate`

- 定制化
  - 修改配置文件
  - 自定义组件，自己给容器中放一个`StringRedisTemplate`


#### 5. yaml基础

 5. 小技巧lombok

    - 简化javabean开发。自动生成构造器、getter/setter、自动生成Builder模式等

    - ```
      
      ```

      

###  2、核心技能

1. 常用注释

​		springboot 摒弃传统XML配置方式，改为全注释驱动。

  1. 组件注册

     1. @Configuration、@SpringBootConfiguration

     2. @Bean、@Scope[实例是否多实例]

     3. @Controller、@Service、@Repository、@Component

     4. @Import

     5. @ComponentScan

        

     步骤：

     1. @Configuration 编写一个配置类
     2. 在配置类中，自定义方法给容器中注册组件，配合@Bean
     3. 或使用@Import 导入第三方的组件

     ​			

  2. 条件注释

     | 如果注释指定的条件成立，则触发指定行为

     @ConditionalOnXxx


​		@ConditionalOnClass: 如果路径中存在此类，则触发指定行为

​		@ConditionalOnMissClass: 如果路径中不存在此类，则触发指定行为

​		@ConditionOnBean：如果容器中存在这个Bean组件，则触发指定行为

​		@ConditionOnMissBean：如果容器中不存在这个Bean组件，则触发指定行为

​		... 

 3. 属性绑定

    @ConfigurationProperties :声明组件的属性和配置文件的哪些前缀开始项进行绑定

    @EnableConfigurationProperties ：快速注册组件

    ​	使用场景：SpringBoot 默认只扫描主程序下的包，当导入第三方包，程序上有@Component、@ConfigurationPeoperties注解也没用。扫描不进来。

    将容器中任意**组件（bean)**的属性值 和 **配置文件**的配置项的值 **进行绑定**

    1. 容器中注册组件（bean）@Component @Bean...

    2. 使用**@ConfigurationProperties 声明组件和配置文件的哪些配置项进行绑定**
    
       [如果遇到 配置文件 中文输出乱码 idea setting FileEncoding 设置编码格式为UTF-8 底层编译仍用ASCII码勾选]

​	

