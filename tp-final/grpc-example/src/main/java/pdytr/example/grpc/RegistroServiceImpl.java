package pdytr.example.grpc;
import io.grpc.stub.StreamObserver;
import java.lang.Throwable;

// Se extiende la clase abstracta compilada por .proto RegistroServiceGrpc.RegistroServiceImplBase
public class RegistroServiceImpl extends RegistroServiceGrpc.RegistroServiceImplBase {
  
  // Sobreescribimos operacion para comunicacion unaria
  @Override
  public void registro(RegistroServiceOuterClass.RegistroRequest request,
        StreamObserver<RegistroServiceOuterClass.RegistroResponse> responseObserver) {
    
    // Se debe usar un builder para contruir un nuevo objeto Protobuffer d e respuesta
    RegistroServiceOuterClass.RegistroResponse response = RegistroServiceOuterClass.RegistroResponse
      .newBuilder()
      .setCantidadRegistrada(1)
      .build();

    // Se usa responseObserver para enviar una unica respuesta
    responseObserver.onNext(response);

    // Al finalizar se invoca el metodo onCompleted()
    responseObserver.onCompleted();
  }

  // Sobreescribimos operacion para comunicacion streaming
  @Override
  public StreamObserver<RegistroServiceOuterClass.RegistroRequest> registroStreaming(
        final StreamObserver<RegistroServiceOuterClass.RegistroResponse> responseObserver) {

    // Definimos una clase anonima para la comunicacion
    return new StreamObserver<RegistroServiceOuterClass.RegistroRequest>(){

        private int cantidadRegistrada = 0;

        @Override
        public void onNext(RegistroServiceOuterClass.RegistroRequest registro) {
            // Incrementa la cantidad registrada
            cantidadRegistrada += 1;
        }
        @Override
        public void onError(Throwable t) {
            System.out.println(t);
        }
        @Override
        public void onCompleted() {
          // Se da por finalizada la comunicacion con el cliente
          responseObserver.onNext(
            RegistroServiceOuterClass.RegistroResponse
            .newBuilder()
            .setCantidadRegistrada(cantidadRegistrada)
            .build()
          );
          responseObserver.onCompleted();
        }
    };
  }
}