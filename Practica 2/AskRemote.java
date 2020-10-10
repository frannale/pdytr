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

    /* Name of the file */
    String fileName = new String("datos.txt");
    
    /*Buffer creation*/
    byte[] buffer = new byte[bufferlength];

    /* Number of bytes acumulator */
    int bytesWritten = 0;

    /*Open the file that you wanna write in the server*/
    File file = new File(fileName);
    FileInputStream in = new FileInputStream(file);
    int bytesReaded = in.read(buffer);

    /* The name that your file will have in the server */
    String fileNameServer = new String("prueba.txt");

    while(bytesReaded != -1){
        bytesWritten = remote.writeFile(fileNameServer, buffer, bytesReaded);
        /* In case the amout of data readed from the file dosen't qual bufferLength */
        while(bytesWritten != bytesReaded){
            bytesReaded -= bytesWritten;
            bytesWritten = remote.writeFile(fileNameServer, buffer, bytesReaded);
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