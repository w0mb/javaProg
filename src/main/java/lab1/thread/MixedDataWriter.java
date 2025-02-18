package lab1.thread;

import lab1.factory.RandomDataFactory;

public class MixedDataWriter extends AbstractDataWriter {
    public MixedDataWriter(int objectCount, String filename, int threadNumber, ProgressListener progressListener) {
        super(objectCount, filename, threadNumber, progressListener);
    }

    @Override
    protected String generateData() {
        Object randomData = RandomDataFactory.getRandomObject();
        return randomData != null ? randomData.toString() : "";
    }
}
