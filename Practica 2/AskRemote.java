/* The client connects with the server and uses the remote object. The length for reading and writing are fixed*/
import java.rmi.Naming; /* lookup */
import java.rmi.registry.Registry; /* REGISTRY_PORT */
import java.io.*;

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

    /* Nombre del archivo */
    String fileName = new String("20200724_175608.jpg");
    
    /*Buffer creation*/
    byte[] buffer = new byte[bufferlength];

    /* Number of bytes acumulator */
    int bytesWritten = 0;

    /*Abrir el archivo a esribir*/
    File file = new File(fileName);
    FileInputStream in = new FileInputStream(file);
    int bytesReaded = in.read(buffer);

    while(bytesReaded != -1){
        bytesWritten = remote.writeFile("prueba.jpg", buffer, bytesReaded);
        while(bytesWritten != bytesReaded){
            bytesReaded -= bytesWritten;
            bytesWritten = remote.writeFile("prueba.jpg", buffer, bytesReaded);
        }
        System.out.println(bytesWritten);
        bytesReaded = in.read(buffer);
        bytesWritten = 0;
    }


} catch (Exception e) {
    e.printStackTrace();
}
}
}