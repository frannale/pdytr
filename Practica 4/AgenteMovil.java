import jade.core.*;
import jade.wrapper.ContainerController;
import java.lang.management.ManagementFactory;
import com.sun.management.OperatingSystemMXBean;

public class AgenteMovil extends Agent
{
	long time;
	double[] cpuLoad = new double[2];
	long[]  freeMemory = new long[2];
	String[] names = new String[2];

// Ejecutado por unica vez en la creacion
public void setup()
{
	jade.core.Runtime runtime = jade.core.Runtime.instance();

	//Crear un container
	Profile profile = new ProfileImpl();
	profile.setParameter(Profile.CONTAINER_NAME, "Container-1");
	profile.setParameter(Profile.MAIN_HOST, "localhost");
	ContainerController container = runtime.createAgentContainer(profile);

	//Crear un container
	Profile profile2 = new ProfileImpl();
	profile2.setParameter(Profile.CONTAINER_NAME, "Container-2");
	profile2.setParameter(Profile.MAIN_HOST, "localhost");
	ContainerController container2 = runtime.createAgentContainer(profile2);

	Location origen = here();
	System.out.println("\n\nHola, agente con nombre local " + getLocalName());
	System.out.println("Y nombre completo... " + getName());
	System.out.println("Y en location " + origen.getID());

// Para migrar el agente
try {
	ContainerID destino = new ContainerID("Container-1", null);
	System.out.println("Migrando el agente a " + destino.getID() + "\n\n");

	//El tiempo en el cual se inicia
	this.time = System.nanoTime();

	doMove(destino);
} catch (Exception e) {
	System.out.println("\n\n\nNo fue posible migrar el agente\n\n\n");}
}

// Ejecutado al llegar a un contenedor como resultado de una migracion
protected void afterMove()
{
	Location origen = here();
	String[] nombre = origen.getID().split("@", 0);

	if(nombre[0].equals("Main-Container")){
		for(int i=0;i<2;i++){
			System.out.println("\n\nInformación de la máquina con nombre: " + this.names[i]);
			System.out.println("Carga de procesamiento medida en el instante fue de: " + (int)(this.cpuLoad[i] * 100) + "%");
			System.out.println("Memoria total disponible: " + (this.freeMemory[i]/1000000) + " megabytes");
		}
		System.out.println("\nTiempo total del recorrido: " + (System.nanoTime() - this.time) + " nanosegundos");
	}
	else{
		OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
		String[] arrayId = nombre[0].split("-", 0);
		int id = Integer.parseInt(arrayId[1]);

		System.out.println("Estoy en el container " + nombre[0] + " obteniendo datos...");
		this.cpuLoad[id-1] = osBean.getSystemCpuLoad();
		this.freeMemory[id-1] = osBean.getFreePhysicalMemorySize();
		this.names[id-1] = nombre[0];

		if(nombre[0].equals("Container-1")){
			ContainerID destino = new ContainerID("Container-2", null);
			System.out.println("Migrando el agente a " + destino.getID() + "\n\n");
			doMove(destino);
		}
		else{
			if(nombre[0].equals("Container-2")){
				ContainerID destino = new ContainerID("Main-Container", null);
				System.out.println("Migrando el agente a " + destino.getID());
				doMove(destino);
			}
		}
	}
}
}
