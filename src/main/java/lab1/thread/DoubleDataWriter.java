package lab1.thread;

import lab1.factory.RandomDataFactory;
import lab1.thread.AbstractDataWriter;

public class DoubleDataWriter extends AbstractDataWriter {
    public DoubleDataWriter(int objectCount, String filename) {
        super(objectCount, filename);
    }

    @Override
    protected String generateData() {
        return String.valueOf(RandomDataFactory.getRandomObject());
    }
}
