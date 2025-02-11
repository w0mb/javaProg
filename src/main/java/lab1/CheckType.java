package lab1;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CheckType {
    public int intCount;
    public int doubleCount;
    public int stringCount;
    public int longCount;

    public BigDecimal sum = BigDecimal.ZERO;
    public BigDecimal avg;
    public BigDecimal min = BigDecimal.valueOf(Long.MAX_VALUE);
    public BigDecimal max = BigDecimal.valueOf(Long.MIN_VALUE);

    public int stringMin = 999999999;
    public int stringMax = 0;

    public List<Object> checkTypesFromFile(String filename) {
        List<Object> dataList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.matches("^-?\\d+$")) {
                    try {
                        int num = Integer.parseInt(line);
                        dataList.add(num);
                        intCount++;
                        BigDecimal value = BigDecimal.valueOf(num);
                        sum = sum.add(value);
                        min = min.min(value);
                        max = max.max(value);
                    } catch (NumberFormatException e) {
                        long num = Long.parseLong(line);
                        dataList.add(num);
                        longCount++;
                        BigDecimal value = BigDecimal.valueOf(num);
                        sum = sum.add(value);
                        min = min.min(value);
                        max = max.max(value);
                    }
                }
                else if (line.matches("^-?\\d+[lL]?$")) {
                    try {
                        line = line.replaceAll("[lL]$", "");
                        long num = Long.parseLong(line);
                        dataList.add(num);
                        longCount++;
                        BigDecimal value = BigDecimal.valueOf(num);
                        sum = sum.add(value);
                        min = min.min(value);
                        max = max.max(value);
                    } catch (NumberFormatException e) {
                        System.out.println("Ошибка преобразования в Long: " + line);
                    }
                }
                else if (line.matches("^-?\\d*\\.\\d+$")) {
                    double num = Double.parseDouble(line);
                    dataList.add(num);
                    doubleCount++;
                    BigDecimal value = BigDecimal.valueOf(num);
                    sum = sum.add(value);
                    min = min.min(value);
                    max = max.max(value);
                }
                else {
                    dataList.add(line);
                    stringCount++;
                    stringMax = Math.max(stringMax, line.length());
                    stringMin = Math.min(stringMin, line.length());
                }
            }
        } catch (IOException ex) {
            System.out.println("Ошибка чтения файла: " + ex.getMessage());
        }
        return dataList;
    }

    public int getIntCount() { return this.intCount; }
    public int getLongCount() { return this.longCount; }
    public int getStringCount() { return this.stringCount; }
    public int getDoubleCount() { return this.doubleCount; }

    public BigDecimal getSum() { return this.sum; }
    public BigDecimal getMin() { return this.min; }
    public BigDecimal getMax() { return this.max; }
//    public BigDecimal getAvg() { return sum.divide(BigDecimal.valueOf(intCount), 10, BigDecimal.ROUND_HALF_UP);  }
    public int getStringMin() { return this.stringMin;}
    public int getStringMax() { return this.stringMax;}
}
