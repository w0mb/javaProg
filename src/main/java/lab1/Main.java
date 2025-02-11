package lab1;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {

    public static void main(String[] args) {
        try {
            // Парсим аргументы командной строки
            CommandLineArgs commandLineArgs = new CommandLineArgs(args);
            DataProcessor dataProcessor = new DataProcessor(commandLineArgs);

            // Получаем список файлов
            List<String> filenames = commandLineArgs.getInputFiles();

            ExecutorService executor = Executors.newFixedThreadPool(4);

            for (String filename : filenames) {
                executor.submit(() -> {
                    try {
                        dataProcessor.processFileMultithreaded(filename, 4);
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
            dataProcessor.printStatistics();

            // Запись результатов в файлы
            dataProcessor.writeResults();

        } catch (Exception e) {
            System.out.println("Ошибка выполнения: " + e.getMessage());
        }
    }
}
