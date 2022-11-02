package com.demo;

import gencode.HelloRequest;
import gencode.HelloResponse;
import gencode.HelloServiceGrpc;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.util.logging.Logger;

public class HelloWorldServer {
    private static final Logger logger = Logger.getLogger(HelloWorldServer.class.getName());
    private Server server;
    private void start() throws IOException, InterruptedException {
        /* The port on which the server should run */
        int port = 3333;
        server = ServerBuilder.forPort(port)
                .addService(new HelloWordImpl())
                .build()
                .start();
        logger.info("Server started, listening on " + port);
        server.awaitTermination();
    }
    public static void main(String[] args) throws IOException, InterruptedException {
        final HelloWorldServer server = new HelloWorldServer();
        server.start();
    }

    static class HelloWordImpl extends HelloServiceGrpc.HelloServiceImplBase{
        @Override
        public void hello(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
            String greeting = new StringBuilder("Hello, ").append(request.getName()).toString();
            HelloResponse response = HelloResponse.newBuilder().setGreeting(greeting).build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
    }
}