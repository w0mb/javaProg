package lab1;

import lab1.StatisticsCollector;
import lab1.factory.DataProcessorFactory;
import lab1.processor.DataProcessorStrategy;
import lab1.utils.FileUtils;

import java.io.IOException;
import java.util.List;

public class DataProcessor {
    private final String filename;

    public DataProcessor(String filename) {
        this.filename = filename;
    }

    public StatisticsCollector processFile() throws IOException {
        List<String> lines = FileUtils.readFile(filename);
        StatisticsCollector stats = null;
        for (String line : lines) {
            if (!line.isEmpty()) {
                // Используем фабрику для получения соответствующего процессора
                DataProcessorStrategy processor = DataProcessorFactory.getProcessor(line);

                // Обрабатываем строку
                processor.process(line);
                stats = StatisticsCollector.getInstance();

            }
        }
        return stats;
    }
}
