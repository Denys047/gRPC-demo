package grpc.client;

import com.grpc.service.Request;
import io.grpc.Codec;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CallOptionsTest extends AbstractTest{

    @Test
    void testCallOptions() {
        var request = Request.newBuilder().setMessage("H").build();
        var response = this.callOptionsServiceBlockingStub.withCompression(new Codec.Gzip().getMessageEncoding()).callOptions(request);
        System.out.println(response);
        assertNotNull(response);
    }

}
