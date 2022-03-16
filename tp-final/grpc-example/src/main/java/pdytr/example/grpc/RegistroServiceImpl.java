package pdytr.example.grpc;
import io.grpc.stub.StreamObserver;

// Se extiende la clase abstracta compilada por .proto RegistroServiceGrpc.RegistroServiceImplBase
public class RegistroServiceImpl extends RegistroServiceGrpc.RegistroServiceImplBase {
  @Override
  public void registro(RegistroServiceOuterClass.RegistroRequest request,
        StreamObserver<RegistroServiceOuterClass.RegistroResponse> responseObserver) {
    
    // RegistroRequest auto-genera el metodo toString().
    System.out.println(request);

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
}