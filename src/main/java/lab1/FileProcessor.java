package lab1;

import java.util.List;

public class FileProcessor {
    private final CommandLineArgs commandLineArgs;

    public FileProcessor(CommandLineArgs commandLineArgs) {
        this.commandLineArgs = commandLineArgs;
    }

    public void process(List<String> inputFiles) {
        inputFiles.addAll(commandLineArgs.getInputFiles());
        StatisticsCollector stats = null;
        for (String filename : inputFiles) {
            try {
                DataProcessor dataProcessor = new DataProcessor(filename);
                stats = dataProcessor.processFile();

            } catch (Exception e) {
                System.out.println("Error uploading file " + filename + ": " + e.getMessage());
            }
        }

        try {
            stats.printStatistics();
//            statisticscolector.printStatistics(); // Теперь метод принимает CommandLineArgs
////            statisticscolector.writeResults();
        } catch (Exception e) {
            System.out.println("Error during file write: " + e.getMessage());
        }
    }
}
