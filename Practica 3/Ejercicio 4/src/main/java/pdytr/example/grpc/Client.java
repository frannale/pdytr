package pdytr.example.grpc;
import io.grpc.*;
import java.io.File;
import java.io.RandomAccessFile;
import java.util.Scanner;
import com.google.protobuf.ByteString;
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
      FileManagerGrpc.FileManagerBlockingStub stub = FileManagerGrpc.newBlockingStub(channel);

      File file = new File("img.jpg");
      RandomAccessFile in = new RandomAccessFile(file, "r");

      /* Buffer creation */
      int bufferLength = 100;
      byte[] buffer = new byte[bufferLength];

      // Variables necesarias para la comunicación con el server RPC
      ByteString bufferString = ByteString.EMPTY;
      FileManagerOuterClass.File request;
      FileManagerOuterClass.Response response;

      /* Number of bytes written in the server */
      int bytesWritten = 0;

      /* Read the the first block of data to be send */
      int bytesReaded = 0;
      int actualBytesReaded = 0;
      bytesReaded = in.read(buffer, 0, bufferLength);

      /* The name that your file will have in the server */
      String fileNameServer = new String("copia_server_" + "img.jpg");

      /*Write unltil all the file is written*/
      while(bytesWritten != in.length()){
          /* Transformas el buffer a ByteString para que sea compatible con el tipo de proto */
          bufferString = bufferString.copyFrom(buffer);

          /* Obtain the amount of data written in the server */
          request = 
            FileManagerOuterClass.File.newBuilder()
              .setFileNameServer(fileNameServer)
              .setData(bufferString)
              .setAmountToWrite(bytesReaded - bytesWritten)
              .build();
          response = stub.writeFile(request);
          bytesWritten += response.getBytesWritten();

          /* In case the amout of data written from the file dosen't equal bytesReaded 
              in this iteration, write in the server until both are equal */
          while(bytesWritten != bytesReaded){
              /* Because the read moves the offset of the file we have 
                  to make it so that it point to the last amount written */
              in.seek(bytesWritten);
              /* Now we read from the file the differnece between the las amount 
                  written and the amount of data readed for this iteration.

                  This read could have been inside de next writeFile method 
                  but for readeability reasons we used an additional variable*/
              actualBytesReaded = in.read(buffer, 0, bytesReaded - bytesWritten);
              
              /* Transformas el buffer a ByteString para que sea compatible con el tipo de proto */
              bufferString = bufferString.copyFrom(buffer);

              /* Obtain the amount of data written in the server */
              request = 
                FileManagerOuterClass.File.newBuilder()
                  .setFileNameServer(fileNameServer)
                  .setData(bufferString)
                  .setAmountToWrite(actualBytesReaded)
                  .build();
              response = stub.writeFile(request);
              
              /* Keep acumulating until bytesWritten equals bytesReaded */
              bytesWritten += response.getBytesWritten();
          }

          /* Read the next block of data to be send to the server */
          bytesReaded += in.read(buffer, 0, bufferLength);

      }

      System.out.println("Archivo cargado exitosamente! " + bytesWritten + " bytes escritos.");

      // A Channel should be shutdown before stopping the process.
      channel.shutdownNow();
    }
}