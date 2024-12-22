package grpc.client;

import com.google.common.util.concurrent.Uninterruptibles;
import com.grpc.service.DeadlineRequest;
import com.grpc.service.DeadlineResponse;
import grpc.common.ResponseObserver;
import io.grpc.Deadline;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class UnaryDeadlineTest extends AbstractTest {

    @Test
    void testUnaryDeadline() {
        var ex = assertThrows(StatusRuntimeException.class, () -> {
            var request = DeadlineRequest.newBuilder().setId(1L).build();
            var deadline = Deadline.after(1L, TimeUnit.SECONDS);
            this.deadlineServiceBlockingStub
                    .withDeadline(deadline)
                    .getMessage(request);
        });
        assertEquals(Status.Code.DEADLINE_EXCEEDED, ex.getStatus().getCode());
    }

    @Test
    void testAsyncDeadline() {
        var streamObserver = ResponseObserver.<DeadlineResponse>create();
        var deadline = Deadline.after(1L, TimeUnit.SECONDS);
        var request = DeadlineRequest.newBuilder().setId(1L).build();

        this.deadlineServiceAsyncStub.withDeadline(deadline).getMessage(request, streamObserver);
        streamObserver.await();

        assertEquals(Status.Code.DEADLINE_EXCEEDED, Status.fromThrowable(streamObserver.getThrowable()).getCode());
    }

    @Test
    void testDeadlineStream() {
        try {
            var deadline = Deadline.after(5L, TimeUnit.SECONDS);
            var request = DeadlineRequest.newBuilder().setId(1L).build();

            var iterator = this.deadlineServiceBlockingStub
                    .withDeadline(deadline)
                    .getStreamOfMessage(request);

            while (iterator.hasNext()) {
                System.out.println(iterator.next());
            }
        } catch (StatusRuntimeException e) {
            System.out.println("1");
        }
     }

}
