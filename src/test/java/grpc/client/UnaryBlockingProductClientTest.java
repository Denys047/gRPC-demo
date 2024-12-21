package grpc.client;

import com.grpc.service.ErrorMessage;
import com.grpc.service.ProductRequest;
import com.grpc.service.ValidationCode;
import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.protobuf.ProtoUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class UnaryBlockingProductClientTest extends AbstractTest {

    private static final Metadata.Key<ErrorMessage> key = ProtoUtils.keyForProto(ErrorMessage.getDefaultInstance());

    @Test
    void FindProductByIdThenReturnProduct() {
        var request = ProductRequest.newBuilder().setId(1).build();

        var response = this.productServiceBlockingStub.getProduct(request);

        assertNotNull(response);
    }

    @Test
    void FindProductByIdThenReturnStatusInvalidArgument() {
        var request = ProductRequest.newBuilder().setId(-1).build();
        var ex = assertThrows(StatusRuntimeException.class, () -> this.productServiceBlockingStub.getProduct(request));

        ValidationCode customServerError = Optional.ofNullable(ex.getTrailers())
                .map(customServerException -> customServerException.get(key))
                .map(ErrorMessage::getValidationCode)
                .orElseThrow();

        Assertions.assertEquals(Status.Code.INVALID_ARGUMENT, ex.getStatus().getCode());
        Assertions.assertEquals(ValidationCode.INVALID_PRODUCT_ID, customServerError);
    }

}
