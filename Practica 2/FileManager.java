import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/* This class implements the interface with remote methods */
public class FileManager extends UnicastRemoteObject implements IFaceFileManager
{

protected FileManager() throws RemoteException
{
    super();
}

public byte[] readFile(String name, int offset, int amountToRead) throws RemoteException
{
    byte[] data = new byte[25];
    System.out.println("Data back to client");
    return data;
}

public void writeFile(String name, byte[] data, int amountToWrite) throws RemoteException
{
    System.out.println("Data back to client");
}

}