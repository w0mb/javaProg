package lab1;

import java.util.List;

public class StartFileProcess {
    public static void start(List<String> inputFiles, String[] args){

        try {
                CommandLineArgs commandLineArgs = new CommandLineArgs(args);
                DataProcessor dataProcessor = new DataProcessor(commandLineArgs);
                inputFiles.addAll(commandLineArgs.getInputFiles());

                for (String filename : inputFiles) {
                    try {
                        dataProcessor.setFilename(filename);
                        dataProcessor.processFile();
                    } catch (Exception e) {
                        System.out.println("Error uploading file " + filename + ": " + e.getMessage());
                    }
                }

                dataProcessor.printStatistics(commandLineArgs);
                dataProcessor.writeResults();
            } catch (Exception e) {
                System.out.println("Error during file write: " + e.getMessage());
            }
    }
}
