package grpc.common;

import com.example.server.GrpcServer;
import com.example.service.CallOptionsService;
import com.example.service.SecurityService;
import com.grpc.service.CallOptionsServiceGrpc;
import com.grpc.service.SecurityServiceGrpc;
import io.grpc.ClientInterceptor;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class AbstractInterceptorTest {

    private final GrpcServer grpcServer = GrpcServer.create(List.of(new CallOptionsService(), new SecurityService()));

    protected ManagedChannel channel;

    protected CallOptionsServiceGrpc.CallOptionsServiceBlockingStub blockingStub;
    protected CallOptionsServiceGrpc.CallOptionsServiceStub asyncStub;

    protected SecurityServiceGrpc.SecurityServiceBlockingStub serviceBlockingStub;
    protected SecurityServiceGrpc.SecurityServiceStub securityServiceStub;

    protected abstract List<ClientInterceptor> getClientInterceptors();

    @BeforeAll
    public void setUp() {
        this.grpcServer.start();
        this.channel = ManagedChannelBuilder.forAddress("localhost", 8080)
                .usePlaintext()
                .intercept(getClientInterceptors())
                .build();

        this.blockingStub = CallOptionsServiceGrpc.newBlockingStub(this.channel);
        this.asyncStub = CallOptionsServiceGrpc.newStub(this.channel);
        this.securityServiceStub = SecurityServiceGrpc.newStub(this.channel);
        this.serviceBlockingStub = SecurityServiceGrpc.newBlockingStub(this.channel);
    }

    @AfterAll
    public void shutDown() {
        this.grpcServer.shutdownNow();
        this.channel.shutdownNow();
    }

}
