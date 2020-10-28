package pdytr.example.grpc;

import io.grpc.stub.StreamObserver;
import java.io.File;
import java.io.*;

public class FileManagerImpl extends FileManagerGrpc.FileManagerImplBase {
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