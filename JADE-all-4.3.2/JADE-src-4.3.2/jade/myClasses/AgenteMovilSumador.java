
import jade.core.*;
import java.io.File;
import java.io.*;
import jade.wrapper.ContainerController;

public class AgenteMovilSumador extends Agent
{
	//DECLARACION DE ESTADO DEL AGENTE
	private String now;
	private int total = 0;
	private int bufferLength = 100;
	private byte[] buffer = new byte[bufferLength];

	// METODO DE ACCION
	public void doAction(){
		try{
			// ABRE EL ARCHIVO Y TRAE EL NUMERO
			File file = new File("number.txt");
			RandomAccessFile in = new RandomAccessFile(file, "r");
			// LEE EL ARCHIVO
			int bytesReaded = in.read(buffer, 0, bufferLength);
			in.close();
			//CONVIERTE BUFFER  A STR Y LUEGO A INT PARA SUMARIZAR EL TOTAL
			String numeroLocalLeido = new String(buffer, "UTF-8");
			total += Integer.parseInt(numeroLocalLeido.trim());
			// IMPRIME RESULTADOS PARCIALES
			System.out.println("\nEl numero local leido es: " + numeroLocalLeido);  
			System.out.println("El acumulado parcial es de: " + String.valueOf(total));
			System.out.println(" ------------------------------------------------- " );
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	// CREAR NUEVO CONTAINER
	public void newDinamicContainer(String nombre){

		Profile profile = new ProfileImpl();
		profile.setParameter(Profile.CONTAINER_NAME, nombre);
		profile.setParameter(Profile.MAIN_HOST, "localhost");
		ContainerController container = jade.core.Runtime.instance().createAgentContainer(profile);
	}

	// LOGICA PARA PROXIMO DESTINO
	public ContainerID getNextContainer(){

		switch(now) {
			case "Container-2": return new ContainerID("Container-3", null);

			case "Container-3": return new ContainerID("Container-4", null);

			case "Container-4": return new ContainerID("Container-1", null);

			default:			return new ContainerID("Container-1", null);			
		}
	}

	// SETUP DE CREACION
	public void setup()
	{
		//NEW CONTAINER
		newDinamicContainer("Container-2");
		newDinamicContainer("Container-3");
		newDinamicContainer("Container-4");

		// MIGRA EL AGENTE
		try {
			ContainerID destino = new ContainerID("Container-2", null);
			doMove(destino);
		} catch (Exception e) {
			System.out.println("\n\n\nNo fue posible migrar el agente\n\n\n");
		}
	}

	// LUEGO DE MIGRAR AGENTE
	protected void afterMove()
	{
		// ACTUALIZA EL ESTADO QUE INDICA CONTAINER ACTUAL
		now = here().getID().split("@", 0)[0];
		System.out.println("\nAgente migrado a: " + now);
		// SI VOLVIO AL ORIGEN INFORMA, SINO EJECUTA ACCION
		if(now.equals("Container-1")){
			System.out.println("\n\nEl total acumulado es de: " + String.valueOf(total));
		}else{
			// EJECUTA EL METODO QUE LEE EL NUMERO DEL ARCHIVO
			doAction();
			// CONSULTA PROXIMO CONTAINER
			ContainerID destino = getNextContainer();
			doMove(destino);
		}
	
	}
}