package grpc.client;

import com.example.repository.AccountRepository;
import com.example.repository.ProductRepository;
import com.example.repository.UserRepository;
import com.example.server.GrpcServer;
import com.example.service.*;
import com.grpc.service.*;
import grpc.common.AbstractChannelTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import java.util.List;

public abstract class AbstractTest extends AbstractChannelTest {

    private final GrpcServer server = GrpcServer.create(9090, List.of(
            new UserService(new UserRepository()),
            new TransferService(new AccountRepository()),
            new ProductService(new ProductRepository()),
            new DeadlineService(),
            new CallOptionsService()));

    protected UserServiceGrpc.UserServiceBlockingStub userServiceBlockingStub;
    protected UserServiceGrpc.UserServiceStub userServiceAsyncStub;

    protected TransferServiceGrpc.TransferServiceBlockingStub transferServiceBlockingStub;
    protected TransferServiceGrpc.TransferServiceStub transferServiceAsyncStub;

    protected ProductServiceGrpc.ProductServiceBlockingStub productServiceBlockingStub;
    protected ProductServiceGrpc.ProductServiceStub productServiceAsyncStub;

    protected DeadlineServiceGrpc.DeadlineServiceBlockingStub deadlineServiceBlockingStub;
    protected DeadlineServiceGrpc.DeadlineServiceStub deadlineServiceAsyncStub;

    protected CallOptionsServiceGrpc.CallOptionsServiceBlockingStub callOptionsServiceBlockingStub;
    protected CallOptionsServiceGrpc.CallOptionsServiceStub callOptionsServiceAsyncStub;

    @BeforeAll
    public void setup() {
        this.server.start();
        this.userServiceBlockingStub = UserServiceGrpc.newBlockingStub(channel);
        this.userServiceAsyncStub = UserServiceGrpc.newStub(channel);
        this.transferServiceBlockingStub = TransferServiceGrpc.newBlockingStub(channel);
        this.transferServiceAsyncStub = TransferServiceGrpc.newStub(channel);
        this.productServiceBlockingStub = ProductServiceGrpc.newBlockingStub(channel);
        this.productServiceAsyncStub = ProductServiceGrpc.newStub(channel);
        this.deadlineServiceBlockingStub = DeadlineServiceGrpc.newBlockingStub(channel);
        this.deadlineServiceAsyncStub = DeadlineServiceGrpc.newStub(channel);
        this.callOptionsServiceBlockingStub = CallOptionsServiceGrpc.newBlockingStub(channel);
        this.callOptionsServiceAsyncStub = CallOptionsServiceGrpc.newStub(channel);
    }

    @AfterAll
    public void stop() {
        this.server.shutdownNow();
    }

}
