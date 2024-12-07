package grpc.common;

import io.grpc.stub.StreamObserver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Logger;

public class ResponseObserver<T> implements StreamObserver<T> {

    private static final Logger logger = Logger.getLogger(ResponseObserver.class.getName());
    private final List<T> list = Collections.synchronizedList(new ArrayList<>());
    private final CountDownLatch latch;
    private Throwable throwable;

    private ResponseObserver(int count) {
        this.latch = new CountDownLatch(count);
    }

    public static <T> ResponseObserver<T> create() {
        return new ResponseObserver<T>(1);
    }

    @Override
    public void onNext(T value) {
        logger.info("received value: " + value);
        this.list.add(value);
    }

    @Override
    public void onError(Throwable t) {
        logger.info("received error: " + t.getMessage());
        this.throwable = t;
        this.latch.countDown();
    }

    @Override
    public void onCompleted() {
        logger.info("received completed");
        this.latch.countDown();
    }

}
