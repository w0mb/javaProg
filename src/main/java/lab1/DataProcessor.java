package lab1;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.*;

class DataProcessor {
    private final CommandLineArgs commandLineArgs;
    private String filename;
    private int numThreads = 4;
    private int intCount = 0;
    private int doubleCount = 0;
    private int stringCount = 0;
    private int longCount = 0;

    private BigDecimal sum = BigDecimal.ZERO;
    private BigDecimal min = BigDecimal.valueOf(Long.MAX_VALUE);
    private BigDecimal max = BigDecimal.valueOf(Long.MIN_VALUE);
    private double stringMinLength = Double.MAX_VALUE;
    private double stringMaxLength = 0;


    public List<Object> dataList = new ArrayList<Object>();


    private List<Integer> integers = Collections.synchronizedList(new ArrayList<>());
    private List<Double> doubles = Collections.synchronizedList(new ArrayList<>());
    private List<String> strings = Collections.synchronizedList(new ArrayList<>());
    private List<Long> longs = Collections.synchronizedList(new ArrayList<>());

    public DataProcessor(CommandLineArgs commandLineArgs) {
        this.commandLineArgs = commandLineArgs;
    }

    public void processFileMultithreaded() throws IOException {
        System.out.println("processFileMultithreaded started");
        List<String> lines = readFile(filename);
        int chunkSize = (int) Math.ceil((double) lines.size() / numThreads);



        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        List<Future<Void>> futures = new ArrayList<>();

        for (int i = 0; i < numThreads; i++) {
            int start = i * chunkSize;
            int end = Math.min(start + chunkSize, lines.size());
            List<String> chunk = lines.subList(start, end);

            int threadId = i + 1;
            futures.add(executor.submit(() -> {
                processChunk(threadId, chunk);
                return null;
            }));
        }

        for (Future<Void> future : futures) {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        executor.shutdown();
    }


    private List<String> readFile(String filename) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line.trim());
            }
        }
        return lines;
    }

private void processChunk(int threadId, List<String> lines) {
    long startTime = System.currentTimeMillis();
    int totalLines = lines.size();
    StringBuilder progressBar = new StringBuilder("[          ]");
    int progressLength = 10;

    for (int i = 0; i < totalLines; i++) {
        String line = lines.get(i);
        if (line.isEmpty()) continue;
        processLine(line);
        if ((i + 1) % (totalLines / progressLength + 1) == 0) {
            int filled = (i + 1) * progressLength / totalLines;
            // Синхронизация вывода
            synchronized (System.out) {
                System.out.printf("\rПоток %d | ID: %d | Прогресс: [%s] %d%%",
                        threadId, Thread.currentThread().getId(), "#".repeat(filled) + " ".repeat(progressLength - filled), ((i + 1) * 100) / totalLines);
                System.out.flush();
                try{Thread.sleep(300);} catch (InterruptedException e) {Thread.currentThread().interrupt();}// Принудительный вывод
            }
        }
    }

    long elapsedTime = System.currentTimeMillis() - startTime;
    // Синхронизация вывода
    synchronized (System.out) {
        System.out.printf("\rПоток %d | ID: %d | Завершено за %d мс\n", threadId, Thread.currentThread().getId(), elapsedTime);
        System.out.flush();  // Принудительный вывод
    }
}


    private synchronized void processLine(String line) {
        List<Object> dataList = this.dataList;

        if (line.matches("^-?\\d+$")) {
            int num = Integer.parseInt(line);
            integers.add(num);
            intCount++;
            dataList.add(num);
            updateStats(num);
        } else if (line.matches("^-?\\d+[lL]?$")) {
            long num = Long.parseLong(line.replaceAll("[lL]$", ""));
            longs.add(num);
            longCount++;
            dataList.add(num);
            updateStats(num);
        } else if (line.matches("^-?\\d*\\.\\d+$")) {
            double num = Double.parseDouble(line);
            doubles.add(num);
            doubleCount++;
            dataList.add(num);
            updateStats(num);
        } else {
            strings.add(line);
            stringCount++;
            stringMinLength = Math.min(stringMinLength, line.length());
            stringMaxLength = Math.max(stringMaxLength, line.length());
            dataList.add(line);
        }
    }

    private void updateStats(Number num) {
        BigDecimal value = new BigDecimal(num.toString());
        sum = sum.add(value);
        min = min.min(value);
        max = max.max(value);
    }

    public void printStatistics(CommandLineArgs commandLineArgs) {
        if(commandLineArgs.isShortStats()) {
            System.out.println("Краткая статистика:");
            System.out.println("Целых чисел: " + intCount);
            System.out.println("Дробных чисел: " + doubleCount);
            System.out.println("Строк: " + stringCount);
            System.out.println("Лонгов: " + longCount);
        }
        if (commandLineArgs.isFullStats()) {
            System.out.println("Краткая статистика:");
            System.out.println("Целых чисел: " + intCount);
            System.out.println("Дробных чисел: " + doubleCount);
            System.out.println("Строк: " + stringCount);
            System.out.println("Лонгов: " + longCount);
            System.out.println("Полная статистика:");
            System.out.println("Сумма: " + sum);
            System.out.println("Максимум: " + max);
            System.out.println("Минимум: " + min);
            if (intCount + longCount + doubleCount > 0) {
                System.out.println("Среднее: " + sum.divide(BigDecimal.valueOf(intCount + longCount + doubleCount), 5, BigDecimal.ROUND_HALF_UP));
            }
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
    public void setFilename(String filename) { this.filename = filename;}
    public String getFilename() { return this.filename;}
    public void setNumThreads(int numThreads) { this.numThreads = numThreads;}

    public List<Object> getDataList() { return this.dataList;}
    public int getIntCount() { return this.intCount;}
    public int getDoubleCount() { return this.doubleCount;}
    public int getStringCount() { return this.stringCount;}
    public int getLongCount() { return this.longCount;}

    public void setIntCount(int intCount) { this.intCount = intCount;}
    public void setDoubleCount(int doubleCount) { this.doubleCount = doubleCount;}
    public void setStringCount(int stringCount) { this.stringCount = stringCount;}
    public void setLongCount(int longCount) { this.longCount = longCount;}
    public void setSum(BigDecimal sum) { this.sum = sum;}
    public void setMin(BigDecimal min) { this.min = min;}
    public void setMax(BigDecimal max) { this.max = max;}
    public void setStringMinLength(int stringMinLength) { this.stringMinLength = stringMinLength;}
    public void setStringMaxLength(int stringMaxLength) { this.stringMaxLength = stringMaxLength;}
    public void setIntegers(int integers) { this.integers.add(integers);}
    public void setDoubles(double doubles) { this.doubles.add(doubles);}
    public void setStrings(String strings) { this.strings.add(strings);}
}
