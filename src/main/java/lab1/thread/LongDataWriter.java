package lab1.thread;

import lab1.factory.RandomDataFactory;

public class LongDataWriter extends AbstractDataWriter {
    public LongDataWriter(int objectCount, String filename) {
        super(objectCount, filename);
    }

    @Override
    protected String generateData() {
        return String.valueOf(RandomDataFactory.getRandomObject());
    }
}
