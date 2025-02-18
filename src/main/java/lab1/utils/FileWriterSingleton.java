package lab1.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileWriterSingleton {
    private static FileWriterSingleton instance;
    private final BufferedWriter writer;

    private FileWriterSingleton(String filename) throws IOException {
        this.writer = new BufferedWriter(new FileWriter(filename, true));
    }

    public static synchronized FileWriterSingleton getInstance(String filename) throws IOException {
        if (instance == null) {
            instance = new FileWriterSingleton(filename);
        }
        return instance;
    }

    public synchronized void writeLine(String data) throws IOException {
        writer.write(data);
        writer.newLine();
        writer.flush();
    }

    public void close() throws IOException {
        writer.close();
    }
}
