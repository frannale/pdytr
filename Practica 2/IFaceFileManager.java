/* Iface that defines de methods to read and write files from the server */
import java.rmi.Remote;
import java.rmi.RemoteException;

/* This interface will need an implementing class */
public interface IFaceFileManager extends Remote
{


public byte[] readFile(String name, int offset, int amountToRead) throws RemoteException;

public void writeFile(String name, byte[] data, int amountToWrite) throws RemoteException;

}