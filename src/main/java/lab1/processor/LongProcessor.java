package lab1.processor;

import lab1.StatisticsCollector;

public class LongProcessor implements DataProcessorStrategy {
    private long num;
    @Override
    public void process(String data) {
        this.num = Long.parseLong(data);
        StatisticsCollector.getInstance().addLong(num);
    }
}