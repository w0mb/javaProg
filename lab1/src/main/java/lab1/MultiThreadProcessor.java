package lab1;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.stream.Collectors;

public class MultiThreadProcessor {
    private static final Random RANDOM = new Random();

    private static String getRandomString() {
        String symbols = "abcdefghijklmnopqrstuvwxyz";
        int size = RANDOM.nextInt(symbols.length()) + 1;
        return RANDOM.ints(size, 0, symbols.length())
                .mapToObj(symbols::charAt)
                .map(Object::toString)
                .collect(Collectors.joining());
    }

    private static Object getRandomObject() {
        switch (RANDOM.nextInt(4)) {
            case 0: return RANDOM.nextInt();
            case 1: return RANDOM.nextBoolean();
            case 2: return RANDOM.nextDouble();
            case 3: return getRandomString();
            default: return null;
        }
    }

static class RandomObjectWriter extends Thread {
    private final int threadNumber;
    private final int count;
    private final String filename;
    private long executionTime;

    public RandomObjectWriter(int threadNumber, int count, String filename) {
        this.threadNumber = threadNumber;
        this.count = count;
        this.filename = filename;
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();

        try (FileWriter writer = new FileWriter(filename, true)) {
            for (int i = 0; i < count; i++) {
                writer.write(getRandomObject().toString() + "\n");
                printProgress(threadNumber, getId(), (i + 1) * 100 / count);
                Thread.sleep(10);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        executionTime = System.currentTimeMillis() - startTime;
    }

    public long getExecutionTime() {
        return executionTime;
    }

    public int getThreadNumber() {
        return threadNumber;
    }

    private synchronized void printProgress(int threadNumber, long threadId, int percent) {
        StringBuilder progressBar = new StringBuilder("Thread " + threadNumber + " (ID: " + threadId + "): [");
        int width = 50;
        int i = (percent * width) / 100;

        for (int j = 0; j < width; j++) {
            progressBar.append(j < i ? "#" : " ");
        }
        progressBar.append("] ").append(percent).append("%");

        System.out.print(progressBar.toString() + "\n");
    }
}
}
