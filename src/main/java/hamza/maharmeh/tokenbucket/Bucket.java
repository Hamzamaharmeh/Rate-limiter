package hamza.maharmeh.tokenbucket;

import java.util.concurrent.atomic.AtomicInteger;

public class Bucket {
    private long lastUpdated;
    private AtomicInteger tokenCount;
    private static final int MAX_TOKEN_COUNT = 10;

    public Bucket() {
        tokenCount = new AtomicInteger(10);
        lastUpdated = System.currentTimeMillis();
    }

    public void increaseTokenCount() {
        lastUpdated = System.currentTimeMillis();
        if(tokenCount.get() < MAX_TOKEN_COUNT) tokenCount.incrementAndGet();
    }
    public void decreaseTokenCount() {
        if(tokenCount.get() > 0) tokenCount.decrementAndGet();
    }
    public boolean tryConsumeToken() {
        if(tokenCount.get() > 0) {
            decreaseTokenCount();
            return true;
        }
        return false;
    }
}
