package lab1.processor;

import lab1.StatisticsCollector;

public class DoubleProcessor implements DataProcessorStrategy {
    private double num;
    @Override
    public void process(String data) {
        this.num = Double.parseDouble(data);
        StatisticsCollector.getInstance().addDouble(num);
    }
}