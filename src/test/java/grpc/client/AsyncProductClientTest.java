package grpc.client;

import com.grpc.service.FindProductByNameRequest;
import com.grpc.service.ProductResponse;
import grpc.common.ResponseObserver;
import io.grpc.Status;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AsyncProductClientTest extends AbstractTest {

    @Test
    void FindProductByNameThenReturnListOfProducts() {
        var request = FindProductByNameRequest.newBuilder().setName("Laptop").build();
        ResponseObserver<ProductResponse> streamObserverResponse = ResponseObserver.create();

        this.productServiceAsyncStub.getProductByName(request, streamObserverResponse);
        streamObserverResponse.await();

        Assertions.assertEquals(2, streamObserverResponse.getList().size());
    }

    @Test
    void FindProductByNameTheReturnStatusInvalidArgument() {
        var request = FindProductByNameRequest.newBuilder().setName("!!!!").build();
        ResponseObserver<ProductResponse> streamObserverResponse = ResponseObserver.create();

        this.productServiceAsyncStub.getProductByName(request, streamObserverResponse);
        streamObserverResponse.await();

        Assertions.assertEquals(streamObserverResponse.getThrowable().getClass(), Status.INVALID_ARGUMENT.asRuntimeException().getClass());
    }

}
