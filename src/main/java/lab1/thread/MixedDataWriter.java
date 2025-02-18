package lab1.thread;

import lab1.factory.RandomDataFactory;

public class MixedDataWriter extends AbstractDataWriter {
    public MixedDataWriter(int objectCount, String filename) {
        super(objectCount, filename);
    }

    @Override
    protected String generateData() {
        Object randomData = RandomDataFactory.getRandomObject();
        return randomData.toString();
    }
}
