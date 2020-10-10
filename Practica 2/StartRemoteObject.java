/* Creates the object which has the remote methods to be invoked and 
registers the object so that it becomes avaliable
*/
import java.rmi.registry.Registry; /* REGISTRY_PORT */
import java.rmi.Naming; /* rebind */
public class StartRemoteObject
{
public static void main (String args[])
{
try{

    FileManager robject = new FileManager();

    String rname = "//localhost:" + Registry.REGISTRY_PORT + "/remote";
    Naming.rebind(rname, robject);
}
catch (Exception e) {
    System.out.println("Hey, an error occurred at Naming.rebind");
    e.printStackTrace();
    System.out.println(e.getMessage());
}

}

}
