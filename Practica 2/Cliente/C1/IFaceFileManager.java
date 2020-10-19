
/* Iface that defines de methods to read and write files from the server */
import java.rmi.Remote;
import java.rmi.RemoteException;

/* This interface will need an implementing class */
public interface IFaceFileManager extends Remote
{


public BufferControlado readFile(String filename, int offset, int amountToRead) throws RemoteException;

public int writeFile(String filename, byte[] data, int amountToWrite,boolean firsTime) throws RemoteException;

}