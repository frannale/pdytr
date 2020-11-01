package pdytr.example.grpc;

import io.grpc.stub.StreamObserver;
import java.io.File;
import java.io.*;
import com.google.protobuf.ByteString;

public class FileManagerImpl extends FileManagerGrpc.FileManagerImplBase {

  @Override
  public void readFile(FileManagerOuterClass.DownloadFile request,
                       StreamObserver<FileManagerOuterClass.DownloadResponse> responseObserver){

    try{
      //ABRE EL ARCHIVO
      File file = new File(request.getFileNameServer());
      RandomAccessFile in = new RandomAccessFile(file, "r");

      //CREA BUFFER
      int bufferLength = request.getAmountToRead();
      byte[] buffer = new byte[bufferLength];
      ByteString bufferString = ByteString.EMPTY;

      // LEE EL ARCHIVO
      in.seek(request.getOffset());
      int bytesReaded = in.read(buffer, 0, bufferLength);
      in.close();
      bufferString = bufferString.copyFrom(buffer);

      // ARMA EL RESPONSE
      FileManagerOuterClass.DownloadResponse response = FileManagerOuterClass.DownloadResponse.newBuilder()
      .setBytesReaded(bytesReaded)
      .setData(bufferString)
      .build();

      // RESPONDE REQUEST
      responseObserver.onNext(response); 

      // FINALIZA EJECUCION
      responseObserver.onCompleted();

    }catch(Exception e){
      e.printStackTrace();
    }
  }

  @Override
  public void writeFile(FileManagerOuterClass.File request,
        StreamObserver<FileManagerOuterClass.Response> responseObserver) {
    
          try{
            
            //Se crea el nuevo archivo
            File file = new File(request.getFileNameServer());
            file.createNewFile();
            
            /*// SI EXISTE EL ARCHIVO Y ES PRIMERA VEZ SE TRUNCA
            if(firstTime){
                PrintWriter writer = new PrintWriter(file);
                writer.print("");
                writer.close();
            }*/

            //Convertir de BytesString a bytes[]
            byte[] data = request.getData().toByteArray();
            
            //Crea los stream para escribir en el archivo
            FileOutputStream out = new FileOutputStream(file,true);
            DataOutputStream stream = new DataOutputStream(out);
            stream.write(data, 0, request.getAmountToWrite());
            int bytesWritten = 0;
            bytesWritten = stream.size();

            stream.flush();
            stream.close();
            out.flush();
            out.close();
            
            //Construye el
            FileManagerOuterClass.Response response = FileManagerOuterClass.Response.newBuilder()
              .setBytesWritten(bytesWritten)
              .build();

            // Use responseObserver to send a single response back
            responseObserver.onNext(response);    

            // When you are done, you must call onCompleted.
            responseObserver.onCompleted();
        
        }catch(Exception e){
            e.printStackTrace();
        }
  }
}