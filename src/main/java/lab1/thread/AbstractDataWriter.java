package lab1.thread;

import lab1.utils.FileWriterSingleton;

import java.io.IOException;

public abstract class AbstractDataWriter extends Thread {
    private final int objectCount;
    private final String filename;
    private final int threadNumber;
    private final ProgressListener progressListener;
    private long startTime;
    private long endTime;

    public AbstractDataWriter(int objectCount, String filename, int threadNumber, ProgressListener progressListener) {
        this.objectCount = objectCount;
        this.filename = filename;
        this.threadNumber = threadNumber;
        this.progressListener = progressListener;
    }

    @Override
    public void run() {
        startTime = System.currentTimeMillis();
        try {
            FileWriterSingleton writer = FileWriterSingleton.getInstance(filename);
            for (int i = 0; i < objectCount; i++) {
                String data = generateData();
                writer.writeLine(data);

                // Обновление прогресса
                if (progressListener != null) {
                    int progress = (int) ((i + 1) * 100.0 / objectCount);
                    progressListener.onProgress(threadNumber, progress);
                }
                try{Thread.sleep(200);}catch(InterruptedException e){}
            }
        } catch (IOException e) {
            System.out.println("Error writing to file " + filename + ": " + e.getMessage());
        }
        endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        System.out.println("Thread #" + threadNumber + " finished in " + executionTime + " ms.");
    }
    public long getExecutionTime() {
        return endTime - startTime;
    }
    protected abstract String generateData();

}
