package grpc.client;

import com.google.protobuf.Empty;
import grpc.common.AbstractInterceptorTest;
import io.grpc.*;
import io.grpc.stub.MetadataUtils;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ApiKeyInterceptorTest extends AbstractInterceptorTest {

    private static final Metadata.Key<String> API_KEY = Metadata.Key.of("api-key", Metadata.ASCII_STRING_MARSHALLER);

    @Override
    protected List<ClientInterceptor> getClientInterceptors() {
        return List.of(
                MetadataUtils.newAttachHeadersInterceptor(getApiKey())
        );
    }

    private Metadata getApiKey() {
        var metadata = new Metadata();
        metadata.put(API_KEY, "apiKey");
        return metadata;
    }

    @Test
    void test() {
        var request = Empty.newBuilder().build();
        var response = this.serviceBlockingStub.getStatusApi(request);
        System.out.println(response);
    }

}
