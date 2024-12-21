package grpc.client;

import com.grpc.service.ErrorMessage;
import com.grpc.service.FindProductByNameRequest;
import com.grpc.service.ProductResponse;
import com.grpc.service.ValidationCode;
import grpc.common.ResponseObserver;
import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.protobuf.ProtoUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class AsyncProductClientTest extends AbstractTest {

    private static final Metadata.Key<ErrorMessage> key = ProtoUtils.keyForProto(ErrorMessage.getDefaultInstance());

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

        var validationCode = Optional.ofNullable(Status.trailersFromThrowable(streamObserverResponse.getThrowable()))
                .map(m -> m.get(key))
                .map(ErrorMessage::getValidationCode)
                .orElseThrow();

        Assertions.assertEquals(Status.INVALID_ARGUMENT.asRuntimeException().getClass(), streamObserverResponse.getThrowable().getClass());
        Assertions.assertEquals(ValidationCode.INVALID_PRODUCT_NAME, validationCode);
    }

}
