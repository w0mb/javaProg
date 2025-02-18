package lab1.thread;

import lab1.factory.RandomDataFactory;

public class IntegerDataWriter extends AbstractDataWriter {
    public IntegerDataWriter(int objectCount, String filename) {
        super(objectCount, filename);
    }

    @Override
    protected String generateData() {
        return String.valueOf(RandomDataFactory.getRandomObject());
    }
}
