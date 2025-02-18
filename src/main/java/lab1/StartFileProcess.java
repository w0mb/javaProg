package lab1;

import java.util.List;

public class StartFileProcess {
    public static void start(List<String> inputFiles, String[] args) {
        CommandLineArgs commandLineArgs = new CommandLineArgs(args);
        FileProcessor processor = new FileProcessor(commandLineArgs);
        processor.process(inputFiles);
    }
}
