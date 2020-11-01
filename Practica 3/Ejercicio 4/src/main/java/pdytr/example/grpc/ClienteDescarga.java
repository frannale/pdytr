package pdytr.example.grpc;
import io.grpc.*;
import java.io.File;
import java.io.*;
import java.io.RandomAccessFile;
import java.util.Scanner;
import com.google.protobuf.ByteString;
import java.util.concurrent.TimeUnit;

public class ClienteDescarga
{
    public static void main( String[] args ) throws Exception
    {
        try{

            // BINDEA UN CANAL CON EL SERVER
            final ManagedChannel channel = ManagedChannelBuilder.forTarget("localhost:8080")
            .usePlaintext(true)
            .build();
            // ASOCIA EL CANAL A UN STUB
            FileManagerGrpc.FileManagerBlockingStub stub = FileManagerGrpc.newBlockingStub(channel);
            
            // CREA EL BUFFER
            int bufferLength = 100;
            byte[] buffer = new byte[bufferLength];
            
            //CREA ARCHIVO
            String filename =  "prueba.deb";
            File file = new File("copia_" + filename);
            file.createNewFile();
            FileOutputStream out = new FileOutputStream(file,true);
            DataOutputStream stream = new DataOutputStream(out);
            int bytesTotalesLeidos = 0;

            // VARS DE COMUNICACION RPC
            FileManagerOuterClass.DownloadFile request;
            FileManagerOuterClass.DownloadResponse response;
                       
            while(true){
                //ARMA REQUEST
                request = 
                    FileManagerOuterClass.DownloadFile.newBuilder()
                    .setFileNameServer(filename)
                    .setOffset(bytesTotalesLeidos)
                    .setAmountToRead(bufferLength)
                    .build();

                //ENVIA REQUEST AL SERVER
                response = stub.readFile(request);

                //LO QUE RETORNA EL SERVER
                buffer = response.getData().toByteArray();
                int bytesActualesLeidos = response.getBytesReaded();
                bytesTotalesLeidos += bytesActualesLeidos;

                //ESCRIBE EL ARCHIVO
                stream.write(buffer, 0, bytesActualesLeidos);
                stream.flush();
                out.flush();

                // SI LOS LEIDOS ES MENOR A EL MAXIMO ES QUE TERMINO
                if(bytesActualesLeidos < bufferLength) break;
            }
            
            // CIERRA ARCHIVO
            stream.close();
            out.close();

            // CIERRA EL CANAL
            channel.shutdownNow();

            System.out.println("Archivo descargado exitosamente! " + bytesTotalesLeidos + " bytes leidos.");
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}