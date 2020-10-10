import java.io.File;
import java.io.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/* This class implements the interface with remote methods */
public class FileManager extends UnicastRemoteObject implements IFaceFileManager
{

protected FileManager() throws RemoteException
{
    super();
}

public byte[] readFile(String filename, int offset, int amountToRead) throws RemoteException
{
    byte[] data = new byte[25];
    System.out.println("Data back to client");
    return data;
}

public int writeFile(String filename, byte[] data, int amountToWrite) throws RemoteException
{
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