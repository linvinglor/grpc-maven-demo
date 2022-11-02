# gRPC-maven-demo

### gRPC是什么
RPC，全称Remote Procedure Call，中文译为远程过程调用。通俗地讲，使用RPC进行通信，调用远程函数就像调用本地函数一样，RPC底层会做好数据的序列化与传输，从而能使我们更轻松地创建分布式应用和服务。

### 步骤
- 安装protobuf依赖
- 编写proto文件
- 编译proto文件（生成stub文件）
- 编写server端，实现接口
- 编写client端，测试接口

### 自定义服务端核心
```java
// 服务的添加：
io.grpc.Server server = ServerBuilder.forPort(9090).addService(new 自定义的Service类名1()).
.addService(new Service2()).addService(new Service3()).build();
// 服务的启动：
server.start();
// 服务的关闭：
server.awaitTermination();
```

### 自定义客户端核心
```java
ManagedChannelImpl channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();
DemoServiceGrpc.DemoServiceBlockingStub stub = DemoServiceGrpc.newBlockingStub(channel);
stub.login(LoginRequest.getDefaultInstance());
```
这三行代码，完成了grpc客户端调用服务器端最重要的三个步骤：
1. 创建连接到远程服务器的 channel
2. 构建使用该channel的客户端stub
3. 通过stub调用服务方法，执行RPC调用

### 运行
1. 编译代码
```shell
mvn clean compile
```
2. 运行server
```shell
mvn exec:java -Dexec.mainClass="com.demo.HelloWorldServer"
```
3. 运行client
```shell
mvn exec:java -Dexec.mainClass="com.demo.HelloWorldClient"
```
