package grpc.client;

import com.google.protobuf.Empty;
import com.grpc.service.FindUserByIdRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UnaryBlockingClientTest extends AbstractTest {

    @Test
    void getUserByIdRequestThenReturnUserResponse() {
        FindUserByIdRequest request = FindUserByIdRequest.newBuilder().setId(1).build();

        var response = userServiceBlockingStub.getUserById(request);

        assertNotNull(response);
    }

    @Test
    void getAllUsersRequestThenReturnAllUserResponse() {
        var request = Empty.newBuilder().build();

        var response = userServiceBlockingStub.getAllUsers(request);

        assertNotNull(response);
        var expected = 5;

        Assertions.assertEquals(expected,response.getUsersList().size());
    }

}
