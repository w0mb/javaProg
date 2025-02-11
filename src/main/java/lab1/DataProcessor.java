package lab1;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

class DataProcessor {
    private CommandLineArgs commandLineArgs;
    private int intCount = 0;
    private int doubleCount = 0;
    private int stringCount = 0;
    private int longCount = 0;

    private BigDecimal sum = BigDecimal.ZERO;
    private BigDecimal min = BigDecimal.valueOf(Long.MAX_VALUE);
    private BigDecimal max = BigDecimal.valueOf(Long.MIN_VALUE);
    private double stringMinLength = Double.MAX_VALUE;
    private double stringMaxLength = 0;

    private List<Integer> integers = new ArrayList<>();
    private List<Double> doubles = new ArrayList<>();
    private List<String> strings = new ArrayList<>();
    private List<Long> longs = new ArrayList<>();

    public DataProcessor(CommandLineArgs commandLineArgs) {
        this.commandLineArgs = commandLineArgs;
    }

    public List<Object> processFile(String filename) throws IOException {
        List<Object> dataList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                if (line.matches("^-?\\d+$")) { // Integer
                    int num = Integer.parseInt(line);
                    integers.add(num);
                    intCount++;
                    sum = sum.add(BigDecimal.valueOf(num));
                    min = min.min(BigDecimal.valueOf(num));
                    max = max.max(BigDecimal.valueOf(num));
                    dataList.add(num);
                } else if (line.matches("^-?\\d+[lL]?$")) { // Long
                    long num = Long.parseLong(line.replaceAll("[lL]$", ""));
                    longs.add(num);
                    longCount++;
                    sum = sum.add(BigDecimal.valueOf(num));
                    min = min.min(BigDecimal.valueOf(num));
                    max = max.max(BigDecimal.valueOf(num));
                    dataList.add(num);
                } else if (line.matches("^-?\\d*\\.\\d+$")) { // Double
                    double num = Double.parseDouble(line);
                    doubles.add(num);
                    doubleCount++;
                    sum = sum.add(BigDecimal.valueOf(num));
                    min = min.min(BigDecimal.valueOf(num));
                    max = max.max(BigDecimal.valueOf(num));
                    dataList.add(num);
                } else { // String
                    strings.add(line);
                    stringCount++;
                    stringMinLength = Math.min(stringMinLength, line.length());
                    stringMaxLength = Math.max(stringMaxLength, line.length());
                    dataList.add(line);
                }
            }
        } catch (IOException ex) {
            System.out.println("Ошибка чтения файла " + filename + ": " + ex.getMessage());
        }
        return dataList;
    }

    public void processData(List<Object> data) {
        // Данные уже обработаны в processFile, добавление в соответствующие списки
    }

    public void printStatistics(CommandLineArgs commandLineArgs) {
        if (commandLineArgs.isShortStats()) {
            System.out.println("Краткая статистика:");
            System.out.println("Целых чисел: " + intCount);
            System.out.println("Давлых чисел: " + doubleCount);
            System.out.println("Строк: " + stringCount);
            System.out.println("Лонгов: " + longCount);
        }

        if (commandLineArgs.isFullStats()) {
            System.out.println("Полная статистика:");
            System.out.println("Сумма: " + sum);
            System.out.println("Максимум: " + max);
            System.out.println("Минимум: " + min);
            System.out.println("Среднее: " + sum.divide(BigDecimal.valueOf(intCount), 5, BigDecimal.ROUND_HALF_UP));
            System.out.println("Мин строка: " + stringMinLength);
            System.out.println("Макс строка: " + stringMaxLength);
        }
    }

    public void writeResults() throws IOException {
        if (!integers.isEmpty()) writeFile("int.txt", integers);
        if (!doubles.isEmpty()) writeFile("double.txt", doubles);
        if (!strings.isEmpty()) writeFile("string.txt", strings);
        if (!longs.isEmpty()) writeFile("long.txt", longs);
    }

    private <T> void writeFile(String fileName, List<T> data) throws IOException {
        String filePath = commandLineArgs.getOutputPath() + commandLineArgs.getPrefix() + fileName;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, commandLineArgs.isAppend()))) {
            for (T item : data) {
                writer.write(item.toString());
                writer.newLine();
            }
        }
    }
}