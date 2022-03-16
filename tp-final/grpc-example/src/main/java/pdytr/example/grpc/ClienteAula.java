package pdytr.example.grpc;
import io.grpc.*;

public class ClienteAula
{
    // Variables de clase para el dispositivo en el aula
    static String facultad = "Facultad de informatica";
    static String aula = "Cobol";

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
      
      // Se itera sobre los x alumnos
      for (int i = 0; i < 10; ++i) {
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

              System.out.println(response.getCantidadRegistrada());
      }

      // Imprime cantidad total de alumnos 
      System.out.println(response.getCantidadRegistrada());

      // El Channel debe ser dado de baja antes de terminar el proceso
      channel.shutdownNow();
    }
}