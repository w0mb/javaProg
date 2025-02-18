package lab1;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class StatisticsCollector {
    private static StatisticsCollector instance;

    private final List<Integer> integers = new ArrayList<>();
    private final List<Double> doubles = new ArrayList<>();
    private final List<String> strings = new ArrayList<>();
    private final List<Long> longs = new ArrayList<>();

    private BigDecimal sum = BigDecimal.ZERO;
    private BigDecimal min = BigDecimal.valueOf(Long.MAX_VALUE);
    private BigDecimal max = BigDecimal.valueOf(Long.MIN_VALUE);

    public StatisticsCollector() {}

    public static synchronized StatisticsCollector getInstance() {
        if (instance == null) {
            instance = new StatisticsCollector();
        }
        return instance;
    }

    public void addInteger(int value) {
        integers.add(value);
        updateStats(value);
    }

    public void addLong(long value) {
        longs.add(value);
        updateStats(value);
    }

    public void addDouble(double value) {
        doubles.add(value);
        updateStats(value);
    }

    public void addString(String value) {
        strings.add(value);
    }

    private void updateStats(Number num) {
        BigDecimal value = new BigDecimal(num.toString());
        sum = sum.add(value);
        min = min.min(value);
        max = max.max(value);
    }

    public void printStatistics() {
        System.out.println("Integers: " + this.integers.size());
        System.out.println("Doubles: " + this.doubles.size());
        System.out.println("Strings: " + this.strings.size());
        System.out.println("Longs: " + this.longs.size());
        System.out.println("Sum: " + this.sum);
        System.out.println("Min: " + this.min);
        System.out.println("Max: " + this.max);
    }
}
