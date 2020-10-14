/* The client connects with the server and uses the remote object. The length for reading and writing are fixed*/
import java.rmi.Naming; /* lookup */
import java.rmi.registry.Registry; /* REGISTRY_PORT */
import java.io.*;
import java.io.File;
import java.util.Scanner;


// CLIENTE
public class AskRemote{

    public static void main(String[] args){
        /* Look for hostname and msg length in the command line */
        // if (args.length < 2){
        //     System.out.println("1 arg = W/R 2 arg = filename");
        //     System.exit(1);
        // }

        try {
            
            // USUARIO ELIJE ACCION
            Scanner scanner = new Scanner(System.in);
            System.out.println("Elija una opcion: \r\n 1 - Subir archivo \r\n 2 - Descargar archivo \r\n 3 - Descargar verificado");
            String action = scanner.nextLine();
            // USUARIO ELIJE ACCION
            System.out.println("Ingrese el nombre del archivo:");
            scanner = new Scanner(System.in);
            String filename = scanner.nextLine();
            // OBTIENE REFERENCIA A OBJETO REMOTO
            String rname = "//localhost:" + Registry.REGISTRY_PORT + "/remote";
            IFaceFileManager remote = (IFaceFileManager) Naming.lookup(rname);

            
            AskRemote cliente = new AskRemote();
            // CLIENTE ELIJE ACCION
            switch(action) {
                case "1":
                    // UPLOAD ARCHIVO
                    cliente.writeFile(remote,filename);break;
                case "2":
                    // DOWNLOAD ARCHIVO
                    cliente.readFile(remote,filename);break;
                case "3":
                    // DOWNLOAD ARCHIVO CON VERIFICACION
                    cliente.readFile(remote,filename);
                    cliente.writeFile(remote, "copia_" +filename);
                    break;
                default:
                    break;
            }         

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // UPLOAD DE ARCHIVO LOCAL AL REMOTO
    public static void writeFile(IFaceFileManager remote, String fileName){

        try {

            /*Open the file that you wanna write in the server*/
            File file = new File(fileName);
            RandomAccessFile in = new RandomAccessFile(file, "r");

            /* Buffer creation */
            int bufferLength = 100;
            byte[] buffer = new byte[bufferLength];

            /* Number of bytes written in the server */
            int bytesWritten = 0;

            /* Read the the first block of data to be send */
            int bytesReaded = 0;
            int actualBytesReaded = 0;
            bytesReaded = in.read(buffer, 0, bufferLength);

            /* The name that your file will have in the server */
            String fileNameServer = new String("copia_server_" + fileName);

            /*Write unltil all the file is written*/
            while(bytesWritten != in.length()){
                /* Obtain the amount of data written in the server */
                bytesWritten += remote.writeFile(fileNameServer, buffer, bytesReaded - bytesWritten);
                /* In case the amout of data written from the file dosen't equal bytesReaded 
                   in this iteration, write in the server until both are equal */

                while(bytesWritten != bytesReaded){
                    /* Because the read moves the offset of the file we have 
                       to make it so that it point to the last amount written */
                    in.seek(bytesWritten);
                    /* Now we read from the file the differnece between the las amount 
                       written and the amount of data readed for this iteration.

                       This read could have been inside de next writeFile method 
                       but for readeability reasons we used an additional variable*/
                    actualBytesReaded = in.read(buffer, 0, bytesReaded - bytesWritten);
                    /* Keep acumulating until bytesWritten equals bytesReaded */
                    bytesWritten += remote.writeFile(fileNameServer, buffer, actualBytesReaded);
                }
                /* A simple progress showing of the data written in each iteration */

                /* Read the next block of data to be send to the server */
                bytesReaded += in.read(buffer, 0, bufferLength);

            }
            System.out.println("Archivo cargado exitosamente! " + bytesWritten + " bytes escritos.");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // DESCARGA DE ARCHIVO REMOTO
    public static void readFile(IFaceFileManager remote,String fileNameServer){

        try {

            // CREACION DEL ARCHIVO DE FORMAL LOCAL
            File file = new File("copia_" + fileNameServer);
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
                BufferControlado bufferControlado = remote.readFile(fileNameServer, bytesLeidosTotal, bufferlength);

                bytesLeidosLast = bufferControlado.getCantidad();

                // SI LA CANTIDAD RECIBIDA ES MENOR QUE LA SOLICITADA ES EL FINAL DEL ARCHIVO
                if(bytesLeidosLast == -1){
                    break;
                }

                buffer = bufferControlado.getBuffer();

                // ESCRIBE LOCAL EL BUFFER QUE RECIBIO
                stream.write(buffer, 0, bytesLeidosLast);

                // ACTUALIZO BYTES LEIDOS
                bytesLeidosTotal += bytesLeidosLast;
                
            }

            System.out.println("Archivo descargado exitosamente! " + bytesLeidosTotal + " bytes leidos totales.");
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