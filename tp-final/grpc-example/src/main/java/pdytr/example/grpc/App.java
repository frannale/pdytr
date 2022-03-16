package pdytr.example.grpc;
import io.grpc.*;

public class App
{
    public static void main( String[] args ) throws Exception
    {
      // Crea servidor en el puerto 8080
      Server server = ServerBuilder.forPort(8080)
        .addService(new RegistroServiceImpl())
        .build();

      // Corre el servidor
      server.start();
      // Los hilos del servidor corren en background.
      System.out.println("Servidor levantado");
      // Hilo principal espera finalizacion del servidor
      server.awaitTermination();
    }
}