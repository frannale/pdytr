/* The client connects with the server and uses the remote object. The length for reading and writing are fixed*/
import java.rmi.Naming; /* lookup */
import java.rmi.registry.Registry; /* REGISTRY_PORT */
import java.io.*;
import java.io.File;

// CLIENTE
public class AskRemote{

    public static void main(String[] args){
        /* Look for hostname and msg length in the command line */
        if (args.length > 1){
            System.out.println("1 argument needed: file name");
            System.exit(1);
        }

        try {
            // OBTIENE REFERENCIA A OBJETO REMOTO
            String rname = "//localhost:" + Registry.REGISTRY_PORT + "/remote";
            IFaceFileManager remote = (IFaceFileManager) Naming.lookup(rname);

            
            AskRemote cliente = new AskRemote();
            // CLIENTE ELIJE ACCION
            switch('R') {
                case 'W':
                    // UPLOAD ARCHIVO
                    cliente.writeFile(remote);break;
                case 'R':
                    // DOWNLOAD ARCHIVO
                    cliente.readFile(remote);break;
                default:
                    break;
            }         

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // UPLOAD DE ARCHIVO LOCAL AL REMOTO
    public static void writeFile(IFaceFileManager remote){

        try {

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

    // DESCARGA DE ARCHIVO REMOTO
    public static void readFile(IFaceFileManager remote){

        try {
            
            /* The name that your file will have in the server */
            String fileNameServer = new String("descarga.txt");

            // CREACION DEL ARCHIVO DE FORMAL LOCAL
            File file = new File(fileNameServer);
            file.createNewFile();
            FileOutputStream out = new FileOutputStream(file,true);
            DataOutputStream stream = new DataOutputStream(out);

            // TAMANIO BUFFER Y SOLICITUD
            int bufferlength = 100;
            
            // CREA BUFFER CON EL TAMANIO DE LA SOLICITUD QUE HARA
            byte[] buffer = new byte[bufferlength];

            //CONTADOR DE BYTES LEIDOS
            int bytesLeidosTotal = 0;
            // LEIDOS ULTIMA VEZ
            int bytesLeidosLast = 0;

            while(true){

                // INVOCA EL METODO REMOTO, INDICANDO ARCHIVO, POSICION EN EL MISMO Y CANTIDAD DE BYTES A LEER
                BufferControlado bufferControlado = remote.readFile("datos.txt", bytesLeidosTotal, bufferlength);
                bytesLeidosLast = bufferControlado.getCantidad();
                buffer = bufferControlado.getBuffer();
                // ESCRIBE LOCAL EL BUFFER QUE RECIBIO
                stream.write(buffer, bytesLeidosTotal, bytesLeidosLast);
                // ACTUALIZO BYTES LEIDOS
                bytesLeidosTotal = stream.size();

                // SI LA CANTIDAD RECIBIDA ES MENOR QUE LA SOLICITADA ES EL FINAL DEL ARCHIVO
                if(bytesLeidosLast != bufferlength ){
                    break;
                }

            }
            
            // CIERRA CONEXIONES CON LOS ARCHIVOS
            stream.flush();
            stream.close();
            out.flush();
            out.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}