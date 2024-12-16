package grpc.common;

import io.grpc.stub.StreamObserver;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class ResponseObserver<T> implements StreamObserver<T> {

    private final List<T> list = Collections.synchronizedList(new ArrayList<>());

    private Throwable throwable;

    private final CountDownLatch latch;

    private static final Logger log = LoggerFactory.getLogger(ResponseObserver.class);

    private ResponseObserver(int countLatch) {
        this.latch = new CountDownLatch(countLatch);
    }

    public static <T> ResponseObserver<T> create() {
        return new ResponseObserver<>(1);
    }

    public static <T> ResponseObserver<T> create(int countLatch) {
        return new ResponseObserver<>(countLatch);
    }

    @Override
    public void onNext(T value) {
        log.info(() -> "received item: " + value);
        this.list.add(value);
    }

    @Override
    public void onError(Throwable t) {
        log.info(() -> "received error: " + t.getMessage());
        this.throwable = t;
        this.latch.countDown();
    }

    @Override
    public void onCompleted() {
        log.info(() -> "received completed");
        this.latch.countDown();
    }

    public void await() {
        try{
            latch.await(5, TimeUnit.SECONDS);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<T> getList() {
        return this.list;
    }

    public Throwable getThrowable() {
        return this.throwable;
    }

}
