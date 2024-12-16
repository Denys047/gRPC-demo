package grpc.client;

import com.google.protobuf.Empty;
import com.grpc.service.AllUsersResponse;
import com.grpc.service.FindUserByIdRequest;
import com.grpc.service.UserResponse;
import grpc.common.ResponseObserver;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UnaryAsyncClientTest extends AbstractTest {

    @Test
    void getUserByIdRequestThenReturnUserResponse() {
        var request = FindUserByIdRequest.newBuilder().setId(1L).build();
        ResponseObserver<UserResponse> streamObserver = ResponseObserver.create();

        asyncStub.getUserById(request, streamObserver);
        streamObserver.await();

        assertEquals(1, streamObserver.getList().size());
        assertEquals(1,streamObserver.getList().getFirst().getId());
        assertNull(streamObserver.getThrowable());
    }

    @Test
    void getAllUsersRequestThenReturnAllUserResponse(){
        var request = Empty.newBuilder().build();
        ResponseObserver<AllUsersResponse> streamObserver = ResponseObserver.create(5);

        asyncStub.getAllUsers(request,streamObserver);
        streamObserver.await();

        assertEquals(5,streamObserver.getList().getFirst().getUsersList().size());
        assertNull(streamObserver.getThrowable());
    }

}
