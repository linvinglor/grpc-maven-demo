package com.demo;

import java.util.concurrent.TimeUnit;

import gencode.HelloRequest;
import gencode.HelloResponse;
import gencode.HelloServiceGrpc;
import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HelloWorldClient{
  private static final Logger logger = Logger.getLogger(HelloWorldClient.class.getName());

  private final HelloServiceGrpc.HelloServiceBlockingStub blockingStub;
  public HelloWorldClient(Channel channel) {
    blockingStub = HelloServiceGrpc.newBlockingStub(channel);
  }

  public void greet(String name) {
    logger.info("Will try to greet " + name + " ...");
    HelloRequest request = HelloRequest.newBuilder().setName(name).build();
    HelloResponse response;
    try {
      response = blockingStub.hello(request);
    } catch (StatusRuntimeException e) {
      logger.log(Level.WARNING, "RPC getMessagefailed: {0}", e.getStatus());
      return;
    }
    logger.info("Greeting: " + response.getGreeting());
  }

  public static void main(String[] args) throws InterruptedException {
    int port = 3333;
    ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", port).usePlaintext().build();
    HelloWorldClient client = new HelloWorldClient(channel);
    String user = "ying.lin";
    client.greet(user);
    channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
  }
}
