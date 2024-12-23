package grpc.client;

import com.grpc.service.Request;
import grpc.common.AbstractInterceptorTest;
import io.grpc.*;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class InterceptorTest extends AbstractInterceptorTest {

    private static final Logger logger = LoggerFactory.getLogger(InterceptorTest.class);

    @Override
    protected List<ClientInterceptor> getClientInterceptors() {
        return List.of(new DeadlineInterceptor(), new CompressionInterceptor(),new LoggingInterceptor());
    }

    @Test
    void testInterceptor() {
        var response = this.blockingStub.callOptions(Request.newBuilder().setMessage("Message").build());
        System.out.println(response);
    }

    private static class DeadlineInterceptor implements ClientInterceptor {

        @Override
        public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> method,
                                                                   CallOptions callOptions,
                                                                   Channel next) {
            logger.info(() -> "1");
            var deadline = Optional.ofNullable(callOptions.getDeadline());
            var globalDeadline = Deadline.after(4L, TimeUnit.SECONDS);
            callOptions = callOptions.withDeadline(deadline.orElse(globalDeadline));
            return next.newCall(method, callOptions);
        }

    }

    private static class CompressionInterceptor implements ClientInterceptor {

        private final String compressorName = new Codec.Gzip().getMessageEncoding();

        @Override
        public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> method,
                                                                   CallOptions callOptions,
                                                                   Channel next) {
            logger.info(() -> "2");
            callOptions = callOptions.withCompression(Optional.ofNullable(callOptions.getCompressor()).orElse(compressorName));
            return next.newCall(method, callOptions);
        }

    }

    private static class LoggingInterceptor implements ClientInterceptor {

        @Override
        public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> method, CallOptions callOptions, Channel next) {
            logger.info(() -> "3");
            return next.newCall(method, callOptions);
        }
    }

}
