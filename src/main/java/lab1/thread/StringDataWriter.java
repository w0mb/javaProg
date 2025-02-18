package lab1.thread;

import lab1.factory.RandomDataFactory;

public class StringDataWriter extends AbstractDataWriter {
    public StringDataWriter(int objectCount, String filename) {
        super(objectCount, filename);
    }

    @Override
    protected String generateData() {
        return String.valueOf(RandomDataFactory.getRandomObject());
    }
}
