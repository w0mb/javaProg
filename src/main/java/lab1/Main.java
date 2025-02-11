package lab1;

import java.util.List;

import lab1.DataProcessor;
import lab1.CommandLineArgs;

public class Main {

    public static void main(String[] args) {
        try {
            // Парсим аргументы командной строки
            CommandLineArgs commandLineArgs = new CommandLineArgs(args);
            DataProcessor dataProcessor = new DataProcessor(commandLineArgs);

            // Получаем список файлов
            List<String> filenames = commandLineArgs.getInputFiles();
            for (String filename : filenames) {
                List<Object> data = dataProcessor.processFile(filename);
                dataProcessor.processData(data);
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




