
import java.io.File;
import java.io.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/* This class implements the interface with remote methods */
public class FileManager extends UnicastRemoteObject implements IFaceFileManager{

    protected FileManager() throws RemoteException{

        super();
    }

    public BufferControlado readFile(String filename, int offset, int bytesSolicitados) throws RemoteException{
                
        try{
            // CREACION DE BUFFER DEL TAMANIO SOLICITADO DE LECTURA
            byte[] buffer = new byte[bytesSolicitados];

            // SE ABRE EL ARCHIVO QUE SE SOLICITO DESCARGAR
            File file = new File(filename);
            FileInputStream in = new FileInputStream(file);
            // SE LEE CANTIDAD DESDE LA POSICION SOLICITADA
            int bytesLeidos = in.read(buffer,offset,bytesSolicitados);

            System.out.println("Data back to client");
            // RETORNA UNA PAR, CANTIDAD DE BYTES LEIDOS Y EL BUFFER 
            return new BufferControlado(bytesLeidos, buffer);
        }catch(Exception e){
            e.printStackTrace();
            return new BufferControlado(-1, null);
        }
    }

    public int writeFile(String filename, byte[] data, int amountToWrite) throws RemoteException{
        
        try{
            File file = new File(filename);
            file.createNewFile();
            FileOutputStream out = new FileOutputStream(file,true);
            DataOutputStream stream = new DataOutputStream(out);
            stream.write(data, 0, amountToWrite);
            int bytesWritten = 0;
            bytesWritten = stream.size();

            stream.flush();
            stream.close();
            out.flush();
            out.close();
            
            System.out.println("Done writing data...");
            
            return bytesWritten;
        
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }

}