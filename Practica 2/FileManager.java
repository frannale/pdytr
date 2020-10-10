import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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

public void writeFile(String filename, byte[] data, int amountToWrite) throws RemoteException
{
    try{
        File file = new File(filename);
        file.createNewFile();
        FileOutputStream out = new FileOutputStream(file,true);
        out.write(data, 0, amountToWrite);
        out.flush();
        out.close();
        System.out.println("Done writing data...");
    }catch(Exception e){
        e.printStackTrace();
    }
}

}