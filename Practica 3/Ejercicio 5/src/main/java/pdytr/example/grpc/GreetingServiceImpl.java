package pdytr.example.grpc;

import io.grpc.stub.StreamObserver;

public class GreetingServiceImpl extends GreetingServiceGrpc.GreetingServiceImplBase {
  @Override
  public void greeting(GreetingServiceOuterClass.HelloRequest request,
        StreamObserver<GreetingServiceOuterClass.HelloResponse> responseObserver) {
    
    try 
    {
        Thread.sleep(5000);
        // You must use a builder to construct a new Protobuffer object
        GreetingServiceOuterClass.HelloResponse response = GreetingServiceOuterClass.HelloResponse.newBuilder()
          .setGreeting("")
          .build();

        // Use responseObserver to send a single response back
        responseObserver.onNext(response);

        // When you are done, you must call onCompleted.
        responseObserver.onCompleted();
    } 
    catch(InterruptedException e)
    {
      System.out.println(e);
    }
  }
}