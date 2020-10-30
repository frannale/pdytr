package pdytr.example.grpc;
import io.grpc.*;
import java.util.concurrent.TimeUnit;

public class Client
{
    public static void main( String[] args ) throws Exception
    {
      // Channel is the abstraction to connect to a service endpoint
      // Let's use plaintext communication because we don't have certs
      final ManagedChannel channel = ManagedChannelBuilder.forTarget("localhost:8080")
        .usePlaintext(true)
        .build();

      // It is up to the client to determine whether to block the call
      // Here we create a blocking stub, but an async stub,
      // or an async stub with Future are always possible.
      GreetingServiceGrpc.GreetingServiceBlockingStub stub = GreetingServiceGrpc.newBlockingStub(channel).withDeadlineAfter(324199895, TimeUnit.NANOSECONDS);
      
      GreetingServiceOuterClass.HelloRequest request =
        GreetingServiceOuterClass.HelloRequest.newBuilder()
          .setName("Ray")
          .build();

      long intialTime;
      long finalTime;
      long aproximatedTime;

      /* Take the time before the comunication */
      intialTime = System.nanoTime(); 

      // Finally, make the call using the stub
      GreetingServiceOuterClass.HelloResponse response = 
        stub.greeting(request);

      /* Take the time after the comunication */
      finalTime = System.nanoTime();

      /* The aproximated time of each individual comunication */
      aproximatedTime = (finalTime - intialTime);

      //Print comunicaction time
      System.out.println(aproximatedTime);

      // A Channel should be shutdown before stopping the process.
      channel.shutdownNow();
    }
}