package lab1.processor;

import lab1.StatisticsCollector;

public class StringProcessor implements DataProcessorStrategy {
    private String data;
    @Override
    public void process(String data) {
        StatisticsCollector.getInstance().addString(data);
        this.data = data;
    }
}
