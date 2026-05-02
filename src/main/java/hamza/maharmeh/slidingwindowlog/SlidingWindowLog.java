package hamza.maharmeh.slidingwindowlog;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SlidingWindowLog {
    private Queue<Long> queue = new ArrayDeque<>();
    private final static int MAX_REQUESTS = 10;
    private final int windowSize;
    private final Lock lock = new ReentrantLock();
    public SlidingWindowLog(int windowSize) {
        this.windowSize = windowSize *  1000;
    }

    public boolean tryToRequest() {
        long now = System.currentTimeMillis();
        long lower = now - windowSize;
        lock.lock();
        try {
            while(!queue.isEmpty() && queue.peek() < lower) {
                queue.poll();
            }
            if(queue.size() < MAX_REQUESTS) {
                queue.add(now);
                return true;
            }else {
                return false;
            }
        }finally {
            lock.unlock();
        }

    }
}
