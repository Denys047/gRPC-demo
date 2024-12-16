package grpc.client;

import com.grpc.service.RandomNumber;
import com.grpc.service.UserRequest;
import grpc.common.ResponseObserver;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class ServerStreamingClientTest extends AbstractTest {

    @Test
    void whenUserSendRequestThenReturnRandomNumbersBlockingStub() {
        var request = UserRequest.newBuilder().setBound(10).setOrigin(1).build();
        Iterator<RandomNumber> iterator = stub.withdraw(request);

        int count = 0;
        while (iterator.hasNext()) {
            iterator.next();
            count++;
        }
        assertEquals(10, count);
    }

    @Test
    void whenUserSendRequestThenReturnRandomNumbersAsyncStub() {
        var request = UserRequest.newBuilder().setBound(10).setOrigin(1).build();
        ResponseObserver<RandomNumber> responseObserver = ResponseObserver.create();

        asyncStub.withdraw(request, responseObserver);
        responseObserver.await();

        assertEquals(10, responseObserver.getList().size());
        assertNull(responseObserver.getThrowable());
    }

}
