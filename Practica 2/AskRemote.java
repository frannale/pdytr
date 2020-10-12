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

            /* Name of the file */
            String fileName = new String("yo.jpg");

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
            String fileNameServer = new String("copia.jpg");

            /*Write unltil all the file is written*/
            while(bytesWritten != in.length()){
                /* Obtain the amount of data written in the server */
                bytesWritten += remote.writeFile(fileNameServer, buffer, bufferLength/2);
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
                System.out.println(bytesWritten);

                /* Read the next block of data to be send to the server */
                bytesReaded += in.read(buffer, 0, bufferLength);
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