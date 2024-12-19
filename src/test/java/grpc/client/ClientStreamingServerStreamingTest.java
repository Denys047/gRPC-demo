package grpc.client;

import com.example.model.TransferStatusModel;
import com.grpc.service.TransferRequest;
import com.grpc.service.TransferResponse;
import grpc.common.ResponseObserver;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

public class ClientStreamingServerStreamingTest extends AbstractTest {

    @Test
    void test() {
        ResponseObserver<TransferResponse> responseObserver = ResponseObserver.create();
        var requestObserver = this.transferServiceAsyncStub.transfer(responseObserver);

        IntStream.rangeClosed(1, 10)
                .mapToObj(i -> TransferRequest.newBuilder().setFromAccount(1).setToAccount(2).setAmount(100).build())
                .forEach(requestObserver::onNext);

        requestObserver.onCompleted();
        responseObserver.await();

        var allStatus = responseObserver.getList().stream().map(account -> TransferStatusModel.valueOf(account.getStatus().name())).toList();

        assertEquals(10, responseObserver.getList().size());
        allStatus.forEach(status -> assertEquals(TransferStatusModel.COMPLETED, status));
    }

}
