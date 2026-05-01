package hamza.maharmeh.tokenbucket;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TokenBucket {
    private final Map<String,Bucket> buckets = new ConcurrentHashMap<>();
    private final ScheduledExecutorService executor = Executors.newScheduledThreadPool(5);

    public Bucket getBucket(String address) {

        return buckets.computeIfAbsent(address, k -> {
            Bucket b = new Bucket();
            executor.scheduleAtFixedRate(b::increaseTokenCount,0,1, TimeUnit.SECONDS);
            return b;
        });
    }
}
