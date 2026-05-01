package hamza.maharmeh.fixedwindow;

import java.time.LocalTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class WindowCounter {
    private final int MAX_REQUEST_COUNT = 10;
    private final AtomicInteger requestAmount = new AtomicInteger(0);

    public WindowCounter() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        LocalTime now = LocalTime.now();
        scheduler.scheduleAtFixedRate(() -> {
            requestAmount.set(0);
        }, 60 - now.getSecond(), 60,TimeUnit.SECONDS);
    }

    public boolean tryToRequest() {
        if(requestAmount.get() > MAX_REQUEST_COUNT) return false;
        requestAmount.incrementAndGet();
        return true;
    }
}
