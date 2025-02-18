package lab1.factory;

import lab1.thread.*;

public class DataWriterFactory {
    public static AbstractDataWriter getWriter(int objectsPerThread, String filename, int threadNumber, ProgressListener progressListener) {
        return new MixedDataWriter(objectsPerThread, filename, threadNumber, progressListener);
    }
}
