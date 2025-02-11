package lab1;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.*;

class DataProcessor {
    private final CommandLineArgs commandLineArgs;
    private int intCount = 0;
    private int doubleCount = 0;
    private int stringCount = 0;
    private int longCount = 0;

    private BigDecimal sum = BigDecimal.ZERO;
    private BigDecimal min = BigDecimal.valueOf(Long.MAX_VALUE);
    private BigDecimal max = BigDecimal.valueOf(Long.MIN_VALUE);
    private double stringMinLength = Double.MAX_VALUE;
    private double stringMaxLength = 0;

    private final List<Integer> integers = Collections.synchronizedList(new ArrayList<>());
    private final List<Double> doubles = Collections.synchronizedList(new ArrayList<>());
    private final List<String> strings = Collections.synchronizedList(new ArrayList<>());
    private final List<Long> longs = Collections.synchronizedList(new ArrayList<>());

    public DataProcessor(CommandLineArgs commandLineArgs) {
        this.commandLineArgs = commandLineArgs;
    }

    public void processFileMultithreaded(String filename, int numThreads) throws IOException {
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
    StringBuilder progressBar = new StringBuilder("[          ]"); // 10 символов для прогресса
    int progressLength = 10; // Количество символов в прогресс-баре

    for (int i = 0; i < totalLines; i++) {
        String line = lines.get(i);
        if (line.isEmpty()) continue;
        processLine(line);

        // Обновление прогресса с анимацией
        if ((i + 1) % (totalLines / progressLength + 1) == 0) {
            int filled = (i + 1) * progressLength / totalLines;
            synchronized (System.out) { // Синхронизация вывода, чтобы избежать артефактов
                System.out.printf("\rПоток %d | ID: %d | Прогресс: [%s] %d%%",
                        threadId, Thread.currentThread().getId(), "#".repeat(filled) + " ".repeat(progressLength - filled), ((i + 1) * 100) / totalLines);
                System.out.flush(); // Принудительный вывод в консоль
            }
        }
    }

    long elapsedTime = System.currentTimeMillis() - startTime;
    synchronized (System.out) {
        System.out.printf("\rПоток %d | ID: %d | Завершено за %d мс\n", threadId, Thread.currentThread().getId(), elapsedTime);
    }
}


    private synchronized void processLine(String line) {
        if (line.matches("^-?\\d+$")) { // Integer
            int num = Integer.parseInt(line);
            integers.add(num);
            intCount++;
            updateStats(num);
        } else if (line.matches("^-?\\d+[lL]?$")) { // Long
            long num = Long.parseLong(line.replaceAll("[lL]$", ""));
            longs.add(num);
            longCount++;
            updateStats(num);
        } else if (line.matches("^-?\\d*\\.\\d+$")) { // Double
            double num = Double.parseDouble(line);
            doubles.add(num);
            doubleCount++;
            updateStats(num);
        } else { // String
            strings.add(line);
            stringCount++;
            stringMinLength = Math.min(stringMinLength, line.length());
            stringMaxLength = Math.max(stringMaxLength, line.length());
        }
    }

    private void updateStats(Number num) {
        BigDecimal value = new BigDecimal(num.toString());
        sum = sum.add(value);
        min = min.min(value);
        max = max.max(value);
    }

    public void printStatistics() {
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
