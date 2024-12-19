package grpc.client;

import com.example.repository.AccountRepository;
import com.example.repository.UserRepository;
import com.example.server.GrpcServer;
import com.example.service.TransferService;
import com.example.service.UserService;
import com.grpc.service.TransferServiceGrpc;
import com.grpc.service.UserServiceGrpc;
import grpc.common.AbstractChannelTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import java.util.List;

public abstract class AbstractTest extends AbstractChannelTest {

    private final GrpcServer server = GrpcServer.create(9090, List.of(new UserService(new UserRepository()), new TransferService(new AccountRepository())));

    protected UserServiceGrpc.UserServiceBlockingStub userServiceBlockingStub;
    protected UserServiceGrpc.UserServiceStub userServiceAsyncStub;

    protected TransferServiceGrpc.TransferServiceBlockingStub transferServiceBlockingStub;
    protected TransferServiceGrpc.TransferServiceStub transferServiceAsyncStub;

    @BeforeAll
    public void setup() {
        this.server.start();
        this.userServiceBlockingStub = UserServiceGrpc.newBlockingStub(channel);
        this.userServiceAsyncStub = UserServiceGrpc.newStub(channel);
        this.transferServiceBlockingStub = TransferServiceGrpc.newBlockingStub(channel);
        this.transferServiceAsyncStub = TransferServiceGrpc.newStub(channel);
    }

    @AfterAll
    public void stop() {
        this.server.shutdownNow();
    }

}
