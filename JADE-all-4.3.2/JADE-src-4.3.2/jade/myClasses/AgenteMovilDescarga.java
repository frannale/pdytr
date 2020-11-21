
import jade.core.*;
import java.io.File;
import java.io.*;
public class AgenteMovilDescarga extends Agent
{
	//DECLARACION DE ESTADO DEL AGENTE
	private String origen;
	private ContainerID origen_container;
	private ContainerID destino_container;

	// ESTADO DEL ARCHIVO
	private String filename = "Entrega1.pdf";
	private File file = new File("copia_" + filename);
	private int offset = 0;
	private int bufferLength = 2048;
	private byte[] buffer = new byte[bufferLength];
	private int bytesTotalesLeidos = 0;
	private int bytesActualesLeidos;

	// SETUP DE CREACION
	public void setup()
	{		
		try {
			// GUARDA ID DE ORIGEN
			origen = here().getID().split("@", 0)[0];

			// CREA ARCHIVO Y STREAMS
			file.createNewFile();
			System.out.println("Setup finalizado " + "\n\n");

			origen_container = new ContainerID("Container-1", null);
			destino_container = new ContainerID("Main-Container", null);
			System.out.println("Migrando el agente a " + destino_container.getID());
			// SE MUEVE AL CONTAINER QUE POSEE EL ARCHIVO
			doMove(destino_container);
		} catch (Exception e) {
			System.out.println("\n\n\nNo fue posible migrar el agente\n\n\n");
		}
	}

	// AL MOVERSE DE CONTAINER
	protected void afterMove()
	{
		String now = here().getID().split("@", 0)[0];

		try{
			// VERIFICA SI ES ORIGEN, PARA SABER SI DEBE LEER O ESCRIBIR
			if( ! now.equals(origen)){
				// ABRE EL ARCHIVO
				File file = new File(filename);
				RandomAccessFile in = new RandomAccessFile(file, "r");
				System.out.println( origen);
				// LEE EL ARCHIVO
				in.seek(offset);
				bytesActualesLeidos = in.read(buffer, 0, bufferLength);
				in.close();

				// VUELVE AL CONTAINER DE ORIGEN
				doMove(origen_container);
			}
			else{
				// ACTUALIZA CANTIDAD DE LEIDOS
				bytesTotalesLeidos += bytesActualesLeidos;
				offset = bytesTotalesLeidos;

				//ESCRIBE EL ARCHIVO
				FileOutputStream out = new FileOutputStream(file,true);
				DataOutputStream stream = new DataOutputStream(out);
                stream.write(buffer, 0, bytesActualesLeidos);
                stream.flush();
                out.flush();

                // SI LOS LEIDOS ES MENOR A EL MAXIMO ES QUE TERMINO
				if(bytesActualesLeidos == bufferLength){
					doMove(destino_container);
				}else{
					System.out.println("Archivo descargado exitosamente! " + bytesTotalesLeidos + " bytes leidos.");
				} 	

			}
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
}