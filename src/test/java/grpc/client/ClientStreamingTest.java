package grpc.client;

import com.grpc.service.SumNumbersRequest;
import com.grpc.service.SumNumbersResponse;
import grpc.common.ResponseObserver;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

public class ClientStreamingTest extends AbstractTest {

    @Test
    void test() {
        var responseObserver = ResponseObserver.<SumNumbersResponse>create();
        var requestObserver = this.asyncStub.sum(responseObserver);

        IntStream.rangeClosed(1, 10)
                .mapToObj(i -> SumNumbersRequest.newBuilder().setNumber(i).build())
                .forEach(requestObserver::onNext);

        requestObserver.onCompleted();
        responseObserver.await();

        assertEquals(55, responseObserver.getList().getFirst().getResult());
    }

}
