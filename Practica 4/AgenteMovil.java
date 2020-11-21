import jade.core.*;
import jade.wrapper.ContainerController;

public class AgenteMovil extends Agent
{
	int test = 0;

public int getTest(){
	return test;
}


public void setTest(int valor){
	test = valor;
}

// Ejecutado por unica vez en la creacion
public void setup()
{
	jade.core.Runtime runtime = jade.core.Runtime.instance();

	//Crear un container
	Profile profile = new ProfileImpl();
	profile.setParameter(Profile.CONTAINER_NAME, "Container-2");
	profile.setParameter(Profile.MAIN_HOST, "localhost");
	ContainerController container = runtime.createAgentContainer(profile);

	//Crear un container
	Profile profile2 = new ProfileImpl();
	profile2.setParameter(Profile.CONTAINER_NAME, "Container-3");
	profile2.setParameter(Profile.MAIN_HOST, "localhost");
	ContainerController container2 = runtime.createAgentContainer(profile2);

	Location origen = here();
	System.out.println("\n\nHola, agente con nombre local " + getLocalName());
	System.out.println("Y nombre completo... " + getName());
	System.out.println("Y en location " + origen.getID() + "\n\n");

// Para migrar el agente
try {
	ContainerID destino = new ContainerID("Container-2", null);
	System.out.println("Migrando el agente a " + destino.getID());
	doMove(destino);
} catch (Exception e) {
	System.out.println("\n\n\nNo fue posible migrar el agente\n\n\n");}
}

// Ejecutado al llegar a un contenedor como resultado de una migracion
protected void afterMove()
{
	Location origen = here();
	String[] nombre = origen.getID().split("@", 0);
	String[] arrayId = nombre[0].split("-", 0);
	int id = Integer.parseInt(arrayId[1]);
	//String[] ids = new String[3];

	//ids[id] = arrayId[1];

	System.out.println("\n\nHola, agente migrado con nombre local " + getLocalName());
	System.out.println("Y nombre completo... " + getName());
	System.out.println("Mis datos: " + nombre[0] + " " +  arrayId[1]);
	System.out.println(this.getTest() + "\n\n");

	if(nombre[0].equals("Container-1")){
		System.out.println("Volvi");
	}

	if(nombre[0].equals("Container-2")){
		this.setTest(2);
		ContainerID destino = new ContainerID("Container-3", null);
		System.out.println("Migrando el agente a " + destino.getID());
		doMove(destino);
	}
	else{
		if(nombre[0].equals("Container-3")){
			ContainerID destino = new ContainerID("Container-1", null);
			System.out.println("Migrando el agente a " + destino.getID());
			doMove(destino);
		}
	}
}
}
