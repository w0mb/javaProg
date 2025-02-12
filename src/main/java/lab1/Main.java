package lab1;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import lab1.CommandLineArgs;
import lab1.DataProcessor;

public class Main {

    public static void main(String[] args) {
        try {
            // Парсим аргументы командной строки
            System.out.println("startRun");
            CommandLineArgs commandLineArgs = new CommandLineArgs(args);
            DataProcessor dataProcessor = new DataProcessor(commandLineArgs);

            // Получаем список файлов
            List<String> filenames = commandLineArgs.getInputFiles();

            ExecutorService executor = Executors.newFixedThreadPool(4);

            for (String filename : filenames) {
                executor.submit(() -> {
                    try {
                        System.out.println("setting filename");
                        Thread.sleep(1000);
                        dataProcessor.setFilename(filename);
                        Thread.sleep(1000);
                        System.out.println("setting threads");
                        dataProcessor.setNumThreads(4);
                        Thread.sleep(2000);
                        System.out.println("run processFileMultithreaded");
                        dataProcessor.processFileMultithreaded();
                    } catch (Exception e) {
                        System.out.println("Ошибка обработки файла " + filename + ": " + e.getMessage());
                    }
                });
            }

            executor.shutdown();
            while (!executor.isTerminated()) {
                // Ждем завершения всех потоков
            }

            // Вывод статистики
            dataProcessor.printStatistics(commandLineArgs);

            // Запись результатов в файлы
            dataProcessor.writeResults();

        } catch (Exception e) {
            System.out.println("Ошибка выполнения: " + e.getMessage());
        }
    }
}
