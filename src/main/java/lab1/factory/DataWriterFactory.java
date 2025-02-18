package lab1.factory;

import lab1.thread.*;

public class DataWriterFactory {
    public static AbstractDataWriter getWriter(int objectsPerThread, String filename) {
        return new MixedDataWriter(objectsPerThread, filename);
    }
}
