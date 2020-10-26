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
      GreetingServiceGrpc.GreetingServiceBlockingStub stub = GreetingServiceGrpc.newBlockingStub(channel).withDeadlineAfter(5000, TimeUnit.MILLISECONDS);

      /* Take the time before the comunication */
      long intialTime = System.nanoTime();
      
      GreetingServiceOuterClass.HelloRequest request =
        GreetingServiceOuterClass.HelloRequest.newBuilder()
          .setName("Ray")
          .build();

      /* Take the time after the comunication */
      long finalTime = System.nanoTime();

      /* The aproximated time of each individual comunication */
      long aproximatedTime = (finalTime - intialTime);

      // Finally, make the call using the stub
      GreetingServiceOuterClass.HelloResponse response = 
        stub.greeting(request);
       
      System.out.println(response);

      // A Channel should be shutdown before stopping the process.
      channel.shutdownNow();
      System.out.println(aproximatedTime); 
    }
}