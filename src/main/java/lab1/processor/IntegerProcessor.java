package lab1.processor;

import lab1.StatisticsCollector;

public class IntegerProcessor implements DataProcessorStrategy {
    private int num = 0;
    @Override
    public void process(String data) {
        this.num = Integer.parseInt(data);
        StatisticsCollector.getInstance().addInteger(num);
    }
}
