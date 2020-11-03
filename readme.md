##  Tencent CloudBase(TCB) Java SDK


##  介绍
CloudBase 提供开发应用所需服务和基础设施。TCB Java SDK 让您可以在 云函数 中访问 TCB 的服务。

##  安装
通过 maven 进行安装。

```xml
<dependency>
    <groupId>com.tencent.cloudbase</groupId>
    <artifactId>tcb-java-sdk</artifactId>
</dependency>
```


##  文档

-   [存储](./doc/storage.md)
-   [数据库](./doc/database.md)
-   [云函数](./doc/function.md)



##  初始化

```java


AppClient appClient = new AppClient();
appClient.setEnvName("envName");  // 设置环境名称
appClient.setTimeOut(5000);      // 设置http client超时时间，单位毫秒，默认5s

```
