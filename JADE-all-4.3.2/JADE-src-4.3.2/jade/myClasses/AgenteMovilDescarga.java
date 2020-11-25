
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
	private String filename;
	private File file;
	private int offset = 0;
	private int bufferLength = 2048;
	private byte[] buffer = new byte[bufferLength];
	private int bytesTotalesLeidos = 0;
	private int bytesActualesLeidos;
	private String operation;
	private boolean loadAndDownload = false;

	// SETUP DE CREACION
	public void setup()
	{		
		try {

			// OBTENER LA OPERACION A REALIZAR
			Object[] args = getArguments();
			String operation = args[0].toString();

			// OBTENER NOMBRE DEL ARCHIVO
			filename =  args[1].toString();

			// GUARDA ID DE ORIGEN
			origen = here().getID().split("@", 0)[0];

			if(operation.equals("carga")){

				//NOMBRE DEL ARCHIVO COPIA
				file = new File("copia_server_" + filename);

				switchOrigenDestino("Main-Container", origen);
				
				// SE EJECUTA EL AFTERMOVE() PARA SIMULAR QUE SE MOVIO DE
				// MAIN-CONTAINER A CONTAINER-1
				afterMove();
			}
			else{
				
				if(operation.equals("cargaydescarga")){
					loadAndDownload = true;
				}

				//NOMBRE DEL ARCHIVO COPIA
				file = new File("copia_" + filename);
				
				switchOrigenDestino(origen,"Main-Container");
				
				// SE MUEVE AL CONTAINER QUE POSEE EL ARCHIVO
				doMove(destino_container);

			}
			System.out.println("Migrando el agente a " + destino_container.getID());
		} catch (Exception e) {
			System.out.println("\n\n\nNo fue posible migrar el agente\n\n\n");
		}
	}

	// SETEA CONTAINER DE ORIGEN Y DESTINO, EN ORIGEN ES DONDE QUEDA LA COPIA
	public void switchOrigenDestino(String origen, String destino){
		
		origen_container = new ContainerID(origen, null);
		destino_container = new ContainerID(destino, null);
	}

	// LOGICA DE COPIA DEL ARCHIVO
	public void copyFile(){

		String now = here().getID().split("@", 0)[0];

		try{
			// VERIFICA SI ES ORIGEN, PARA SABER SI DEBE LEER O ESCRIBIR
			if( ! now.equals(origen_container.getID().split("@", 0)[0])){
				
				// ABRE EL ARCHIVO
				File file = new File(filename);
				RandomAccessFile in = new RandomAccessFile(file, "r");

				System.out.println("Recolectando datos de " + destino_container.getID().split("@", 0)[0] + "...");
				
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

				// CREA ARCHIVO, EN CASO DE QUE YA EXISTA NO HACE NADA
				if(file.createNewFile()){
					System.out.println("Archivo creado con nombre: " + file.getName());
				}

				//ESCRIBE EL ARCHIVO
				FileOutputStream out = new FileOutputStream(file,true);
				DataOutputStream stream = new DataOutputStream(out);
				stream.write(buffer, 0, bytesActualesLeidos);
				stream.flush();
				out.flush();

				// SI LOS LEIDOS ES MENOR A EL MAXIMO ES QUE TERMINO
				if(bytesActualesLeidos == bufferLength){
					doMove(origen_container);
				}
			}
			System.out.println("Archivo copiado exitosamente! " + bytesTotalesLeidos + " bytes leidos.");
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	// AL MOVERSE DE CONTAINER
	protected void afterMove()
	{

		try{
				// EJECUTA LOGICA PARA COPIAR EL ARCHIVO
				copyFile();

				if(loadAndDownload){
					System.out.println("Realizando copia de verificacion");

					// RESETEAMOS LOS ACUMULADORES PARA REALIZAR LA NUEVA LECTURA
					offset = 0;
					bytesTotalesLeidos = 0;

					// NOMBRE DEL ARCHIVO COPIA
					file = new File("copia_server_" + filename);

					switchOrigenDestino("Main-Container", origen);

					// SI NO SE VUELVE FALSA SE GENERA UN LOOP
					loadAndDownload = false;
						
					// SIMULAR QUE SE MOVIO DE MAIN-CONTAINER A CONTAINER-1
					afterMove();
				}	

		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
}
