package pdytr.example.grpc;
import io.grpc.*;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.CountDownLatch;
import java.time.Duration;
import java.time.Instant;

public class ClienteAulaStreaming
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
        RegistroServiceGrpc.RegistroServiceStub stub = RegistroServiceGrpc
            .newStub(channel);

        // Clase para manejar el llamado asincronico 
        final CountDownLatch doneSignal = new CountDownLatch(1);

        // Definimos una clase anonima para la comunicacion
        StreamObserver<RegistroServiceOuterClass.RegistroResponse> responseObserver =
            new StreamObserver<RegistroServiceOuterClass.RegistroResponse>(){

                @Override
                public void onNext(RegistroServiceOuterClass.RegistroResponse response) {
                    // Se envia otro mensaje al servidor por la comunicacion abierta 
                    System.out.println(response.getCantidadRegistrada());
                }
                @Override
                public void onError(Throwable t) {
                    System.out.println(t);
                    doneSignal.countDown();
                }
                @Override
                public void onCompleted() {

                    // Se da por finalizada la comunicacion con el servidor 
                    System.out.println("Comunicacion finalizada");
                    doneSignal.countDown();
                }
        };
            
        // Se invoca el metodo mediante el stub por cada alumno
        StreamObserver<RegistroServiceOuterClass.RegistroRequest>  request = 
            stub.registroStreaming(responseObserver);

        // Marca de tiempo inicial
        Instant start = Instant.now();

        // Se itera sobre los x alumnos
        for (int i = 0; i < 200; ++i) {
            // Se crea el request por cada alumno
            RegistroServiceOuterClass.RegistroRequest registro =
                RegistroServiceOuterClass.RegistroRequest.newBuilder()
                .setFacultad(facultad)
                .setAula(aula)
                .setNombre("Runa_"+ i)
                .build();

            request.onNext(registro);
        }

        request.onCompleted();

        // Queda a la espera que se finalize la comunicacion con el servidor
        doneSignal.await();

        // Marca de tiempo final
        Instant end = Instant.now();

        // El Channel debe ser dado de baja antes de terminar el proceso
        channel.shutdownNow();

        // Calcula que imprime tiempo total
        Duration timeElapsed = Duration.between(start, end);
        System.out.println("Tiempo: "+ timeElapsed.toMillis() +" milliseconds");


    }
}