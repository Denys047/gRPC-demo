package grpc.client;

import com.grpc.service.ProductRequest;
import io.grpc.Status;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UnaryBlockingProductClientTest extends AbstractTest {

    @Test
    void FindProductByIdThenReturnProduct() {
        var request = ProductRequest.newBuilder().setId(1).build();

        var response = this.productServiceBlockingStub.getProduct(request);

        assertNotNull(response);
    }

    @Test
    void FindProductByIdThenReturnStatusInvalidArgument() {
        var request = ProductRequest.newBuilder().setId(-1).build();
        assertThrows(Status.INVALID_ARGUMENT.asRuntimeException().getClass(), () -> this.productServiceBlockingStub.getProduct(request));
    }

}
