package lab1;

import lab1.factory.DataWriterFactory;
import lab1.thread.AbstractDataWriter;
import lab1.thread.ProgressListener;

import java.util.ArrayList;
import java.util.List;

public class FileGenerator implements ProgressListener {
    private final int threadCount;
    private final int totalObjects;
    private final String filename;

    public FileGenerator(Builder builder) {
        this.threadCount = builder.threadCount;
        this.totalObjects = builder.totalObjects;
        this.filename = builder.filename;
    }

@Override
public void onProgress(int threadNumber, int progress) {
    // Выводим прогресс в главном потоке
    String progressBar = String.format("Thread #%d (ID: %d): [", threadNumber, Thread.currentThread().getId());
    int progressBarLength = 50; // Длина прогресс бара
    int filledLength = (int) (progressBarLength * progress / 100.0);
    String progressBarString = "=".repeat(filledLength) + " ".repeat(progressBarLength - filledLength);

    // Каждый раз выводим в новую строку, чтобы избежать наложения
    System.out.println(progressBar + progressBarString + "] " + progress + "%");
}


    public void generate() {
        List<Thread> threads = new ArrayList<>();
        int objectsPerThread = totalObjects / threadCount;

        for (int i = 0; i < threadCount; i++) {
            AbstractDataWriter writer = DataWriterFactory.getWriter(objectsPerThread, filename, i + 1, this);
            writer.start();
            threads.add(writer);
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted: " + e.getMessage());
            }
        }
        System.out.println();  // Переход на новую строку после завершения всех потоков
    }

    public static class Builder {
        private int threadCount;
        private int totalObjects;
        private String filename;

        public Builder setThreadCount(int threadCount) {
            this.threadCount = threadCount;
            return this;
        }

        public Builder setTotalObjects(int totalObjects) {
            this.totalObjects = totalObjects;
            return this;
        }

        public Builder setFilename(String filename) {
            this.filename = filename;
            return this;
        }

        public FileGenerator build() {
            return new FileGenerator(this);
        }
    }
}
