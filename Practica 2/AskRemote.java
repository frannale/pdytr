/* The client connects with the server and uses the remote object. The length for reading and writing are fixed*/
import java.rmi.Naming; /* lookup */
import java.rmi.registry.Registry; /* REGISTRY_PORT */
public class AskRemote
{
public static void main(String[] args)
{
/* Look for hostname and msg length in the command line */
if (args.length > 1)
{
    System.out.println("1 argument needed: file name");
    System.exit(1);
}
try {
    String rname = "//localhost:" + Registry.REGISTRY_PORT + "/remote";
    IFaceFileManager remote = (IFaceFileManager) Naming.lookup(rname);
    int bufferlength = 100;
    byte[] buffer = new byte[bufferlength];
    for(int i=0; i < 100; i++){
        buffer[i] = 'a';
    }
    remote.writeFile("prueba.txt", buffer, bufferlength/2);
    System.out.println("Done");
} catch (Exception e) {
    e.printStackTrace();
}
}
}