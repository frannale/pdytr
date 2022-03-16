package pdytr.example.grpc;

import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.7.0)",
    comments = "Source: RegistroService.proto")
public final class RegistroServiceGrpc {

  private RegistroServiceGrpc() {}

  public static final String SERVICE_NAME = "pdytr.example.grpc.RegistroService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<pdytr.example.grpc.RegistroServiceOuterClass.RegistroRequest,
      pdytr.example.grpc.RegistroServiceOuterClass.RegistroResponse> METHOD_REGISTRO =
      io.grpc.MethodDescriptor.<pdytr.example.grpc.RegistroServiceOuterClass.RegistroRequest, pdytr.example.grpc.RegistroServiceOuterClass.RegistroResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "pdytr.example.grpc.RegistroService", "registro"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              pdytr.example.grpc.RegistroServiceOuterClass.RegistroRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              pdytr.example.grpc.RegistroServiceOuterClass.RegistroResponse.getDefaultInstance()))
          .setSchemaDescriptor(new RegistroServiceMethodDescriptorSupplier("registro"))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static RegistroServiceStub newStub(io.grpc.Channel channel) {
    return new RegistroServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static RegistroServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new RegistroServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static RegistroServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new RegistroServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class RegistroServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void registro(pdytr.example.grpc.RegistroServiceOuterClass.RegistroRequest request,
        io.grpc.stub.StreamObserver<pdytr.example.grpc.RegistroServiceOuterClass.RegistroResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_REGISTRO, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_REGISTRO,
            asyncUnaryCall(
              new MethodHandlers<
                pdytr.example.grpc.RegistroServiceOuterClass.RegistroRequest,
                pdytr.example.grpc.RegistroServiceOuterClass.RegistroResponse>(
                  this, METHODID_REGISTRO)))
          .build();
    }
  }

  /**
   */
  public static final class RegistroServiceStub extends io.grpc.stub.AbstractStub<RegistroServiceStub> {
    private RegistroServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private RegistroServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected RegistroServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new RegistroServiceStub(channel, callOptions);
    }

    /**
     */
    public void registro(pdytr.example.grpc.RegistroServiceOuterClass.RegistroRequest request,
        io.grpc.stub.StreamObserver<pdytr.example.grpc.RegistroServiceOuterClass.RegistroResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_REGISTRO, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class RegistroServiceBlockingStub extends io.grpc.stub.AbstractStub<RegistroServiceBlockingStub> {
    private RegistroServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private RegistroServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected RegistroServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new RegistroServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public pdytr.example.grpc.RegistroServiceOuterClass.RegistroResponse registro(pdytr.example.grpc.RegistroServiceOuterClass.RegistroRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_REGISTRO, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class RegistroServiceFutureStub extends io.grpc.stub.AbstractStub<RegistroServiceFutureStub> {
    private RegistroServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private RegistroServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected RegistroServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new RegistroServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<pdytr.example.grpc.RegistroServiceOuterClass.RegistroResponse> registro(
        pdytr.example.grpc.RegistroServiceOuterClass.RegistroRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_REGISTRO, getCallOptions()), request);
    }
  }

  private static final int METHODID_REGISTRO = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final RegistroServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(RegistroServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_REGISTRO:
          serviceImpl.registro((pdytr.example.grpc.RegistroServiceOuterClass.RegistroRequest) request,
              (io.grpc.stub.StreamObserver<pdytr.example.grpc.RegistroServiceOuterClass.RegistroResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class RegistroServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    RegistroServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return pdytr.example.grpc.RegistroServiceOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("RegistroService");
    }
  }

  private static final class RegistroServiceFileDescriptorSupplier
      extends RegistroServiceBaseDescriptorSupplier {
    RegistroServiceFileDescriptorSupplier() {}
  }

  private static final class RegistroServiceMethodDescriptorSupplier
      extends RegistroServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    RegistroServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (RegistroServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new RegistroServiceFileDescriptorSupplier())
              .addMethod(METHOD_REGISTRO)
              .build();
        }
      }
    }
    return result;
  }
}
