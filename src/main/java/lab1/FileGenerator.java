package lab1;

import java.util.ArrayList;
import java.util.List;

public class FileGenerator {
    private final int threadCount;
    private final int totalObjects;
    private final String filename;

    public FileGenerator(int threadCount, int totalObjects, String filename) {
        this.threadCount = threadCount;
        this.totalObjects = totalObjects;
        this.filename = filename;
    }

    public void generate() {
        List<MultiThreadProcessor.RandomObjectWriter> threads = new ArrayList<>();

        for (int i = 0; i < threadCount; i++) {
            System.out.print("Thread " + (i + 1) + ": [                                                  ] 0%\n");
        }

        for (int i = 0; i < threadCount; i++) {
            MultiThreadProcessor.RandomObjectWriter thread =
                new MultiThreadProcessor.RandomObjectWriter(i + 1, totalObjects / threadCount, filename);
            thread.start();
            threads.add(thread);
        }

        for (MultiThreadProcessor.RandomObjectWriter thread : threads) {
            try {
                thread.join();
                System.out.println("Thread #" + thread.getThreadNumber() + " (ID: " + thread.getId() + ") completed in "
                    + thread.getExecutionTime() + " ms");
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted: " + e.getMessage());
            }
        }
    }
}
