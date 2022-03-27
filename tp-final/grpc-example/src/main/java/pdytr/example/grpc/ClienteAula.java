package pdytr.example.grpc;
import io.grpc.*;
import java.time.Duration;
import java.time.Instant;

public class ClienteAula
{
    // Variables de clase para el dispositivo en el aula
    static String facultad = "Facultad de informatica";
    static String aula = "Cobol";
    static int cantidadRegistrada = 0;

    public static void main( String[] args ) throws Exception
    {      
        // Channel es la abstraccion para connectarse al endpoint del servidor
        final ManagedChannel channel = ManagedChannelBuilder
          .forTarget("localhost:8080")
          .usePlaintext(true)
          .build();

        // Se crea un stub bloqueante asociado el Channel creado
        RegistroServiceGrpc.RegistroServiceBlockingStub stub = RegistroServiceGrpc
          .newBlockingStub(channel);

        // Marca de tiempo inicial
        Instant start = Instant.now(); 

        // Se itera sobre los x alumnos
        for (int i = 0; i < 200; ++i) {
            // Se crea el request por cada alumno
            RegistroServiceOuterClass.RegistroRequest request =
              RegistroServiceOuterClass.RegistroRequest.newBuilder()
                .setFacultad(facultad)
                .setAula(aula)
                .setNombre("Runa_"+ i)
                .build();

            // Se invoca el metodo mediante el stub por cada alumno
            RegistroServiceOuterClass.RegistroResponse response = 
              stub.registro(request);

            // Incrementa la cantidad en base al response
            // Comentar esto para medir performance
            cantidadRegistrada += response.getCantidadRegistrada();

        }

        // Marca de tiempo final
        Instant end = Instant.now();

        // Imprime cantidad total de alumnos 
        System.out.println(cantidadRegistrada);

        // El Channel debe ser dado de baja antes de terminar el proceso
        channel.shutdownNow();

        // Calcula que imprime tiempo total
        Duration timeElapsed = Duration.between(start, end);
        System.out.println("Tiempo: "+ timeElapsed.toMillis() +" milliseconds");

    }
}