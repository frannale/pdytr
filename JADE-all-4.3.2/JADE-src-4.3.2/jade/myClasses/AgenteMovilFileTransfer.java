
import jade.core.*;
import jade.core.behaviours.*;
import java.io.File;
import java.io.*;
public class AgenteMovilFileTransfer extends Agent
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

	// SETUP DE CREACION
	public void setup()
	{		
		try {

			// OBTENER LA OPERACION A REALIZAR
			Object[] args = getArguments();
			String operation = args[0].toString();
			// OBTENER NOMBRE DEL ARCHIVO
			String aux_filename =  args[1].toString();

			// GUARDA ID DE ORIGEN
			origen = here().getID().split("@", 0)[0];

			// CLIENTE ELIJE ACCION
            switch(operation) {
                case "carga":
                    // UPLOAD ARCHIVO
					addBehaviour( new CargaBehaviour(aux_filename));
					break;
                case "descarga":
                    // DOWNLOAD ARCHIVO
					addBehaviour( new DescargaBehaviour(aux_filename));
					break;
                case "cargaydescarga":
                    // DOWNLOAD ARCHIVO CON VERIFICACION
					addBehaviour( new DescargaBehaviour(aux_filename));
					addBehaviour( new CargaBehaviour("copia_d_" + aux_filename));
                    break;
                default:
                    break;
			}
		} catch (Exception e) {
			System.out.println("\n\n\nNo fue posible migrar el agente\n\n\n");
		}
	}

	public class CargaBehaviour extends Behaviour
	{	
		private String filenameCarga;
		
		public CargaBehaviour(String filename) {
			filenameCarga = filename;
		}
		
		public void action() {

			// INICIAR VARIABLES PARA RECORRER EL ARCHIVO A LEER
			offset = 0;
			bytesTotalesLeidos = 0;

			//NOMBRE DEL ARCHIVO COPIA
			filename = filenameCarga;
			file = new File("copia_c_" + filenameCarga);

			switchOrigenDestino("Main-Container", origen);
			
			// SE EJECUTA EL AFTERMOVE() PARA SIMULAR QUE SE MOVIO DE
			// MAIN-CONTAINER A CONTAINER-1
			afterMove();
		}
		
		public boolean done() {
			return true;
		}
	}

	public class DescargaBehaviour extends Behaviour
	{	
		private String filenameDescarga;
		
		public DescargaBehaviour(String filename) {
			filenameDescarga = filename;
		}
		
		public void action() {

			// INICIAR VARIABLES PARA RECORRER EL ARCHIVO A LEER
			offset = 0;
			bytesTotalesLeidos = 0;

			//NOMBRE DEL ARCHIVO COPIA
			filename = filenameDescarga;
			file = new File("copia_d_" + filename);
			
			switchOrigenDestino(origen,"Main-Container");
			
			// SE MUEVE AL CONTAINER QUE POSEE EL ARCHIVO
			doMove(destino_container);
		}
		
		public boolean done() {
			return true;
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

				if(offset == 0){
					System.out.println("Transfiriendo " + file.getName() + " a " + origen_container.getID().split("@", 0)[0] + "...");
				}
				// LEE EL ARCHIVO
				in.seek(offset);
				bytesActualesLeidos = in.read(buffer, 0, bufferLength);
				in.close();

				// VUELVE AL CONTAINER DE ORIGEN
				doMove(origen_container);
			}
			else{
				
				// CREA ARCHIVO, EN CASO DE QUE YA EXISTA NO HACE NADA
				if(file.createNewFile()){
					System.out.println("Archivo creado con nombre: " + file.getName());
				}

				if(offset == 0){
					System.out.println("Recibiendo " + filename + " desde " + destino_container.getID().split("@", 0)[0] + "...");
				}
				
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
				}
				else{
					System.out.println("Archivo copiado exitosamente! " + bytesTotalesLeidos + " bytes leidos. \n\n");
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	// AL MOVERSE DE CONTAINER
	protected void afterMove()
	{
		// EJECUTA LOGICA PARA COPIAR EL ARCHIVO
		copyFile();		
	}
}
