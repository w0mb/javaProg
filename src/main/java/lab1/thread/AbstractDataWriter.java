package lab1.thread;

import lab1.utils.FileWriterSingleton;
import lab1.factory.RandomDataFactory;
import java.io.IOException;

public abstract class AbstractDataWriter extends Thread {
    private final int objectCount;
    private final String filename;

    public AbstractDataWriter(int objectCount, String filename) {
        this.objectCount = objectCount;
        this.filename = filename;
    }

    @Override
    public void run() {
        try {
            FileWriterSingleton writer = FileWriterSingleton.getInstance(filename);
            for (int i = 0; i < objectCount; i++) {
                String data = generateData();
                writer.writeLine(data);
            }
        } catch (IOException e) {
            System.out.println("Error writing to file " + filename + ": " + e.getMessage());
        }
    }

    protected abstract String generateData();  // Шаблонный метод
}
