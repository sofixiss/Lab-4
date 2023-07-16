import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

public class ArrayMultiplier {
    private static final int ARRAY_SIZE = 10000;
    private static final int MAX_RANDOM_NUMBER = 100;

    public static void main(String[] args) {
        Random random = new Random();
        int[] array1 = random.ints(ARRAY_SIZE, 0, MAX_RANDOM_NUMBER).toArray();
        int[] array2 = random.ints(ARRAY_SIZE, 0, MAX_RANDOM_NUMBER).toArray();

        for (int sleep : new int[]{0, 1}) {
            System.out.println("Sleep: " + sleep);
            measureTime(() -> multiplySync(array1, array2, sleep), "sync");
            measureTime(() -> multiplyParallel(array1, array2, sleep), "parallel");
        }
    }

    private static void measureTime(Runnable runnable, String method) {
        long time1 = System.currentTimeMillis();
        runnable.run();
        System.out.printf("%s: %s\n", method, System.currentTimeMillis() - time1);
    }

    private static void multiplySync(int[] array1, int[] array2, int sleep) {
        int[] result = new int[ARRAY_SIZE];
        for (int i = 0; i < ARRAY_SIZE; ++i) {
            result[i] = array1[i] * array2[i];
            sleep(sleep);
        }
    }

    private static void multiplyParallel(int[] array1, int[] array2, int sleep) {
        int[] result = new int[ARRAY_SIZE];
        IntStream.range(0, ARRAY_SIZE).parallel().forEach(i -> {
            result[i] = array1[i] * array2[i];
            sleep(sleep);
        });
    }

    private static void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

