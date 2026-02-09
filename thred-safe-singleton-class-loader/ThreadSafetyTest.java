import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

public class ThreadSafetyTest {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Thread Safety Test ===\n");

        int threadCount = 4000;
        CountDownLatch latch = new CountDownLatch(threadCount);
        Set<Integer> hashCodes = ConcurrentHashMap.newKeySet();

        // Create 1000 threads trying to get instance simultaneously
        for (int i = 0; i < threadCount; i++) {
            new Thread(() -> {
                try {
                    DatabaseConfig instance = DatabaseConfig.getInstance();
                    hashCodes.add(System.identityHashCode(instance));
                } finally {
                    latch.countDown();
                }
            }).start();
        }

        latch.await(); // Wait for all threads to complete

        System.out.println("Total threads: " + threadCount);
        System.out.println("Unique instances created: " + hashCodes.size());
        System.out.println("Test Result: " + (hashCodes.size() == 1 ? "✅ PASS" : "❌ FAIL"));

        if (hashCodes.size() == 1) {
            System.out.println("Instance hashCode: " + hashCodes.iterator().next());
        }
    }
}