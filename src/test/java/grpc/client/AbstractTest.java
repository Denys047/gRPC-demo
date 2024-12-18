package grpc.client;

import com.example.repository.UserRepository;
import com.example.server.GrpcServer;
import com.example.service.UserService;
import com.grpc.service.UserServiceGrpc;
import grpc.common.AbstractChannelTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import java.util.List;

public abstract class AbstractTest extends AbstractChannelTest {

    private final GrpcServer server = GrpcServer.create(9090, List.of(new UserService(new UserRepository())));

    protected UserServiceGrpc.UserServiceBlockingStub stub;
    protected UserServiceGrpc.UserServiceStub asyncStub;

    @BeforeAll
    public void setup(){
        this.server.start();
        this.stub = UserServiceGrpc.newBlockingStub(channel);
        this.asyncStub = UserServiceGrpc.newStub(channel);
    }

    @AfterAll
    public void stop(){
        this.server.shutdownNow();
    }

}
