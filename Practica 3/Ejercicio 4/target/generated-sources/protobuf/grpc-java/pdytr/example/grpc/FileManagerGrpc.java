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
 * <pre>
 * Defining a Service, a Service can have multiple RPC operations
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.7.0)",
    comments = "Source: FileManager.proto")
public final class FileManagerGrpc {

  private FileManagerGrpc() {}

  public static final String SERVICE_NAME = "pdytr.example.grpc.FileManager";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<pdytr.example.grpc.FileManagerOuterClass.File,
      pdytr.example.grpc.FileManagerOuterClass.Response> METHOD_WRITE_FILE =
      io.grpc.MethodDescriptor.<pdytr.example.grpc.FileManagerOuterClass.File, pdytr.example.grpc.FileManagerOuterClass.Response>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "pdytr.example.grpc.FileManager", "writeFile"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              pdytr.example.grpc.FileManagerOuterClass.File.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              pdytr.example.grpc.FileManagerOuterClass.Response.getDefaultInstance()))
          .setSchemaDescriptor(new FileManagerMethodDescriptorSupplier("writeFile"))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static FileManagerStub newStub(io.grpc.Channel channel) {
    return new FileManagerStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static FileManagerBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new FileManagerBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static FileManagerFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new FileManagerFutureStub(channel);
  }

  /**
   * <pre>
   * Defining a Service, a Service can have multiple RPC operations
   * </pre>
   */
  public static abstract class FileManagerImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     *Servicio para escribir en el server 
     * </pre>
     */
    public void writeFile(pdytr.example.grpc.FileManagerOuterClass.File request,
        io.grpc.stub.StreamObserver<pdytr.example.grpc.FileManagerOuterClass.Response> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_WRITE_FILE, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_WRITE_FILE,
            asyncUnaryCall(
              new MethodHandlers<
                pdytr.example.grpc.FileManagerOuterClass.File,
                pdytr.example.grpc.FileManagerOuterClass.Response>(
                  this, METHODID_WRITE_FILE)))
          .build();
    }
  }

  /**
   * <pre>
   * Defining a Service, a Service can have multiple RPC operations
   * </pre>
   */
  public static final class FileManagerStub extends io.grpc.stub.AbstractStub<FileManagerStub> {
    private FileManagerStub(io.grpc.Channel channel) {
      super(channel);
    }

    private FileManagerStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FileManagerStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new FileManagerStub(channel, callOptions);
    }

    /**
     * <pre>
     *Servicio para escribir en el server 
     * </pre>
     */
    public void writeFile(pdytr.example.grpc.FileManagerOuterClass.File request,
        io.grpc.stub.StreamObserver<pdytr.example.grpc.FileManagerOuterClass.Response> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_WRITE_FILE, getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   * Defining a Service, a Service can have multiple RPC operations
   * </pre>
   */
  public static final class FileManagerBlockingStub extends io.grpc.stub.AbstractStub<FileManagerBlockingStub> {
    private FileManagerBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private FileManagerBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FileManagerBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new FileManagerBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     *Servicio para escribir en el server 
     * </pre>
     */
    public pdytr.example.grpc.FileManagerOuterClass.Response writeFile(pdytr.example.grpc.FileManagerOuterClass.File request) {
      return blockingUnaryCall(
          getChannel(), METHOD_WRITE_FILE, getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * Defining a Service, a Service can have multiple RPC operations
   * </pre>
   */
  public static final class FileManagerFutureStub extends io.grpc.stub.AbstractStub<FileManagerFutureStub> {
    private FileManagerFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private FileManagerFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FileManagerFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new FileManagerFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     *Servicio para escribir en el server 
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<pdytr.example.grpc.FileManagerOuterClass.Response> writeFile(
        pdytr.example.grpc.FileManagerOuterClass.File request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_WRITE_FILE, getCallOptions()), request);
    }
  }

  private static final int METHODID_WRITE_FILE = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final FileManagerImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(FileManagerImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_WRITE_FILE:
          serviceImpl.writeFile((pdytr.example.grpc.FileManagerOuterClass.File) request,
              (io.grpc.stub.StreamObserver<pdytr.example.grpc.FileManagerOuterClass.Response>) responseObserver);
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

  private static abstract class FileManagerBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    FileManagerBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return pdytr.example.grpc.FileManagerOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("FileManager");
    }
  }

  private static final class FileManagerFileDescriptorSupplier
      extends FileManagerBaseDescriptorSupplier {
    FileManagerFileDescriptorSupplier() {}
  }

  private static final class FileManagerMethodDescriptorSupplier
      extends FileManagerBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    FileManagerMethodDescriptorSupplier(String methodName) {
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
      synchronized (FileManagerGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new FileManagerFileDescriptorSupplier())
              .addMethod(METHOD_WRITE_FILE)
              .build();
        }
      }
    }
    return result;
  }
}
