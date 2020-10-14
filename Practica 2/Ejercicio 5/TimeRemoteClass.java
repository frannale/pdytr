/*
* RemoteClass.java
* Just implements the RemoteMethod interface as an extension to
* UnicastRemoteObject
*
*/
/* Needed for implementing remote method/s */
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
/* This class implements the interface with remote methods */
public class TimeRemoteClass extends UnicastRemoteObject implements ITimeRemoteClass
{
    protected TimeRemoteClass() throws RemoteException
    {
        super();
    }

    /* Remote method implementation */
    public Boolean sendTrue() throws RemoteException
    {
        return true;
    }

}